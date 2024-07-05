package com.pimsupa.coin

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CoinApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}