package com.sunny.student.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitProvider {
    public static final String TAG = "RetrofitProvider";

    public static Retrofit getInstance() {
        return new Retrofit.Builder()
                .baseUrl("https://www.baidu.com")
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(new OkHttpClient())
                .build();
    }
}
