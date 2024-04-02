package com.ksnk.tiktokdownloader.di.module

import com.ksnk.tiktokdownloader.data.repository.DownloadRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory {  DownloadRepository(get(), get()) }
}