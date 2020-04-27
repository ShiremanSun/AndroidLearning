package com.sunny.student.okhttp

import okhttp3.*
import java.io.IOException

/**
 * Created by SunShuo.
 * Date: 2019-10-25
 * Time: 10:58
 */
object OKHttp {

    val client = OkHttpClient()
    fun get(url : String) : Call{
        val request = Request.Builder()
                .url(url)
                .build()

        val call = client.newCall(request)
        call.enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
            }

        })
        return call
    }


}