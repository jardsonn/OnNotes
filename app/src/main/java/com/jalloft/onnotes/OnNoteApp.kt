package com.jalloft.onnotes

import android.app.Application
import com.jalloft.onnotes.di.AppModule.appModule
import com.jalloft.onnotes.di.cacheModule
import com.jalloft.onnotes.di.networkModule
import com.jalloft.onnotes.di.repositoryModule
import com.jalloft.onnotes.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber


class OnNoteApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@OnNoteApp)
            modules(
                appModule,
                cacheModule,
                networkModule,
                repositoryModule,
                viewModelModule
            )
        }
    }

}