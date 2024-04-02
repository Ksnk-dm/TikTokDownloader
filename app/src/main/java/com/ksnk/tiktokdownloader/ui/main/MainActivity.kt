package com.ksnk.tiktokdownloader.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ksnk.tiktokdownloader.R
import com.ksnk.tiktokdownloader.databinding.ActivityMainBinding
import com.ksnk.tiktokdownloader.utils.Navigation
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    private val viewBinding by viewBinding(ActivityMainBinding::bind)
    private val navigator: Navigation by inject { parametersOf(supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(viewBinding) {
            bottomNavigationView.setupWithNavController(navigator.getNavController())

            bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.downloadFragment -> navigator.getNavController().navigate(R.id.downloadFragment)
                    R.id.historyFragment -> navigator.getNavController().navigate(R.id.historyFragment)
                }
                true
            }
        }
    }
}