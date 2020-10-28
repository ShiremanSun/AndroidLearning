package com.genshuiue.student.jaeger

import com.genshuixue.student.jaeger.JaegerUtil
import com.genshuixue.student.jaeger.NetWorkListener
import com.genshuixue.student.jaeger.TracerInterceptor
import io.opentracing.Tracer
import io.opentracing.util.GlobalTracer
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
        val url = "https://sapi.genshuixue.com//sapi/viewLogic/homepage/clazzCartCount?os=android27&l-mac=&channel=GenShuiXue&version=4.4.2.2&uuid=9205322882962650&platform=androidPBAM00&did=bjhl9205322882962650&oaid=&l-imei=9205322882962650&cid=2100012&timestamp=1603702179072&os=android27&l-mac=&channel=GenShuiXue&version=4.4.2.2&uuid=9205322882962650&platform=androidPBAM00&did=bjhl9205322882962650&oaid=&l-imei=9205322882962650&cid=2100012&timestamp=1603702179072"
        val client = OkHttpClient.Builder()
                //.addInterceptor(TracerInterceptor(tracer))
                .eventListenerFactory(NetWorkListener.get())
                .build()
        val request = Request.Builder().url(url).get().build();
        val response = client.newCall(request).execute()

    }
}