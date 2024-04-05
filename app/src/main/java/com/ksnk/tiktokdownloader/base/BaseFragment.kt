package com.ksnk.tiktokdownloader.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.ksnk.tiktokdownloader.R
import com.ksnk.tiktokdownloader.ui.main.MainActivity
import com.ksnk.tiktokdownloader.utils.Navigation
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

abstract class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {

    val navigation: Navigation by inject { parametersOf(requireParentFragment()) }
    var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(requireContext()) {}
        loadAds()
    }

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

    fun initAdmob(): AdRequest =
         AdRequest.Builder().build()

    private fun loadAds() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(requireContext(), AD_ID, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                loadAds()
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        mInterstitialAd = null
    }

    companion object {
        private const val AD_ID = "ca-app-pub-2981423664535117/8753070833"
    }
}