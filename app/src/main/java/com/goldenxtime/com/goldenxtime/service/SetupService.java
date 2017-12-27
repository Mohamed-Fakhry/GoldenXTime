package com.goldenxtime.com.goldenxtime.service;

import android.app.Application;
import android.content.Context;

import com.goldenxtime.com.goldenxtime.R;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class SetupService extends Application {

    public static Service getService;

    @Override
    public void onCreate() {
        super.onCreate();
        createApi(null);
    }

    static void createApi(AuthInterceptor authInterceptor) {
        final OkHttpClient client;

        if (authInterceptor == null) {
            client = new OkHttpClient.Builder()
                    .build();
        } else {
            client = new OkHttpClient.Builder()
                    .addInterceptor(
                            authInterceptor
                    ).build();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getService = retrofit.create(Service.class);
    }
}