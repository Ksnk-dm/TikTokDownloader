package com.ksnk.tiktokdownloader.ui.download

import android.app.Application
import android.app.DownloadManager
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ksnk.tiktokdownloader.BaseViewModel
import com.ksnk.tiktokdownloader.Navigation
import com.ksnk.tiktokdownloader.data.DownloadRepository
import kotlinx.coroutines.launch
import java.io.File

class DownloadViewModel(private val repository: DownloadRepository, application: Application, private val navigationHelper: Navigation) : BaseViewModel(application) {

    private val expandedUrlLiveData = repository.getExpandedUrlLiveData()
    fun getExpandedUrlLiveData(): LiveData<String> {
        return expandedUrlLiveData
    }

    fun expandShortenedUrl(shortenedUrl: String) {
        viewModelScope.launch {
            repository.expandShortenedUrl(shortenedUrl)
        }
    }

    fun extractVideoIdFromUrl(url: String): String? {
        val pattern = Regex("/video/(\\d+)")
        val matchResult = pattern.find(url)
        return matchResult?.groupValues?.getOrNull(1)
    }

   fun downloadVideo(id: String, context: Context) {
        val VIDEOS = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "TIKTOK VIDEO DOWNLOADER/VIDEOS"
        )
        try {
            if (!VIDEOS.exists()) {
                VIDEOS.mkdirs()
            }

            val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadUri = Uri.parse("https://www.tikwm.com/video/media/play/$id.mp4")
            val request = DownloadManager.Request(downloadUri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(id)
                .setMimeType("video/*")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationUri(Uri.fromFile(File(VIDEOS, "$id.mp4")))

            dm.enqueue(request)

            Toast.makeText(context, "Download started!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Download failed! ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

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

    fun openShareFragment() =
        navigationHelper.openShareFragmentFromDownload()
}