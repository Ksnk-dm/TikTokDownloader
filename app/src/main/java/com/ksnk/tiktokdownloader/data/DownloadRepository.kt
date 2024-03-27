package com.ksnk.tiktokdownloader.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ksnk.tiktokdownloader.data.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Response
import java.net.SocketTimeoutException


class DownloadRepository(private val remoteDataSource: RemoteDataSource) {

    private val expandedUrlLiveData = MutableLiveData<String>()

    fun getExpandedUrlLiveData(): LiveData<String> =
        expandedUrlLiveData


    suspend fun expandShortenedUrl(shortenedUrl: String) = withContext(Dispatchers.IO) {
        try {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(shortenedUrl)
                .build()

            val response = client.newCall(request).execute()
            val expandedUrl = response.request.url.toString()
            expandedUrlLiveData.postValue(expandedUrl)
        } catch (e: SocketTimeoutException) {
            expandedUrlLiveData.postValue("Timeout occurred: ${e.message}")
        } catch (e: Exception) {
            expandedUrlLiveData.postValue("Error: ${e.message}")
        }
    }
}