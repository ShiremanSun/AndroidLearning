package com.sunshuo.grpc.jaeger.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sunshuo.grpc.jaeger.R
import com.sunshuo.grpc.jaeger.JaegerUtil
import com.sunshuo.grpc.jaeger.NetWorkListener
import kotlinx.android.synthetic.main.activity_jaeger_test.*
import okhttp3.*
import java.io.IOException

class JaegerTestActivity : AppCompatActivity() {
    companion object{
        var client : OkHttpClient? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jaeger_test)

        JaegerUtil.getTracer()
        test.setOnClickListener {

            val request = Request.Builder().url("http://172.20.116.11:8888")
            if (client == null) {
                client = OkHttpClient.Builder()
                        .eventListenerFactory(NetWorkListener.get())
                        .build()
            }

            client?.newCall(request.build())?.enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("jaeger", e.message)
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.d("jaeger", response.body?.string())
                }

            })

        }
    }
}