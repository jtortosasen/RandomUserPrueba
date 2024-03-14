package com.jtortosasen.randomuserapp

import android.app.Application
import co.touchlab.kermit.CommonWriter
import co.touchlab.kermit.LogcatWriter
import co.touchlab.kermit.Logger
import com.jtortosasen.data.di.dataModule
import com.jtortosasen.randomuserapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Logger.setLogWriters(LogcatWriter())
        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(appModule, dataModule)
        }
    }
}