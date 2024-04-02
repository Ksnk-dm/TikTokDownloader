package com.ksnk.tiktokdownloader.data.remote

class RemoteDataSource (private val apiService: ApiService) {

    suspend fun responseBody(fileUrl: String) = apiService.getApiValue(fileUrl)
}