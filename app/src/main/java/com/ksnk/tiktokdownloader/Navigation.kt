package com.ksnk.tiktokdownloader

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ksnk.tiktokdownloader.data.entity.FileEntity
import com.ksnk.tiktokdownloader.ui.download.DownloadFragmentDirections

class Navigation(private val navHostFragment: NavHostFragment) {

    fun openShareFragmentFromDownload(fileUri: String, id: FileEntity) =
        navHostFragment.findNavController().navigate(DownloadFragmentDirections.actionFragmentDownloadToFragmentShare(fileUri, id))

    fun popBackStack()=
        navHostFragment.findNavController().popBackStack()
}