package com.goldenxtime.com.goldenxtime.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.goldenxtime.com.goldenxtime.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.usernameET)
    EditText usernameET;
    @BindView(R.id.passwordET)
    EditText passwordET;
    @BindView(R.id.logoIV)
    ImageView logoIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Glide.with(this)
                .load(R.drawable.logo)
                .into(logoIV);
    }

    @OnClick(R.id.rememberTV)
    public void onRememberTVClicked() {
        Intent intent = new Intent(this, RememberPasswordActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.loginB)
    public void onLoginBClicked() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
