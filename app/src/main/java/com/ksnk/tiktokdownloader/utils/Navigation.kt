package com.ksnk.tiktokdownloader.utils

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ksnk.tiktokdownloader.data.model.FileInfo
import com.ksnk.tiktokdownloader.ui.download.DownloadFragmentDirections
import com.ksnk.tiktokdownloader.ui.history.HistoryFragmentDirections
import com.ksnk.tiktokdownloader.ui.permission.PermissionFragmentDirections
import com.ksnk.tiktokdownloader.ui.share.ShareFragmentDirections

class Navigation(private val navHostFragment: NavHostFragment) {

    fun openShareFragmentFromDownload(filePath: String, id: FileInfo) =
        getNavController().navigate(DownloadFragmentDirections.actionFragmentDownloadToFragmentShare(filePath, id))

    fun openPlayerFragmentFromHistory(fileUri: String, fileName: String) =
        getNavController().navigate(HistoryFragmentDirections.actionHistoryFragmentToPlayerFragment(fileUri, fileName))

    fun openPlayerFragmentFromShare(fileUri: String, showButton: Boolean = true) =
        getNavController().navigate(ShareFragmentDirections.actionShareFragmentToPlayerFragment(fileUri, showButton))

    fun openPermissionFragmentFromHistory() =
        getNavController().navigate(HistoryFragmentDirections.actionHistoryFragmentToPermissionFragment())

    fun openDownloadFragmentFromPermission() =
        getNavController().navigate(PermissionFragmentDirections.actionPermissionFragmentToDownloadFragment())

    fun popBackStack() =
        getNavController().popBackStack()

    fun getNavController(): NavController =
        navHostFragment.findNavController()
}