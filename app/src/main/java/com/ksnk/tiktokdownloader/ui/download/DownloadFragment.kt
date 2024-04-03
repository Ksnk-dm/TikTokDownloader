package com.ksnk.tiktokdownloader.ui.download

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ksnk.tiktokdownloader.events.DownloadEvent
import com.ksnk.tiktokdownloader.utils.Navigation
import com.ksnk.tiktokdownloader.R
import com.ksnk.tiktokdownloader.base.BaseFragment
import com.ksnk.tiktokdownloader.databinding.FragmentDownloadBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.io.File

class DownloadFragment : BaseFragment(R.layout.fragment_download) {

    private val viewBinding by viewBinding(FragmentDownloadBinding::bind)
    private val viewModel: DownloadViewModel by inject()
    private var isFileDownloaded = false

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
                    Toast.makeText(requireContext(), getText(R.string.empty_url), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                urlEditText.text?.let { text ->
                    if (!text.contains(CONTAINS, true)) {
                        Toast.makeText(requireContext(), getText(R.string.bad_link), Toast.LENGTH_SHORT).show()
                        urlEditText.setText("")
                        return@setOnClickListener
                    }
                }
                isFileDownloaded = false
                viewModel.expandShortenedUrl(urlEditText.text.toString())
                viewModel.getExpandedUrlLiveData()
                    .observe(viewLifecycleOwner) { fileEntity ->
                        if (!isFileDownloaded) {
                            navigation.openShareFragmentFromDownload(
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

    companion object {
        private const val CONTAINS = "tiktok"
            val DOWNLOAD_VIDEOS_DIRECTORY = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "TIKTOK DOWNLOADS/VIDEOS"
            )
            const val MIME_TYPE = "video/*"
            const val FILE_FORMAT = ".mp4"
    }
}