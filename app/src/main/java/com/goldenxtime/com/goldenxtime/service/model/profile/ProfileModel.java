package com.goldenxtime.com.goldenxtime.service.model.profile;

import com.goldenxtime.com.goldenxtime.model.User;
import com.google.gson.JsonObject;

public interface ProfileModel {

    interface OnGetUserFinish {
        void onCanceled();

        void onSuccess(User user);

        void onAccountStopped();

        void onCodeSent();

        void onCodeFail();

        void onGetVerifyUser(String message);

        void onCodeNoCorrect(String message);
    }

    void getUser(OnGetUserFinish onGetUserFinish);

    void requestVerifyPhone(OnGetUserFinish onGetUserFinish);

    void sentCode(JsonObject code, OnGetUserFinish onGetUserFinish);
}
