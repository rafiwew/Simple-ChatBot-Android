package com.piwew.mychatbot.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("v1/completions")
    fun getResponse(
        @Body request: ReqBody,
    ): Call<ChatResponse>
}