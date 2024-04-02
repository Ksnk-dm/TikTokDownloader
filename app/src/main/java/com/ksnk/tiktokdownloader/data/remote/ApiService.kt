package com.ksnk.tiktokdownloader.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("api/")
    suspend fun getApiValue(@Query("url") fileUrl: String): Response<ResponseBody>
}