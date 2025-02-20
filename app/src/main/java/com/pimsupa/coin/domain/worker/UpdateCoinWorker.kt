package com.pimsupa.coin.domain.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.domain.repository.CoinRepository
import com.pimsupa.coin.util.CoinDispatchers
import com.pimsupa.coin.util.Dispatcher
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

@HiltWorker
class UpdateCoinWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val coinRepository: CoinRepository,
    @Dispatcher(CoinDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result = try {
        withContext(ioDispatcher) {
            Log.d("UpdateCoinWorker", "SyncWorker started")
            val count = coinRepository.getCoinCount()
            if (count == 0) return@withContext Result.success()

            val updateCoinFlow = coinRepository.updateCoins(coinRepository.getCoinCount())
            val coinList = mutableListOf<Coin>()
            updateCoinFlow
                .catch { e ->
                    Log.e("UpdateCoinWorker", "update coin failed", e)
                    Result.failure()
                }
                .collect { coins ->
                    coinList.addAll(coins)
                }
            coinRepository.updateLocalCoins(coinList)
            if (coinList.isNotEmpty()) {
                Result.success()
            } else {
                Result.retry()
            }
        }
    } catch (e: Exception) {
        Log.e("UpdateCoinWorker", "SyncWorker failed", e)
        Result.failure()
    }

    companion object {
        val SyncConstraints
            get() = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        fun createWorkRequest(delay: Int): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<UpdateCoinWorker>()
                .setConstraints(SyncConstraints)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    10, TimeUnit.SECONDS // Adjust as needed
                )
                .setInitialDelay(delay.toLong(), TimeUnit.SECONDS)
                .build()
        }
    }
}