package com.goldenxtime.com.goldenxtime.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.goldenxtime.com.goldenxtime.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RememberPasswordActivity extends AppCompatActivity {

    @BindView(R.id.logoIV)
    ImageView logoIV;
    @BindView(R.id.emailET)
    EditText emailET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember_password);
        ButterKnife.bind(this);
        Glide.with(this)
                .load(R.drawable.logo)
                .into(logoIV);
    }
}
