package com.sunshuo.grpc.jaeger;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

import io.opentracing.propagation.TextMap;
import okhttp3.Request;

/**
 * created by sunshuo
 * on 2020/10/26
 */
public class RequestBuilderCarrier implements TextMap {
    public static Field headers;
    static {
        try {
            headers = Request.class.getDeclaredField("headers");
            headers.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    private final Request mRequest;
    public RequestBuilderCarrier(Request request) {
        mRequest = request;
    }
    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        return null;
    }

    /**
     * 发生在requestBuilder.build之后
     * @param key
     * @param value
     */
    @Override
    public void put(String key, String value) {
        Request.Builder builder = mRequest.newBuilder();
        builder.addHeader(key, value);
        if (headers != null) {
            try {
                headers.set(mRequest, builder.build().headers());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
