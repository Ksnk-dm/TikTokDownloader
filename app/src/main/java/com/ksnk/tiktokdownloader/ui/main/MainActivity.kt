package com.ksnk.tiktokdownloader.ui.main

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.ads.MobileAds
import com.ksnk.tiktokdownloader.R
import com.ksnk.tiktokdownloader.databinding.ActivityMainBinding
import com.ksnk.tiktokdownloader.utils.Navigation
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    val viewBinding by viewBinding(ActivityMainBinding::bind)
    private val navigation: Navigation by inject { parametersOf(supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment) }
    private val onBackPressed = onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {}
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this) {}
        with(viewBinding) {
            bottomNavigationView.setupWithNavController(navigation.getNavController())

            bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.downloadFragment -> navigation.getNavController().navigate(R.id.downloadFragment)
                    R.id.historyFragment -> navigation.getNavController().navigate(R.id.historyFragment)
                }
                true
            }
        }
    }
}