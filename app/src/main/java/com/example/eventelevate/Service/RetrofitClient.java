package com.example.eventelevate.Service;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    private static String BASE_URL = "https://eventpeak.000webhostapp.com";
    private static String SUB_URL = "/Events/Api/index.php/";
    private static String LOCATION = "https://services.feverup.com/api/4.1/";

    public static Retrofit getRetrofitInstance() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL+SUB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        return retrofit;
    }

    public static Retrofit getRetrofitInstanceforlocation() {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request newRequest = originalRequest.newBuilder()
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjk3MjUyMjYzLCJpYXQiOjE2OTcyMDM1MjAsImp0aSI6IjU4YjE0ZjNhODliNDRhNzc4MGRkODU3YzNjYmE5NjkzIiwidXNlcl9pZCI6NTMwODkzNjJ9.PbSSDMKpXc6rrlOI1EzZfmCRZttekraLJ_3nkmSsbzo")
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl(LOCATION)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

}
