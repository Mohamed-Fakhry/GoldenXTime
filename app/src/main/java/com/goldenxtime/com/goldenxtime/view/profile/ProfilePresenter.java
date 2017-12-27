package com.goldenxtime.com.goldenxtime.view.profile;

import com.goldenxtime.com.goldenxtime.model.User;
import com.goldenxtime.com.goldenxtime.service.model.profile.ProfileModel;
import com.goldenxtime.com.goldenxtime.service.model.profile.ProfileModelImpl;
import com.google.gson.JsonObject;

public class ProfilePresenter implements ProfileContract.Presenter, ProfileModel.OnGetUserFinish {

    ProfileContract.View profileView;
    ProfileModel profileModel;

    public ProfilePresenter(ProfileContract.View profileView) {
        this.profileView = profileView;
        this.profileModel = new ProfileModelImpl();
    }

    @Override
    public void getUser() {
        if (profileView.isNetworkAvailable()) {
            profileView.setProgressB(true);
            profileModel.getUser(this);
        } else {
            profileView.setProgressB(false);
            profileView.noInternetConnection();
        }
    }

    @Override
    public void verifyPhone() {
        if (profileView.isNetworkAvailable()) {
            profileView.setProgressB(true);
            profileModel.requestVerifyPhone(this);
        } else {
            profileView.noInternetConnection();
        }
    }

    @Override
    public void sendCode(int code) {
        if (profileView.isNetworkAvailable()) {
            profileView.setProgressB(true);
            JsonObject codeJsonObject = new JsonObject();
            codeJsonObject.addProperty("code", code);
            profileModel.sentCode(codeJsonObject, this);
        } else {
            profileView.noInternetConnection();
        }
    }

    @Override
    public void onCanceled() {
        profileView.setProgressB(false);
    }

    @Override
    public void onSuccess(User user) {
        profileView.setProgressB(false);
        profileView.showProfileDetails(user);
    }

    @Override
    public void onAccountStopped() {
        profileView.setProgressB(false);
        profileView.onAccountStopped();
    }

    @Override
    public void onCodeSent() {
        profileView.setProgressB(false);
        profileView.onRequestCodeSentSuccess();
    }

    @Override
    public void onCodeFail() {
        profileView.setProgressB(false);
        profileView.onRequestCodeSentFail();
    }

    @Override
    public void onGetVerifyUser(String message) {
        profileView.setProgressB(false);
        profileView.onCodeSuccess(message);
    }

    @Override
    public void onCodeNoCorrect(String message) {
        profileView.setProgressB(false);
        profileView.onCodeFail(message);
    }
}