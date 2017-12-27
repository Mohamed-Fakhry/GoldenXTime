package com.goldenxtime.com.goldenxtime.view.profile;

import com.goldenxtime.com.goldenxtime.model.User;

public interface ProfileContract {
    
    interface View {
        void showProfileDetails(User user);

        void onAccountStopped();

        void onRequestCodeSentSuccess();

        void onRequestCodeSentFail();

        void onCodeSuccess(String message);

        void onCodeFail(String message);

        void setProgressB(boolean status);

        boolean isNetworkAvailable();

        void noInternetConnection();
    }
    
    interface Presenter {
        void getUser();

        void verifyPhone();

        void sendCode(int code);
    }
}