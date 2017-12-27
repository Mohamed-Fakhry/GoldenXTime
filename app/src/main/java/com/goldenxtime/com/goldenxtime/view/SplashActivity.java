package com.goldenxtime.com.goldenxtime.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.goldenxtime.com.goldenxtime.R;
import com.goldenxtime.com.goldenxtime.service.LoginPref;
import com.goldenxtime.com.goldenxtime.view.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splah_activity);

        ImageView logoIV = (ImageView) findViewById(R.id.logoIV);
        Glide.with(this)
                .load(R.drawable.logo)
                .into(logoIV);


        Thread splashThread = new Thread() {
            @Override
            public void run() {
                boolean flag = true;
                try {
                    flag = loginToApp();

                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (flag)
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                else
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        };
        splashThread.start();
    }

    private boolean loginToApp() {
        LoginPref loginPref = new LoginPref(this);
        String accessToken = loginPref.getAccessToken();

        if (accessToken == null || accessToken.isEmpty())
            return true;
        else {
            loginPref.setSecureConnection();
            return false;
        }
    }
}