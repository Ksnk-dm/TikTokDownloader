package com.ksnk.tiktokdownloader

import android.app.Application
import com.ksnk.tiktokdownloader.di.module.appModule
import com.ksnk.tiktokdownloader.di.module.networkModule
import com.ksnk.tiktokdownloader.di.module.remoteDataSourceModule
import com.ksnk.tiktokdownloader.di.module.repositoryModule
import com.ksnk.tiktokdownloader.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class DownloaderApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DownloaderApplication)
            androidLogger()
            modules(networkModule, repositoryModule, viewModelModule, remoteDataSourceModule, appModule)
        }
    }
}