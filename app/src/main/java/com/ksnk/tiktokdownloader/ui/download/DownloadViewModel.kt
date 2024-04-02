package com.ksnk.tiktokdownloader.ui.download

import android.app.Application
import android.app.DownloadManager
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ksnk.tiktokdownloader.base.BaseViewModel
import com.ksnk.tiktokdownloader.data.entity.FileEntity
import com.ksnk.tiktokdownloader.data.repository.DownloadRepository
import kotlinx.coroutines.launch
import java.io.File

class DownloadViewModel(
    private val repository: DownloadRepository,
    application: Application
) : BaseViewModel(application) {

    private val expandedUrlLiveData = repository.fileEntityLiveData()
    fun getExpandedUrlLiveData(): LiveData<FileEntity> =
        expandedUrlLiveData

    fun expandShortenedUrl(shortenedUrl: String) {
        viewModelScope.launch {
            repository.getApiData(shortenedUrl)
        }
    }

    fun downloadVideo(fileEntity: FileEntity, context: Context): File? = runCatching {
        if (!DOWNLOAD_VIDEOS_DIRECTORY.exists()) {
            DOWNLOAD_VIDEOS_DIRECTORY.mkdirs()
        }
        val dm = getSystemService(context, DownloadManager::class.java)
        val downloadUri = Uri.parse(fileEntity.data?.playUrl)
        val request = DownloadManager.Request(downloadUri)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setAllowedOverRoaming(false)
            .setTitle(fileEntity.data?.playUrl)
            .setMimeType(MIME_TYPE)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationUri(Uri.fromFile(File(DOWNLOAD_VIDEOS_DIRECTORY, "${fileEntity.data?.id}$FILE_FORMAT")))

        dm?.enqueue(request)

        Toast.makeText(context, "Download started!", Toast.LENGTH_SHORT).show()
        File(DOWNLOAD_VIDEOS_DIRECTORY, "${fileEntity.data?.id}$FILE_FORMAT")
    }.onFailure { e ->
        Toast.makeText(context, "Download failed! ${e.message}", Toast.LENGTH_SHORT).show()
    }.getOrNull()

    fun pasteFromClipboard(context: Context): String? {
        var clipBoardText: String? = null
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (clipboard.hasPrimaryClip()) {
            val clip = clipboard.primaryClip
            val item = clip?.getItemAt(0)
            clipBoardText = item?.text.toString()
        }
        return clipBoardText
    }

    companion object {
        val DOWNLOAD_VIDEOS_DIRECTORY = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "TIKTOK DOWNLOADS/VIDEOS"
        )

        private const val MIME_TYPE = "video/*"
        private const val FILE_FORMAT = ".mp4"

    }
}