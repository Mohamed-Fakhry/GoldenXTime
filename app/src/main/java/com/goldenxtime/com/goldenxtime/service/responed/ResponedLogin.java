package com.goldenxtime.com.goldenxtime.service.responed;

import com.google.gson.annotations.SerializedName;

public class ResponedLogin {

        @SerializedName("accessToken")
        String token;


    public String getToken() {
        return token;
    }
}
