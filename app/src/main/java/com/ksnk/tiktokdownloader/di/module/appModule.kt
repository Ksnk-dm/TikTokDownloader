package com.ksnk.tiktokdownloader.di.module

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.ksnk.tiktokdownloader.Navigation
import com.ksnk.tiktokdownloader.R
import org.koin.dsl.module

val appModule = module {
   factory { (navHostFragment: NavHostFragment) -> Navigation(navHostFragment) }
}

