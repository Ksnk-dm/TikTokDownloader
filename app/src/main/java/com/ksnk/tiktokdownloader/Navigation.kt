package com.ksnk.tiktokdownloader

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ksnk.tiktokdownloader.ui.download.DownloadFragmentDirections

class Navigation(private val navHostFragment: NavHostFragment) {

    fun openShareFragmentFromDownload(fileUri: String, id: String) =
        navHostFragment.findNavController().navigate(DownloadFragmentDirections.actionFragmentDownloadToFragmentShare(fileUri.toString(), id))
}