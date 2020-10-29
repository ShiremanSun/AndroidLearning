package com.sunshuo.grpc.jaeger;

import java.io.IOException;

import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * created by sunshuo
 * on 2020/10/26
 */
public class TracerInterceptor implements Interceptor {

    private Tracer mTracer;
    public TracerInterceptor(Tracer tracer) {
        mTracer = tracer;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = null;
        Span totalSpan = mTracer.buildSpan(chain.request().url().toString()).start();
        totalSpan.setTag("test", "test");
        Request.Builder builder = chain.request().newBuilder();
        mTracer.inject(totalSpan.context(), Format.Builtin.HTTP_HEADERS, new RequestBuilderCarrier(chain.request()));
        try {
            response = chain.proceed(builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            totalSpan.finish();
        }
        return response;
    }
}
