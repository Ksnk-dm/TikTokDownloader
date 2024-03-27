package com.ksnk.tiktokdownloader.data.remote

class RemoteDataSource (private val apiService: ApiService) {

    suspend fun downloadFile(fileUrl: String) = apiService.downloadFile(fileUrl)
}