package com.pimsupa.coin.domain.usecase

import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.domain.repository.CoinRepository
import com.pimsupa.coin.domain.worker.UpdateCoinWorker
import com.pimsupa.coin.util.CoinDispatchers
import com.pimsupa.coin.util.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface UpdateCoin {
    operator fun invoke(page: Int): Flow<List<Coin>>
}

class UpdateCoinImpl @Inject constructor(
    private val workManager: WorkManager,
) : UpdateCoin {
    override fun invoke(offset: Int): Flow<List<Coin>> = callbackFlow {

        val inputData = workDataOf(UpdateCoinWorker.OFFSET to 0)
        val requestWorker = UpdateCoinWorker.request(inputData)
        workManager.enqueue(UpdateCoinWorker.request(inputData))
        workManager.getWorkInfoByIdLiveData(requestWorker.id)
            .observeForever { workInfo ->
                if (workInfo != null) {
                    when (workInfo.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            val coinsArray =
                                workInfo.outputData.getStringArray(UpdateCoinWorker.COIN_DATA)
                            if (coinsArray != null) {
                                val coins =
                                    coinsArray.map { Coin(it) } // Convert the string array back to Coin objects
                                trySend(coins)
                            }
                        }

                        WorkInfo.State.FAILED, WorkInfo.State.CANCELLED -> {
                            close()
                        }

                        else -> {}
                    }
                }
            }
    }

}