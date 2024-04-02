package com.ksnk.tiktokdownloader.ui.share

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.ksnk.tiktokdownloader.utils.Navigation
import com.ksnk.tiktokdownloader.R
import com.ksnk.tiktokdownloader.data.entity.FileEntity
import com.ksnk.tiktokdownloader.databinding.FragmentShareBinding
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.io.File

class ShareFragment : Fragment(R.layout.fragment_share) {

    private val viewBinding by viewBinding(FragmentShareBinding::bind)
    private val navigator: Navigation by inject { parametersOf(requireParentFragment()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding) {

            val jsonString = arguments?.getParcelable<FileEntity>(KEY_FILE_ENTITY)

            Glide.with(requireContext())
                .load("${jsonString?.data?.cover}$COVER_FORMAT")
                .into(imageView)

            textViewTitle.text = jsonString?.data?.title
            buttonShare.setOnClickListener {
                arguments?.getString(KEY_FILE_URI)?.let {
                    shareFile(it)
                }
            }

            toolbar.setNavigationOnClickListener {
                navigator.popBackStack()
            }
        }
    }

    private fun shareFile(fileUri: String) {
        val file = File(fileUri)
        val uri = FileProvider.getUriForFile(requireContext(), "${context?.packageName}$FILE_PROVIDER", file)

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            type = SHARE_TYPE
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(shareIntent, "Поделиться файлом через..."))
    }


    companion object {
        private const val COVER_FORMAT = ".webp"
        private const val KEY_FILE_ENTITY = "fileEntity"
        private const val KEY_FILE_URI = "fileUri"
        private const val SHARE_TYPE = "video/*"
        private const val FILE_PROVIDER = ".fileprovider"
    }
}