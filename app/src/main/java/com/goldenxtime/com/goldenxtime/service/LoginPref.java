package com.goldenxtime.com.goldenxtime.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

public class LoginPref {

    private SharedPreferences prefs;

    private static final String KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN";
    private static final String LOGIN_PREF = "LOGIN_PREF";

    private String accessToken;

    public LoginPref(Context context) {
        try {
            prefs = context.getApplicationContext().getSharedPreferences(LOGIN_PREF, context.MODE_PRIVATE);
            accessToken = prefs.getString(KEY_ACCESS_TOKEN, null);
        } catch (Exception e) {}
    }

    public void setAccessToken(@NonNull Context context, String accessToken) {
        try {
            if (!TextUtils.isEmpty(accessToken)) {
                this.accessToken = accessToken;
                prefs.edit().putString(KEY_ACCESS_TOKEN, accessToken).apply();
                setSecureConnection();
            }
        } catch (Exception e) {}
    }

    public void removeAccessToken(@NonNull Context context) {
        try {
            this.accessToken = null;
            prefs.edit().putString(KEY_ACCESS_TOKEN, null).apply();
            removeSecureConnection();
        } catch (Exception e) {}
    }

    public void setSecureConnection() {
        try {
            SetupService.createApi(new AuthInterceptor(this.accessToken));
        } catch (Exception e) {}
    }

    public void removeSecureConnection() {
        SetupService.createApi(null);
    }

    public String getAccessToken() {
        return accessToken;
    }
}
