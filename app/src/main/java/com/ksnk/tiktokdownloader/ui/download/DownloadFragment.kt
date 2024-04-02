package com.ksnk.tiktokdownloader.ui.download

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ksnk.tiktokdownloader.DownloadEvent
import com.ksnk.tiktokdownloader.Navigation
import com.ksnk.tiktokdownloader.R
import com.ksnk.tiktokdownloader.databinding.FragmentDownloadBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class DownloadFragment : Fragment(R.layout.fragment_download) {

    private val viewBinding by viewBinding(FragmentDownloadBinding::bind)
    private val viewModel: DownloadViewModel by inject()
    private var isFileDownloaded = false
    private val navigator: Navigation by inject { parametersOf(requireParentFragment()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding) {
            activity?.intent?.let { text ->
                text.getStringExtra(Intent.EXTRA_TEXT)?.let {
                    urlEditText.setText(it)
                }
            }

            buttonDownload.setOnClickListener {
                if (urlEditText.text.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), "empty", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                isFileDownloaded = false
                viewModel.expandShortenedUrl(urlEditText.text.toString())
                viewModel.getExpandedUrlLiveData()
                    .observe(viewLifecycleOwner) { fileEntity ->
                        if (!isFileDownloaded) {
                            navigator.openShareFragmentFromDownload(
                                viewModel.downloadVideo(
                                    fileEntity,
                                    requireContext()
                                )?.absolutePath.toString(), fileEntity
                            )
                            isFileDownloaded = true
                        }
                    }
                urlEditText.setText("")
            }

            buttonPaste.setOnClickListener {
                urlEditText.setText(viewModel.pasteFromClipboard(requireContext()))
            }
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
        if (event.success) {

        }
    }
}