package com.ksnk.tiktokdownloader.ui.download

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.EditText
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ksnk.tiktokdownloader.events.DownloadEvent
import com.ksnk.tiktokdownloader.R
import com.ksnk.tiktokdownloader.base.BaseFragment
import com.ksnk.tiktokdownloader.databinding.FragmentDownloadBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject
import java.io.File

class DownloadFragment : BaseFragment(R.layout.fragment_download) {

    private val viewBinding by viewBinding(FragmentDownloadBinding::bind)
    private val viewModel: DownloadViewModel by inject()
    private var isFileDownloaded = false

    @SuppressLint("QueryPermissionsNeeded")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showButtonNav()
        with(viewBinding) {
            activity?.intent?.let { text ->
                text.getStringExtra(Intent.EXTRA_TEXT)?.let {
                    customEndIcon.setText(it)
                }
            }

            buttonDownload.setOnClickListener {
                if (customEndIcon.text.isNullOrEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        getText(R.string.empty_url),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                customEndIcon.text?.let { text ->
                    if (!text.contains(CONTAINS, true)) {
                        Toast.makeText(
                            requireContext(),
                            getText(R.string.bad_link),
                            Toast.LENGTH_SHORT
                        ).show()
                        customEndIcon.setText("")
                        return@setOnClickListener
                    }
                }
                isFileDownloaded = false
                viewModel.expandShortenedUrl(customEndIcon.text.toString())
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
                customEndIcon.setText("")
            }

            buttonPaste.setOnClickListener {
                if (!checkContainsUrl(
                        viewModel.pasteFromClipboard(requireContext()),
                        customEndIcon
                    )
                ) return@setOnClickListener
                customEndIcon.setText(viewModel.pasteFromClipboard(requireContext()))
            }
            buttonOpen.setOnClickListener {
                if (!checkContainsUrl(
                        customEndIcon.text.toString(),
                        customEndIcon
                    )
                ) return@setOnClickListener
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(customEndIcon.text.toString())
                ).setPackage(TIK_TOK_PACKAGE_NAME)

                if (context?.packageManager?.let { it ->
                        intent.resolveActivity(it)
                    } != null)
                    startActivity(intent)
                else startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(customEndIcon.text.toString())
                    )
                )
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

    private fun checkContainsUrl(text: String?, editText: EditText): Boolean {
        text?.let {
            if (!it.contains(CONTAINS, true)) {
                Toast.makeText(
                    requireContext(),
                    getText(R.string.bad_link),
                    Toast.LENGTH_SHORT
                ).show()
                editText.setText("")
                return false
            }
        }
        return true
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
        private const val TIK_TOK_PACKAGE_NAME = "com.zhiliaoapp.musically"
    }
}