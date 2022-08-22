package com.example.httptest.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstance {

    //TODO: Replace with your base URL
    public static String BASE_URL = "https://frontend-346800.wn.r.appspot.com/";//http://192.168.1.14:4000/ http://localhost:4000/             https://frontend-346800.wn.r.appspot.com/

    private static Retrofit retrofit;

    public static Retrofit getRetroClient() {
        OkHttpClient okhttpclient = new OkHttpClient.Builder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .build();
        if(retrofit == null ) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .callFactory(okhttpclient)
                    .build();
        }
        return retrofit;
    }
}
