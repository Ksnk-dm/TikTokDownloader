package com.ksnk.tiktokdownloader.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ksnk.tiktokdownloader.data.entity.FileEntity
import com.ksnk.tiktokdownloader.data.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.SocketTimeoutException


class DownloadRepository(private val remoteDataSource: RemoteDataSource, private val gson: Gson) {

    private val expandedUrlLiveData = MutableLiveData<FileEntity>()

    fun getExpandedUrlLiveData(): LiveData<FileEntity> =
        expandedUrlLiveData

    suspend fun expandShortenedUrl(shortenedUrl: String) = withContext(Dispatchers.IO) {
        try {
            val jsonData = remoteDataSource.downloadFile(shortenedUrl).body()?.string()
            val fileEntity: FileEntity = gson.fromJson(jsonData, FileEntity::class.java)

            expandedUrlLiveData.postValue(fileEntity)
        } catch (e: SocketTimeoutException) {
           // expandedUrlLiveData.postValue("Timeout occurred: ${e.message}")
        } catch (e: Exception) {
          //  expandedUrlLiveData.postValue("Error: ${e.message}")
        }
    }
}