package com.nabeeltech.retrofitcodinginflow;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class okHttpClient
{
    OkHttpClient okHttpClient;
    HttpLoggingInterceptor loggingInterceptor;

    public okHttpClient()
    {
        loggingInterceptor = new HttpLoggingInterceptor();
    }

    public OkHttpClient getOkHttpClient() {
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                        Request originalRequest = chain.request();

                        Request newRequest = originalRequest.newBuilder()
                                .header("Default-Interceptor-Header", "in all requests")  //this is default header in all requests
                                .build();

                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(loggingInterceptor)
                .build();
        return okHttpClient;
    }
}
