package com.ksnk.tiktokdownloader.ui.history

import android.app.Application
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.ksnk.tiktokdownloader.base.BaseViewModel
import com.ksnk.tiktokdownloader.data.repository.DownloadRepository
import com.ksnk.tiktokdownloader.ui.MediaModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.internal.concurrent.formatDuration

class HistoryViewModel(
    private val repository: DownloadRepository,
    application: Application
) : BaseViewModel(application) {

    suspend fun getVideosFromFolder(context: Context, folderPath: String): ArrayList<MediaModel> = withContext(
        Dispatchers.IO) {
        val videoList: ArrayList<MediaModel> = ArrayList()
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE
        )

        val selection = "${MediaStore.Video.Media.DATA} like ?"
        val selectionArgs = arrayOf("$folderPath%")

        val sortOrder = "${MediaStore.Video.Media.DATE_MODIFIED} DESC"

        val queryUri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

        context.contentResolver.query(queryUri, projection, selection, selectionArgs, sortOrder)
            ?.use { cursor ->
                while (cursor.moveToNext()) {
                    val videoId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID))
                    val displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME))
                    val duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION))
                    val size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE))

                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        videoId
                    )
                    videoList.add(MediaModel(displayName, contentUri, formatDuration(duration), formatSize(size)))
                }
            }
        return@withContext videoList
    }

    private fun formatDuration(duration: Long): String {
        val seconds = duration / 1000
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60

        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

    private fun formatSize(size: Long): String {
        val fileSizeInKB = size / 1024
        val fileSizeInMB = fileSizeInKB / 1024

        return if (fileSizeInMB > 0) {
            String.format("%d MB", fileSizeInMB)
        } else {
            String.format("%d KB", fileSizeInKB)
        }
    }
}