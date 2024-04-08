package com.ksnk.tiktokdownloader.ui.history

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ksnk.tiktokdownloader.R
import com.ksnk.tiktokdownloader.base.BaseFragment
import com.ksnk.tiktokdownloader.databinding.FragmentHistoryBinding
import com.ksnk.tiktokdownloader.ui.download.DownloadFragment.Companion.DOWNLOAD_VIDEOS_DIRECTORY
import com.ksnk.tiktokdownloader.ui.history.adapter.HistoryAdapter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class HistoryFragment : BaseFragment(R.layout.fragment_history) {

    private val viewBinding by viewBinding(FragmentHistoryBinding::bind)
    private val viewModel: HistoryViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showButtonNav()
        getPermissionAccess()

        with(viewBinding) {
            adView.loadAd(initAdmob())
            lifecycleScope.launch {
                recyclerViewHistory.adapter = HistoryAdapter(
                    viewModel.getVideosFromFolder(
                        requireContext(),
                        DOWNLOAD_VIDEOS_DIRECTORY.absolutePath
                    ), requireContext(), navigation
                )
                recyclerViewHistory.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            }
        }
    }

    private fun getPermissionAccess() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_MEDIA_VIDEO
                ) == PackageManager.PERMISSION_DENIED
            ) navigation.openPermissionFragmentFromHistory()
        } else if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) navigation.openPermissionFragmentFromHistory()
    }
}