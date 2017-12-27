package com.goldenxtime.com.goldenxtime.view.login;

public interface LoginView {

    void showProgress(boolean showProgress);

    void setUsernameOrPasswordError(int messageResId);

    void successAction(String token);

    void onAccountStopped();

    void noInternetConnection();

    boolean isNetworkAvailable();
}
