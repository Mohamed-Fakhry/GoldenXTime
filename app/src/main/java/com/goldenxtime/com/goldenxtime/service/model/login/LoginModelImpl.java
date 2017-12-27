package com.goldenxtime.com.goldenxtime.service.model.login;

import com.goldenxtime.com.goldenxtime.service.SetupService;
import com.goldenxtime.com.goldenxtime.service.responed.ResponedLogin;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModelImpl implements LoginModel {

    OnLoginFinishedListener listener;

    @Override
    public void login(String username, String password, final OnLoginFinishedListener listener) {
        this.listener = listener;

        JsonObject user = new JsonObject();
        user.addProperty("username", username);
        user.addProperty("password", password);

        SetupService.getService.loginService(user).enqueue(new Callback<ResponedLogin>() {
            @Override
            public void onResponse(Call<ResponedLogin> call, Response<ResponedLogin> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body().getToken());
                } else if (response.code() == 401)
                    listener.onAccountStopped();
            }

            @Override
            public void onFailure(Call<ResponedLogin> call, Throwable t) {
                listener.onCanceled();
            }
        });
    }

    @Override
    public void restPassword(String email, final OnRestPasswordFinishedListener listener) {
        JsonObject emailObject = new JsonObject();
        emailObject.addProperty("email", email);
        SetupService.getService.resetPassword(emailObject).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess();
                } else if (response.code() == 404)
                    listener.onCanceled();
                else if (response.code() == 401)
                    listener.onAccountStopped();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onCanceled();
            }
        });
    }

}
