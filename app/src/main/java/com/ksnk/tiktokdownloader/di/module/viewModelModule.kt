package com.ksnk.tiktokdownloader.di.module

import androidx.navigation.fragment.NavHostFragment
import com.ksnk.tiktokdownloader.utils.Navigation
import com.ksnk.tiktokdownloader.ui.download.DownloadViewModel
import com.ksnk.tiktokdownloader.ui.history.HistoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { (navHostFragment: NavHostFragment) -> Navigation(navHostFragment) }
    viewModel { DownloadViewModel(get(), get()) }
    viewModel { HistoryViewModel(get(), get()) }
}