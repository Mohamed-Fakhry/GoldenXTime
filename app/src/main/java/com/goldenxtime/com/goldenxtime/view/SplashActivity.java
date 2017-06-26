package com.goldenxtime.com.goldenxtime.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.goldenxtime.com.goldenxtime.R;

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
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        };
        splashThread.start();
    }
}
