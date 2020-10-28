package com.genshuixue.student.jaeger.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.genshuiue.student.jaeger.R
import com.genshuixue.student.jaeger.JaegerUtil
import com.genshuixue.student.jaeger.NetWorkListener
import kotlinx.android.synthetic.main.activity_jaeger_test.*
import okhttp3.*
import java.io.IOException

class JaegerTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jaeger_test)

        JaegerUtil.getTracer()
        test.setOnClickListener {
            val url = "https://sapi.genshuixue.com//sapi/viewLogic/homepage/clazzCartCount?os=android27&l-mac=&channel=GenShuiXue&version=4.4.2.2&uuid=9205322882962650&platform=androidPBAM00&did=bjhl9205322882962650&oaid=&l-imei=9205322882962650&cid=2100012&timestamp=1603702179072&os=android27&l-mac=&channel=GenShuiXue&version=4.4.2.2&uuid=9205322882962650&platform=androidPBAM00&did=bjhl9205322882962650&oaid=&l-imei=9205322882962650&cid=2100012&timestamp=1603702179072"
            val client = OkHttpClient.Builder()
                    //.addInterceptor(TracerInterceptor(tracer))
                    .eventListenerFactory(NetWorkListener.get())
                    .build()
            val request = Request.Builder().url(url).get().build();
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.d("jaeger", response.body()?.string())
                }

            })

        }
    }
}