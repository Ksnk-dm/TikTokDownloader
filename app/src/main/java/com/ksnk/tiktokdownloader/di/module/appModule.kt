package com.ksnk.tiktokdownloader.di.module

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.ksnk.tiktokdownloader.Navigation
import com.ksnk.tiktokdownloader.R
import org.koin.dsl.module

val appModule = module {
    single { provideNavController(get()) }
}

fun provideNavController(activity: AppCompatActivity): NavController {
    return activity.findNavController(R.id.fragmentContainerView)
}