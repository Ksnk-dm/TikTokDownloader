package com.ksnk.tiktokdownloader.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.ksnk.tiktokdownloader.data.model.FileInfo
import com.ksnk.tiktokdownloader.data.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber


class DownloadRepository(private val remoteDataSource: RemoteDataSource, private val gson: Gson) {

    private val fileInfoMutableLiveData = MutableLiveData<FileInfo>()

    fun fileInfoModelLiveData(): LiveData<FileInfo> =
        fileInfoMutableLiveData

    suspend fun getApiData(url: String) = withContext(Dispatchers.IO) {
        runCatching {
            fileInfoMutableLiveData.postValue(
                gson.fromJson(
                    remoteDataSource.responseBody(url).body()?.string(),
                    FileInfo::class.java
                )
            )
        }.onSuccess {
            Timber.d("Api data send in postValue")
        }.onFailure {
            Timber.e(it)
        }
    }
}