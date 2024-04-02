package com.ksnk.tiktokdownloader.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.ksnk.tiktokdownloader.data.entity.FileEntity
import com.ksnk.tiktokdownloader.data.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber


class DownloadRepository(private val remoteDataSource: RemoteDataSource, private val gson: Gson) {

    private val fileEntityMutableLiveData = MutableLiveData<FileEntity>()

    fun fileEntityLiveData(): LiveData<FileEntity> =
        fileEntityMutableLiveData

    suspend fun getApiData(shortenedUrl: String) = withContext(Dispatchers.IO) {
        runCatching {
            val jsonData = remoteDataSource.responseBody(shortenedUrl).body()?.string()
            val fileEntity = gson.fromJson(jsonData, FileEntity::class.java)

            fileEntityMutableLiveData.postValue(fileEntity)
        }.onSuccess {
            Timber.d("Api data send in postValue")
        }.onFailure {
            Timber.e(it)
        }
    }
}