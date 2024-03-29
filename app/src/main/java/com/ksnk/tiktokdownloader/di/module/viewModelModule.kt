package com.ksnk.tiktokdownloader.di.module

import com.ksnk.tiktokdownloader.Navigation
import com.ksnk.tiktokdownloader.ui.download.DownloadViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { provideNavController(get()) }
    viewModel { DownloadViewModel(get(), get(), get()) }
    single<Navigation> { Navigation(get()) }
}