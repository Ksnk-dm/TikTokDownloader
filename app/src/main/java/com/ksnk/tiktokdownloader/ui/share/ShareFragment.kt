package com.ksnk.tiktokdownloader.ui.share

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.ksnk.tiktokdownloader.R
import com.ksnk.tiktokdownloader.databinding.FragmentShareBinding
import java.io.File

class ShareFragment : Fragment(R.layout.fragment_share) {

    private val viewBinding by viewBinding(FragmentShareBinding::bind)
    val SOURCE_IMAGEVIEW = "https://www.tikwm.com/video/cover/"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding) {

            Glide.with(requireContext())
                .load("${SOURCE_IMAGEVIEW}${arguments?.getString("id")}.webp")
                .into(imageView)

            buttonShare.setOnClickListener {
                arguments?.getString("your_key")?.let {
                    shareFile(it)
                }
            }
        }
    }

    private fun shareFile(fileUri: String) {
        val file = File(fileUri)
        val uri = FileProvider.getUriForFile(requireContext(), "com.ksnk.tiktokdownloader.fileprovider", file)

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "video/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Grant temporary read permission to the content URI
        }
        startActivity(Intent.createChooser(shareIntent, "Поделиться файлом через..."))
    }
}