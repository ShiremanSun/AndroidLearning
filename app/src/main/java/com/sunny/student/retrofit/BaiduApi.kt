package com.sunny.student.retrofit

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface BaiduApi {

    @GET("/s")
    suspend fun getBaiduHtmlSuspend() : Response<String>

    @GET("/s")
    fun getBaiduHtmlNormal() : Call<String>

}