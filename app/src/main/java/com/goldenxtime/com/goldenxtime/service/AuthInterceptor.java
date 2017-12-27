package com.goldenxtime.com.goldenxtime.service;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private final String accessToken;

    public AuthInterceptor(@Nullable String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request request = original.newBuilder()
                .header("x-access-token", this.accessToken)
                .method(original.method(), original.body())
                .build();

        Response response = chain.proceed(request);

        return response ;
    }
}
