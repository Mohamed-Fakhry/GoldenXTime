package com.goldenxtime.com.goldenxtime.service.model.login;

public interface LoginModel {

    interface OnLoginFinishedListener {

        void onCanceled();

        void onSuccess(String token);

        void onAccountStopped();
    }

    interface OnRestPasswordFinishedListener {

        void onCanceled();

        void onSuccess();

        void onAccountStopped();
    }

    void login(String username, String password, OnLoginFinishedListener listener);

    void restPassword(String email, OnRestPasswordFinishedListener listener);


}
