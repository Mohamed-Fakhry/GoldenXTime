package com.goldenxtime.com.goldenxtime.service.model.profile;

import com.goldenxtime.com.goldenxtime.model.User;
import com.goldenxtime.com.goldenxtime.service.SetupService;
import com.goldenxtime.com.goldenxtime.service.responed.ResponedReqestPhoneVerify;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileModelImpl implements ProfileModel {

    OnGetUserFinish listener;

    @Override
    public void getUser(final OnGetUserFinish listener) {
        this.listener = listener;

        SetupService.getService.getUser().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {
                    if (response.isSuccessful())
                        listener.onSuccess(response.body());
                    else if (response.code() == 401)
                        listener.onAccountStopped();
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                listener.onCanceled();
            }
        });
    }

    @Override
    public void requestVerifyPhone(final OnGetUserFinish onGetUserFinish) {
        SetupService.getService.sendRequestVerifyCode().enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                onGetUserFinish.onCodeSent();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                onGetUserFinish.onCodeFail();
            }
        });
    }

    @Override
    public void sentCode(JsonObject code, final OnGetUserFinish onGetUserFinish) {
        SetupService.getService.getAnswerVerifyPhone(code).enqueue(new Callback<ResponedReqestPhoneVerify>() {
            @Override
            public void onResponse(Call<ResponedReqestPhoneVerify> call, Response<ResponedReqestPhoneVerify> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {
                    onGetUserFinish.onGetVerifyUser("تم تاكيد رقم الجوال");
                } else {
                    onGetUserFinish.onCodeNoCorrect("الرقم خطا نرجو المحاولة مرة اخرى");
                }
            }

            @Override
            public void onFailure(Call<ResponedReqestPhoneVerify> call, Throwable t) {
                onGetUserFinish.onCodeNoCorrect("حدث خطا ما يرجى المحاولة مرة اخرى");
            }
        });
    }
}
