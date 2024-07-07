package com.pimsupa.coin.domain.usecase

import android.util.Log
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.domain.worker.UpdateCoinWorker
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

interface UpdateCoin {
    operator fun invoke(limit: Int): Flow<List<Coin>>
}

class UpdateCoinImpl @Inject constructor(
    private val workManager: WorkManager,
) : UpdateCoin {
    override fun invoke(limit: Int): Flow<List<Coin>> = callbackFlow {
        try {
            val requestWorker = UpdateCoinWorker.createWorkRequest(limit, 10)
            workManager.enqueueUniqueWork(
                "UpdateCoinWork",
                ExistingWorkPolicy.REPLACE,
                requestWorker
            )
            workManager.getWorkInfoByIdLiveData(requestWorker.id).observeForever { workInfo ->
                when (workInfo.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        val coinsArray =
                            workInfo.outputData.getStringArray(UpdateCoinWorker.COIN_DATA)
                        if (coinsArray != null) {
                            val coins = coinsArray.map { Coin(it) }
                            trySend(coins)
                        }

                        workManager.enqueueUniqueWork(
                            "UpdateCoinWork",
                            ExistingWorkPolicy.REPLACE,
                            requestWorker
                        )
                    }

                    WorkInfo.State.FAILED, WorkInfo.State.CANCELLED -> {
                        close()
                    }

                    else -> {}
                }
            }

            awaitClose {
                close()
            }

        } catch (e: Exception) {
            Log.e("error",e.toString())
        }
    }

}