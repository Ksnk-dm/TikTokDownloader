package com.ksnk.tiktokdownloader.ui.share

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ksnk.tiktokdownloader.R
import com.ksnk.tiktokdownloader.databinding.FragmentDownloadBinding

class ShareFragment : Fragment(R.layout.fragment_share) {

    private val viewBinding by viewBinding(FragmentDownloadBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {

        }
    }

    private fun shareFile(fileUri: Uri) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, fileUri)
            type = "file/*" // Меняем тип на "file/*" чтобы указать, что отправляем файл
        }
        startActivity(Intent.createChooser(shareIntent, "Поделиться файлом через..."))
    }
}