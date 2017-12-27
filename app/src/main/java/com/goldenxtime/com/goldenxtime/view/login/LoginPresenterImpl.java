package com.goldenxtime.com.goldenxtime.view.login;

import android.util.Log;

import com.goldenxtime.com.goldenxtime.R;
import com.goldenxtime.com.goldenxtime.service.model.login.LoginModel;
import com.goldenxtime.com.goldenxtime.service.model.login.LoginModelImpl;

public class LoginPresenterImpl implements LoginPresenter, LoginModel.OnLoginFinishedListener {

    private LoginView loginView;
    private LoginModelImpl loginModel;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginModel = new LoginModelImpl();
    }

    @Override
    public void validateCerdintial(String username, String password) {

        if ((username == null || username.isEmpty()) && (password == null || password.isEmpty())) {
            loginView.setUsernameOrPasswordError(R.string.error_username_and_password_empty);
            return;
        }
        else if (username == null || username.isEmpty()) {
            loginView.setUsernameOrPasswordError(R.string.error_username_empty);
            return;
        }
        else if (password == null || password.isEmpty()) {
            loginView.setUsernameOrPasswordError(R.string.error_password_empty);
            return;
        }

        if (loginView.isNetworkAvailable()) {
            loginView.showProgress(true);
            loginModel.login(username, password, this);
        } else {
            loginView.noInternetConnection();
        }
    }

    @Override
    public void onCanceled() {
        loginView.showProgress(false);
    }

    @Override
    public void onSuccess(String token) {
        loginView.showProgress(false);
        loginView.successAction(token);
    }

    @Override
    public void onAccountStopped() {
        loginView.showProgress(false);
        loginView.onAccountStopped();
    }
}