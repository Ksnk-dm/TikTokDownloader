package com.ksnk.tiktokdownloader

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

fun AppCompatActivity.setupActivity(activity: AppCompatActivity) {
    activity.supportFragmentManager.findFragmentById(R.id.fragmentContainerView) ?: run {
        val navHostFragment = NavHostFragment.create(R.navigation.nav_graph)
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, navHostFragment)
            .commitNow()
    }
}