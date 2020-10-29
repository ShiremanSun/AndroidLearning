package com.sunshuo.grpc.jaeger

import io.opentracing.util.GlobalTracer
import org.junit.Test

/**
 * created by sunshuo
 * on 2020/10/28
 */
class JaegerTest {
    @Test
    fun jaegerTest() {
        JaegerUtil.getTracer()
        val totalSpan = GlobalTracer.get().buildSpan("student").start()
        totalSpan.log("start")
        for (i in 0..6) {
            val span = GlobalTracer.get().buildSpan("student").asChildOf(totalSpan).start()
            span.log("spanStart")
            Thread.sleep(1000)
            span.log("spanFinish")
            span.finish()
        }
        totalSpan.log("end")
        totalSpan.finish()
    }
}