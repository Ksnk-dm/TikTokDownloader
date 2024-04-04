package com.ksnk.tiktokdownloader.utils

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ksnk.tiktokdownloader.data.entity.FileEntity
import com.ksnk.tiktokdownloader.ui.download.DownloadFragmentDirections
import com.ksnk.tiktokdownloader.ui.history.HistoryFragmentDirections
import com.ksnk.tiktokdownloader.ui.share.ShareFragmentDirections

class Navigation(private val navHostFragment: NavHostFragment) {

    fun openShareFragmentFromDownload(filePath: String, id: FileEntity) =
        getNavController().navigate(DownloadFragmentDirections.actionFragmentDownloadToFragmentShare(filePath, id))

    fun openPlayerFragmentFromHistory(fileUri: String, fileName: String) =
       getNavController().navigate(HistoryFragmentDirections.actionHistoryFragmentToPlayerFragment(fileUri, fileName))

    fun openPlayerFragmentFromShare(fileUri: String) =
        getNavController().navigate(ShareFragmentDirections.actionShareFragmentToPlayerFragment(fileUri))
    fun popBackStack()=
        getNavController().popBackStack()

    fun getNavController(): NavController =
        navHostFragment.findNavController()
}