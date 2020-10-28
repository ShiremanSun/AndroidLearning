package com.genshuixue.student.jaeger;

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
        // 怎么改变request里的header
        // 第一个办法，将该方法前置
        //TODO
        Request.Builder builder = mRequest.newBuilder(); // 就是将request本身复制了一份，headers变成了新对象
        builder.addHeader(key, value);
        builder.build();
    }
}
