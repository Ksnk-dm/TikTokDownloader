package com.ksnk.tiktokdownloader.ui.download

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ksnk.tiktokdownloader.DownloadEvent
import com.ksnk.tiktokdownloader.R
import com.ksnk.tiktokdownloader.databinding.FragmentDownloadBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject

class DownloadFragment : Fragment(R.layout.fragment_download) {

    private val viewBinding by viewBinding(FragmentDownloadBinding::bind)
    private val downloadViewModel: DownloadViewModel by inject()
    private var isFileDownloaded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.buttonDownload.setOnClickListener {
            if (viewBinding.urlEditText.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            isFileDownloaded = false
            downloadViewModel.expandShortenedUrl(viewBinding.urlEditText.text.toString())
            downloadViewModel.getExpandedUrlLiveData()
                .observe(viewLifecycleOwner) { expandedUrl ->
                    downloadViewModel.extractVideoIdFromUrl(expandedUrl)?.let { id ->
                        if (!isFileDownloaded) {
                            downloadViewModel.downloadVideo(id, requireContext())
                            isFileDownloaded = true
                        }
                    }
                }
            viewBinding.urlEditText.setText("")
        }

        viewBinding.buttonPaste.setOnClickListener {
            viewBinding.urlEditText.setText(downloadViewModel.pasteFromClipboard(requireContext()))
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDownloadEvent(event: DownloadEvent) {
        if (event.success) Toast.makeText(requireContext(), "done", Toast.LENGTH_LONG).show()
    }
}