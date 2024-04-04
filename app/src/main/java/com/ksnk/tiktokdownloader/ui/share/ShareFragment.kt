package com.ksnk.tiktokdownloader.ui.share

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.FileProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.ksnk.tiktokdownloader.R
import com.ksnk.tiktokdownloader.base.BaseFragment
import com.ksnk.tiktokdownloader.data.entity.FileEntity
import com.ksnk.tiktokdownloader.databinding.FragmentShareBinding
import java.io.File

class ShareFragment : BaseFragment(R.layout.fragment_share) {

    private val viewBinding by viewBinding(FragmentShareBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNav()
        with(viewBinding) {

            val jsonString = arguments?.getParcelable<FileEntity>(KEY_FILE_ENTITY)

            Glide.with(requireContext())
                .load("${jsonString?.data?.cover}$COVER_FORMAT")
                .into(imageView)

            textViewTitle.text = jsonString?.data?.title
            textViewAuthor.text = jsonString?.data?.author?.nickName
            buttonShare.setOnClickListener {
                arguments?.getString(KEY_FILE_PATH)?.let {
                    shareFile(it)
                }
            }

            toolbar.setNavigationOnClickListener {
                navigation.popBackStack()
            }

            buttonBack.setOnClickListener {
                navigation.popBackStack()
            }

            buttonPlay.setOnClickListener {
                navigation.openPlayerFragmentFromShare(jsonString?.data?.playUrl.toString())
            }
        }
    }

    private fun shareFile(fileUri: String) {
        val file = File(fileUri)
        val uri = FileProvider.getUriForFile(
            requireContext(),
            "${context?.packageName}$FILE_PROVIDER",
            file
        )

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            type = SHARE_TYPE
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(shareIntent, context?.getString(R.string.send_file_as)))
    }

    companion object {
        private const val COVER_FORMAT = ".webp"
        private const val KEY_FILE_ENTITY = "fileEntity"
        const val KEY_FILE_PATH = "filePath"
        const val KEY_FILE_URI = "fileUri"
        const val SHARE_TYPE = "video/*"
        const val FILE_PROVIDER = ".fileprovider"
    }
}