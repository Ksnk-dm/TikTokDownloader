package com.ksnk.tiktokdownloader.base

import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.ksnk.tiktokdownloader.R
import com.ksnk.tiktokdownloader.ui.main.MainActivity
import com.ksnk.tiktokdownloader.utils.Navigation
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

abstract class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {

    val navigation: Navigation by inject { parametersOf(requireParentFragment()) }

    fun setBlackColorInStatusBar() {
        requireActivity().window.statusBarColor = requireActivity().getColor(R.color.black)
        requireActivity().window.navigationBarColor = requireActivity().getColor(R.color.black)
    }

    fun hideBottomNav() {
        (requireActivity() as MainActivity).viewBinding.bottomNavigationView.visibility = View.GONE
    }

    fun showButtonNav() {
        (requireActivity() as MainActivity).viewBinding.bottomNavigationView.visibility = View.VISIBLE
    }
}