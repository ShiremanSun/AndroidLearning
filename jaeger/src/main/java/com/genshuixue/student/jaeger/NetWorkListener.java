package com.genshuixue.student.jaeger;

import android.util.Log;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMapAdapter;
import io.opentracing.util.GlobalTracer;
import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

/**
 * created by sunshuo
 * on 2020/10/26
 * 50:15.351 28144-28277/cn.com.zwwl.bayuwen D/NetworkEventListener: dnsStart
 * 2020-09-22 20:50:15.373 28144-28277/cn.com.zwwl.bayuwen D/NetworkEventListener: dnsEnd
 * 2020-09-22 20:50:15.374 28144-28277/cn.com.zwwl.bayuwen D/NetworkEventListener: connectStart
 * 2020-09-22 20:50:15.404 28144-28277/cn.com.zwwl.bayuwen D/NetworkEventListener: secureConnectStart
 * 2020-09-22 20:50:15.490 28144-28277/cn.com.zwwl.bayuwen D/NetworkEventListener: secureConnectEnd
 * 2020-09-22 20:50:15.490 28144-28277/cn.com.zwwl.bayuwen D/NetworkEventListener: connectEnd
 * 2020-09-22 20:50:15.492 28144-28277/cn.com.zwwl.bayuwen D/NetworkEventListener: requestHeadersStart
 * 2020-09-22 20:50:15.492 28144-28277/cn.com.zwwl.bayuwen D/NetworkEventListener: requestHeadersEnd
 * 2020-09-22 20:50:15.528 28144-28277/cn.com.zwwl.bayuwen D/NetworkEventListener: responseHeadersStart
 * 2020-09-22 20:50:15.528 28144-28277/cn.com.zwwl.bayuwen D/NetworkEventListener: responseHeadersEnd
 * 2020-09-22 20:50:15.532 28144-28277/cn.com.zwwl.bayuwen D/NetworkEventListener: responseBodyStart
 * 2020-09-22 20:50:15.534 28144-28277/cn.com.zwwl.bayuwen D/NetworkEventListener: responseBodyEnd
 * 2020-09-22 20:50:15.547 28144-28277/cn.com.zwwl.bayuwen D/NetworkEventListener: callEnd
 */
public class NetWorkListener extends EventListener {

    private static final String CALL = "call";
    private static final String DNS = "dns";
    private static final String CONNECT = "connect";
    private static final String SECURE_CONNECT = "secureConnect";
    private static final String REQUEST_HEADERS = "requestHeaders";
    private static final String RESPONSE_HEADERS = "responseHeaders";
    private static final String REQUEST_BODY = "requestBody";


    private static final String RESPONSE_BODY = "responseBody";



    // 必须为每一个请求创建一个EventListener
    private Span mTotalSpan;
    private Tracer mTracer;
    private Map<String, Span> mSpanMap;
    private Call mCall;
    private NetWorkListener(Call call, Tracer tracer) {
        mTracer = tracer;
        mCall = call;
        mSpanMap = new HashMap<>();
        mTotalSpan = mTracer.buildSpan("total").start();
        //
        Log.d("spanTime", String.valueOf(System.currentTimeMillis()));

        mTotalSpan.log(call.request().url().toString());
        mTracer.inject(mTotalSpan.context(), Format.Builtin.HTTP_HEADERS, new RequestBuilderCarrier(mCall.request()));
    }
    public static Factory get() {
        Factory factory = new Factory() {
            @Override
            public EventListener create(Call call) {
                return new NetWorkListener(call, GlobalTracer.get());
            }
        };
        return factory;
    }

    private static final String TAG = "NetworkEventListener";


    /**
     *
     * @param call
     */
    @Override
    public void callStart(@NotNull Call call) {
        super.callStart(call);
        buildChildSpan(CALL);
    }

    @Override
    public void dnsStart(@NotNull Call call, @NotNull String domainName) {
        super.dnsStart(call, domainName);
        buildChildSpan(DNS);
    }

    @Override
    public void connectStart(@NotNull Call call, @NotNull InetSocketAddress inetSocketAddress, @NotNull Proxy proxy) {
        super.connectStart(call, inetSocketAddress, proxy);
        buildChildSpan(CONNECT);
    }

    @Override
    public void secureConnectStart(@NotNull Call call) {
        super.secureConnectStart(call);
        buildChildSpan(SECURE_CONNECT);
    }

    @Override
    public void requestHeadersStart(@NotNull Call call) {
        super.requestHeadersStart(call);
        buildChildSpan(REQUEST_HEADERS);
    }

    @Override
    public void requestBodyStart(@NotNull Call call) {
        super.requestBodyStart(call);
        buildChildSpan(REQUEST_BODY);
    }

    @Override
    public void responseHeadersStart(@NotNull Call call) {
        super.responseHeadersStart(call);
        buildChildSpan(RESPONSE_HEADERS);
    }

    @Override
    public void responseBodyStart(@NotNull Call call) {
        super.responseBodyStart(call);
        buildChildSpan(RESPONSE_BODY);
    }


    @Override
    public void dnsEnd(@NotNull Call call, @NotNull String domainName, @NotNull List<InetAddress> inetAddressList) {
        super.dnsEnd(call, domainName, inetAddressList);
        finishSpan(DNS);
    }



    @Override
    public void secureConnectEnd(@NotNull Call call, @Nullable Handshake handshake) {
        super.secureConnectEnd(call, handshake);
        finishSpan(SECURE_CONNECT);
    }

    @Override
    public void connectEnd(@NotNull Call call, @NotNull InetSocketAddress inetSocketAddress,
                           @NotNull Proxy proxy, @Nullable Protocol protocol) {
        super.connectEnd(call, inetSocketAddress, proxy, protocol);
        finishSpan(CONNECT);
    }

    @Override
    public void connectFailed(@NotNull Call call, @NotNull InetSocketAddress inetSocketAddress, @NotNull Proxy proxy, @Nullable Protocol protocol, @NotNull IOException ioe) {
        super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe);
        finishSpan(CONNECT);
        mTotalSpan.finish();
    }



    @Override
    public void requestHeadersEnd(@NotNull Call call, @NotNull Request request) {
        super.requestHeadersEnd(call, request);
        finishSpan(REQUEST_HEADERS);
    }


    @Override
    public void requestBodyEnd(@NotNull Call call, long byteCount) {
        super.requestBodyEnd(call, byteCount);
        finishSpan(REQUEST_BODY);
    }



    @Override
    public void responseHeadersEnd(@NotNull Call call, @NotNull Response response) {
        super.responseHeadersEnd(call, response);
        finishSpan(RESPONSE_HEADERS);
    }


    @Override
    public void responseBodyEnd(@NotNull Call call, long byteCount) {
        super.responseBodyEnd(call, byteCount);
        finishSpan(RESPONSE_BODY);
    }

    @Override
    public void callEnd(@NotNull Call call) {
        super.callEnd(call);
        finishSpan(CALL);
        mTotalSpan.finish();
    }

    @Override
    public void callFailed(@NotNull Call call, @NotNull IOException ioe) {
        super.callFailed(call, ioe);
        finishSpan(CALL);
        mTotalSpan.finish();
    }

    @Override
    public void connectionReleased(Call call, Connection connection) {
        super.connectionReleased(call, connection);
    }

    private void buildChildSpan(String operation) {
        mSpanMap.put(operation, mTracer.buildSpan(operation).asChildOf(mTotalSpan).start());
    }

    private void finishSpan(String operation) {
        Span span = mSpanMap.get(operation);
        if (span != null) {
            span.finish();
        }
    }
}
