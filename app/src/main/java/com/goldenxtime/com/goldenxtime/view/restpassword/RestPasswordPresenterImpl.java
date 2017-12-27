package com.goldenxtime.com.goldenxtime.view.restpassword;

import android.util.Log;

import com.goldenxtime.com.goldenxtime.R;
import com.goldenxtime.com.goldenxtime.service.model.login.LoginModel;
import com.goldenxtime.com.goldenxtime.service.model.login.LoginModelImpl;

public class RestPasswordPresenterImpl implements RestPasswordPresenter
        , LoginModel.OnRestPasswordFinishedListener {

    LoginModel loginModel;
    RestPasswordView restPasswordView;

    public RestPasswordPresenterImpl(RestPasswordView restPasswordView) {
        this.restPasswordView = restPasswordView;
        loginModel = new LoginModelImpl();
    }

    @Override
    public void sendEmail(String email) {
        loginModel.restPassword(email, this);
    }

    @Override
    public void onSuccess() {
        restPasswordView.showSuccessMessage();
    }

    @Override
    public void onAccountStopped() {
        restPasswordView.onAccountStopped();
    }

    @Override
    public void onCanceled() {
        restPasswordView.showErrorMessage(R.string.error_email_not_found);
    }
}
