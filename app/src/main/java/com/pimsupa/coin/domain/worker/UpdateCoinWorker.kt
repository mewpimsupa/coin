package com.pimsupa.coin.domain.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.domain.repository.CoinRepository
import com.pimsupa.coin.util.CoinDispatchers
import com.pimsupa.coin.util.Dispatcher
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
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
            val offset = inputData.getInt(LIMIT, 0)

            val updateCoinFlow = coinRepository.updateCoin(offset)
            val coinList = mutableListOf<Coin>()
            updateCoinFlow.collect { coins ->
                coinList.addAll(coins)
            }
            if (coinList.isNotEmpty()) {
//                Result.success(workDataOf(COIN_DATA to coinList.map { it.toString() }
//                    .toTypedArray()))
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
        const val LIMIT = "limit"
        const val COIN_DATA = "coin_data"
        val SyncConstraints
            get() = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        fun createWorkRequest(limit: Int,delay:Int): OneTimeWorkRequest {
            val inputData = workDataOf(LIMIT to limit)
            return OneTimeWorkRequestBuilder<UpdateCoinWorker>()
                .setInputData(inputData)
                .setConstraints(SyncConstraints)
                .setInitialDelay(delay.toLong(), TimeUnit.SECONDS)
                .build()
        }
    }
}