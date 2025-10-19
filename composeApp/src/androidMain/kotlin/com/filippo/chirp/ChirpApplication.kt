package com.filippo.chirp

import android.app.Application
import com.filippo.chirp.di.KoinApp
import org.koin.android.ext.koin.androidContext
import org.koin.ksp.generated.startKoin

class ChirpApplication : Application() {

    override fun onCreate() {
        super.onCreate()
//        KoinApp.startKoin {
//            androidContext(this@ChirpApplication)
//        }
    }
}
