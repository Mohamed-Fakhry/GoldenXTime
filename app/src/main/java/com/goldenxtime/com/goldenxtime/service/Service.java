package com.goldenxtime.com.goldenxtime.service;

import com.goldenxtime.com.goldenxtime.model.Message;
import com.goldenxtime.com.goldenxtime.model.Property;
import com.goldenxtime.com.goldenxtime.model.User;
import com.goldenxtime.com.goldenxtime.service.responed.ResponedLogin;
import com.goldenxtime.com.goldenxtime.service.responed.ResponedRent;
import com.goldenxtime.com.goldenxtime.service.responed.ResponedReqestPhoneVerify;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Service {

    @POST("api/1/login")
    Call<ResponedLogin> loginService(@Body JsonObject user);

    @GET("api/1/m/profile")
    Call<User> getUser();

    @GET("api/1/m/properties")
    Call<ArrayList<Property>> getProperties();

    @GET("api/1/m/properties/{id}/rent")
    Call<ResponedRent> getRent(@Path("id") String id);

    @GET("api/1/m/properties/{id}")
    Call<Property> getProperity(@Path("id") int id);

    @POST("api/1/m/register-device")
    Call<Void> sendToken(@Body JsonObject token);

    @POST("api/1/reset-password")
    Call<Void> resetPassword(@Body JsonObject email);

    @GET("api/1/m/notifications")
    Call<ArrayList<Message>> getNotifications();

    @GET("api/1/m/request-verify-code")
    Call<Void> sendRequestVerifyCode();

    @POST("api/1/m/verify-code")
    Call<ResponedReqestPhoneVerify> getAnswerVerifyPhone(@Body JsonObject code);
}
