package com.myproject.repaircar.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myproject.repaircar.consts.ServiceUrl;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Semicolon07 on 9/29/2016 AD.
 */

public class HttpManager {
    private static HttpManager instance;
    private MyApi service;

    public static HttpManager getInstance() {
        if (instance == null)
            instance = new HttpManager();
        return instance;
    }

    private HttpManager() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logging);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .setLenient()
                .create();

        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ServiceUrl.BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(httpClientBuilder.build())
                    .build();
            service = retrofit.create(MyApi.class);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public MyApi getService() {
        return service;
    }
}
