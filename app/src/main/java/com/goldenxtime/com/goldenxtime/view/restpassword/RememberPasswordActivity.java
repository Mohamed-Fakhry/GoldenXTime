package com.goldenxtime.com.goldenxtime.view.restpassword;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.goldenxtime.com.goldenxtime.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.anwarshahriar.calligrapher.Calligrapher;

public class RememberPasswordActivity extends AppCompatActivity implements RestPasswordView {

    @BindView(R.id.logoIV)
    ImageView logoIV;
    @BindView(R.id.emailET)
    EditText emailET;

    RestPasswordPresenter restPasswordPresenter;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember_password);
        ButterKnife.bind(this);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getString(R.string.font_name), true);

        Glide.with(this)
                .load(R.drawable.logo)
                .into(logoIV);
        restPasswordPresenter = new RestPasswordPresenterImpl(this);
        progress = new ProgressDialog(this);
    }

    @Override
    public void showErrorMessage(int messageId) {
        progress.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(messageId);
        builder.show();
    }

    @Override
    public void showSuccessMessage() {
        progress.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.sent_to_mail);
        builder.show();
    }

    @Override
    public void onAccountStopped() {}

    @OnClick(R.id.restPasswordB)
    public void onViewClicked() {
        String email = emailET.getText().toString();
        if (email != null && !email.isEmpty()) {
            restPasswordPresenter.sendEmail(email);
            progress.show();
        } else {
            showErrorMessage(R.string.error_email_empty);
        }
    }
}