package com.ksnk.tiktokdownloader.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @POST("api/")
    suspend fun downloadFile(@Query("url") fileUrl: String): Response<ResponseBody>
}