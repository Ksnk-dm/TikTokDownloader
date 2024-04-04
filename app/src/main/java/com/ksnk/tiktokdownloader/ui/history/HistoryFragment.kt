package com.ksnk.tiktokdownloader.ui.history

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ksnk.tiktokdownloader.R
import com.ksnk.tiktokdownloader.base.BaseFragment
import com.ksnk.tiktokdownloader.databinding.FragmentHistoryBinding
import com.ksnk.tiktokdownloader.ui.download.DownloadFragment
import com.ksnk.tiktokdownloader.ui.download.DownloadFragment.Companion.DOWNLOAD_VIDEOS_DIRECTORY
import com.ksnk.tiktokdownloader.ui.download.DownloadViewModel
import com.ksnk.tiktokdownloader.ui.history.adapter.HistoryAdapter
import com.ksnk.tiktokdownloader.ui.main.MainActivity
import com.ksnk.tiktokdownloader.utils.Navigation
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HistoryFragment : BaseFragment(R.layout.fragment_history) {

    private val viewBinding by viewBinding(FragmentHistoryBinding::bind)
    private val viewModel: HistoryViewModel by inject()
    private val requestPermissionWriteExternalStorage =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
//todo
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showButtonNav()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionWriteExternalStorage.launch(Manifest.permission.READ_MEDIA_VIDEO)
        } else {
            requestPermissionWriteExternalStorage.launch(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
        with(viewBinding) {

            lifecycleScope.launch {
                val list = viewModel.getVideosFromFolder(
                    requireContext(),
                    DOWNLOAD_VIDEOS_DIRECTORY.absolutePath
                )

                recyclerViewHistory.adapter = HistoryAdapter(list, requireContext(), navigation)
                recyclerViewHistory.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)

            }
        }
    }
}