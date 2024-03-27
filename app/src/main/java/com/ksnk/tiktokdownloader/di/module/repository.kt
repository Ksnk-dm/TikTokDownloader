package com.ksnk.tiktokdownloader.di.module

import com.ksnk.tiktokdownloader.data.DownloadRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory {  DownloadRepository(get()) }
}