package com.sunshuo.grpc.jaeger

import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        JaegerUtil.getTracer()
        val url = "http://127.0.0.1:8888"
        val client = OkHttpClient.Builder()
                //.addInterceptor(TracerInterceptor(tracer))
                .eventListenerFactory(NetWorkListener.get())
                .build()
        val request = Request.Builder().url(url).get().build();
        val response = client.newCall(request).execute()

    }
}