package com.goldenxtime.com.goldenxtime.view.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.goldenxtime.com.goldenxtime.R;
import com.goldenxtime.com.goldenxtime.service.LoginPref;
import com.goldenxtime.com.goldenxtime.view.MainActivity;
import com.goldenxtime.com.goldenxtime.view.restpassword.RememberPasswordActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.anwarshahriar.calligrapher.Calligrapher;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.usernameET)
    EditText usernameET;
    @BindView(R.id.passwordET)
    EditText passwordET;
    @BindView(R.id.logoIV)
    ImageView logoIV;

    LoginPresenter loginPresenter;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Glide.with(this)
                .load(R.drawable.logo)
                .into(logoIV);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getString(R.string.font_name), true);

        progress = new ProgressDialog(this);
        loginPresenter = new LoginPresenterImpl(this);
    }

    @OnClick(R.id.rememberTV)
    public void onRememberTVClicked() {
        Intent intent = new Intent(this, RememberPasswordActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.loginB)
    public void onLoginBClicked() {
        loginPresenter.validateCerdintial(usernameET.getText().toString(),
                passwordET.getText().toString());
    }

    @Override
    public void showProgress(boolean showProgress) {
        try {
            if(showProgress) {
                progress.show();
            } else {
                progress.dismiss();
            }
        } catch (Exception e) {}
    }

    @Override
    public void setUsernameOrPasswordError(int messageResId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(messageResId);
        builder.show();
    }

    @Override
    public void successAction(String token) {
        setToken(token);
        Intent intent = MainActivity.startMainActivity(this);
        startActivity(intent);
        finish();
    }

    private void setToken(String token) {
        LoginPref loginPref = new LoginPref(this);
        loginPref.setAccessToken(this, token);
    }

    @Override
    public void onAccountStopped() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.error_username_or_password_incorrect);
        builder.show();
    }

    @Override
    public void noInternetConnection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.error_no_iternet_connection);
        builder.show();
    }

    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}