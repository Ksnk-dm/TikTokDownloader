package com.ksnk.tiktokdownloader

import androidx.navigation.NavController

class Navigation(private val navController: NavController) {

    fun openShareFragmentFromDownload() =
        navController.navigate(R.id.action_fragmentDownload_to_fragmentShare)
}