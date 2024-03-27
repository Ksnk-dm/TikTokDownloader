package com.ksnk.tiktokdownloader.di.module

import com.ksnk.tiktokdownloader.data.remote.RemoteDataSource
import org.koin.dsl.module

val remoteDataSourceModule= module {
    factory {  RemoteDataSource(get()) }
}