package com.ksnk.tiktokdownloader.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiService {

    @GET
    suspend fun downloadFile(@Url fileUrl: String): Response<ResponseBody>
}