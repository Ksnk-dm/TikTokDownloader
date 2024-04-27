package com.ksnk.tiktokdownloader.ui.download

import android.app.Application
import android.app.DownloadManager
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ksnk.tiktokdownloader.base.BaseViewModel
import com.ksnk.tiktokdownloader.data.model.FileInfo
import com.ksnk.tiktokdownloader.data.repository.DownloadRepository
import com.ksnk.tiktokdownloader.ui.download.DownloadFragment.Companion.DOWNLOAD_VIDEOS_DIRECTORY
import com.ksnk.tiktokdownloader.ui.download.DownloadFragment.Companion.FILE_FORMAT
import com.ksnk.tiktokdownloader.ui.download.DownloadFragment.Companion.MIME_TYPE
import kotlinx.coroutines.launch
import java.io.File

class DownloadViewModel(
    private val repository: DownloadRepository,
    private val application: Application
) : BaseViewModel(application) {

    private val expandedUrlLiveData = repository.fileInfoModelLiveData()

    fun getExpandedUrlLiveData(): LiveData<FileInfo> =
        expandedUrlLiveData

    fun expandShortenedUrl(shortenedUrl: String) {
        viewModelScope.launch {
            repository.getApiData(shortenedUrl)
        }
    }

    fun downloadVideo(fileInfo: FileInfo, context: Context): File? = runCatching {
        if (!DOWNLOAD_VIDEOS_DIRECTORY.exists()) {
            DOWNLOAD_VIDEOS_DIRECTORY.mkdirs()
        }
        val dm = getSystemService(context, DownloadManager::class.java)
        val downloadUri = Uri.parse(fileInfo.data?.playUrl)
        val request = DownloadManager.Request(downloadUri)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setAllowedOverRoaming(false)
            .setTitle(fileInfo.data?.playUrl)
            .setMimeType(MIME_TYPE)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationUri(Uri.fromFile(File(DOWNLOAD_VIDEOS_DIRECTORY, "${fileInfo.data?.id}$FILE_FORMAT")))

        dm?.enqueue(request)

        Toast.makeText(context, "Download started!", Toast.LENGTH_SHORT).show()
        File(DOWNLOAD_VIDEOS_DIRECTORY, "${fileInfo.data?.id}$FILE_FORMAT")
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
}