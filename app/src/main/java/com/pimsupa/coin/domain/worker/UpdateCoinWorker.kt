package com.pimsupa.coin.domain.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.pimsupa.coin.util.CoinDispatchers
import com.pimsupa.coin.util.Dispatcher
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher

//@HiltWorker
//class PrivacyNoticeSyncWorker @AssistedInject constructor(
//    @Assisted appContext: Context,
//    @Assisted workerParams: WorkerParameters,
//    private val privacyRepository: Coin,
//    @Dispatcher(CoinDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
//) : CoroutineWorker(appContext, workerParams){
//
//}