package com.genshuixue.student.jaeger;

import java.util.Iterator;
import java.util.Map;

import io.opentracing.propagation.TextMap;
import okhttp3.Request;

/**
 * created by sunshuo
 * on 2020/10/26
 */
public class RequestBuilderCarrier implements TextMap {
    private final Request.Builder mBuilder;
    public RequestBuilderCarrier(Request.Builder builder) {
        mBuilder = builder;
    }
    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        return null;
    }

    @Override
    public void put(String key, String value) {
        mBuilder.addHeader(key, value);
    }
}
