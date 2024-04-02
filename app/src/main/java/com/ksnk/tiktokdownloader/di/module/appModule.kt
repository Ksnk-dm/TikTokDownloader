package com.ksnk.tiktokdownloader.di.module

import androidx.navigation.fragment.NavHostFragment
import com.ksnk.tiktokdownloader.utils.Navigation
import org.koin.dsl.module

val appModule = module {
   factory { (navHostFragment: NavHostFragment) -> Navigation(navHostFragment) }
}

