package com.goldenxtime.com.goldenxtime.view.profile;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.goldenxtime.com.goldenxtime.R;
import com.goldenxtime.com.goldenxtime.model.User;
import com.goldenxtime.com.goldenxtime.service.LoginPref;
import com.goldenxtime.com.goldenxtime.view.adapter.ViewPageFragment;
import com.goldenxtime.com.goldenxtime.view.login.LoginActivity;
import com.mikhaellopez.circularimageview.CircularImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ProfileFragment extends ViewPageFragment implements ProfileContract.View {

    @BindView(R.id.profileCIV)
    CircularImageView profileCIV;
    @BindView(R.id.emailTV)
    TextView emailTV;
    @BindView(R.id.phoneTV)
    TextView phoneTV;
    @BindView(R.id.landlineTV)
    TextView landlineTV;
    @BindView(R.id.addressTV)
    TextView addressTV;
    @BindView(R.id.nameTV)
    TextView nameTV;
    @BindView(R.id.landlineL)
    LinearLayout landlineL;
    @BindView(R.id.addressL)
    LinearLayout addressL;
    @BindView(R.id.emailVerifyIV)
    ImageView emailVerifyIV;
    @BindView(R.id.phoneVerifyIV)
    ImageView phoneVerifyIV;
    @BindView(R.id.infoL)
    LinearLayout infoL;
    @BindView(R.id.progressB)
    ProgressBar progressB;

    Unbinder unbinder;
    ProfileContract.Presenter profilePresenter;
    Dialog dialog;
    User user;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);

        unbinder = ButterKnife.bind(this, view);
        profilePresenter = new ProfilePresenter(this);
        profilePresenter.getUser();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
        profilePresenter.getUser();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showProfileDetails(User user) {
        this.user = user;
        LoginPref loginPref = new LoginPref(getActivity());
        String accessToken = loginPref.getAccessToken();


        if (infoL.getVisibility() == View.GONE) {
            infoL.setVisibility(View.VISIBLE);
        }

        if (user.getPicture() != null) {
            GlideUrl glideUrl = new GlideUrl(user.getPictureUrl(), new LazyHeaders.Builder()
                    .addHeader("x-access-token", accessToken)
                    .build());
            Glide.with(getActivity()).load(glideUrl).into(profileCIV);
        }

        nameTV.setText(user.getName());
        emailTV.setText(user.getEmail());
        phoneTV.setText(user.getPhone());
        if (user.getLandline() == null || user.getLandline().isEmpty()) {
            landlineL.setVisibility(View.GONE);
        } else {
            if (landlineL.getVisibility() == View.GONE)
                landlineL.setVisibility(View.VISIBLE);
            landlineTV.setText(user.getLandline());
        }

        if (user.getAddress() == null || user.getAddress().isEmpty()) {
            addressL.setVisibility(View.GONE);
        } else {
            if (addressL.getVisibility() == View.GONE)
                addressL.setVisibility(View.VISIBLE);
            addressTV.setText(user.getAddress());
        }
        if (user.getEmailVerified() == 1)
            Glide.with(getActivity()).load(R.drawable.ic_verified).into(emailVerifyIV);
        if (user.getPhoneVerified() == 1)
            Glide.with(getActivity()).load(R.drawable.ic_verified).into(phoneVerifyIV);
    }

    @Override
    public void onAccountStopped() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.error_account_stopped);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                logout();
            }
        });
        builder.show();
    }

    @Override
    public void onRequestCodeSentSuccess() {
        String message = getString(R.string.code_sent);
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestCodeSentFail() {
        String message = getString(R.string.error_try_agine);
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCodeSuccess(String message) {
        Glide.with(getActivity()).load(R.drawable.ic_verified).into(phoneVerifyIV);
        user.setPhoneVerified(1);
        dialog.dismiss();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCodeFail(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.logoutB)
    public void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.confirm_exit_dialog, null);
        builder.setView(view);
        final Dialog dialog = builder.create();
        dialog.show();

        TextView yesTV = (TextView) view.findViewById(R.id.yesTV);
        TextView noTV = (TextView) view.findViewById(R.id.noTV);

        noTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        yesTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginPref loginPref = new LoginPref(getActivity());
                loginPref.removeAccessToken(getContext());

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
//                getActivity().finish();
            }
        });
    }

    @OnClick(R.id.phoneL)
    public void onViewClicked() {
        if (user.getPhoneVerified() != 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View view = getActivity().getLayoutInflater().inflate(R.layout.confirm_sent_code_dialog, null);
            builder.setView(view);
            final Dialog dialog = builder.create();
            dialog.show();

            TextView yesTV = (TextView) view.findViewById(R.id.yesTV);
            TextView noTV = (TextView) view.findViewById(R.id.noTV);

            noTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            yesTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    View viewd = getActivity().getLayoutInflater().inflate(R.layout.dialog_verify_phone, null);
                    final EditText codeET = (EditText) viewd.findViewById(R.id.codeET);
                    Button verifyB = (Button) viewd.findViewById(R.id.verifyB);
                    Button retryB = (Button) viewd.findViewById(R.id.retryB);

                    verifyB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!codeET.getText().toString().isEmpty())
                                profilePresenter.sendCode(Integer.valueOf(codeET.getText().toString()));
                        }
                    });

                    retryB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            profilePresenter.verifyPhone();
                        }
                    });

                    builder.setView(viewd);
                    Dialog dialogC = builder.create();
                    dialogC.show();
                    profilePresenter.verifyPhone();
                }
            });


        }
    }

    @Override
    public void setProgressB(boolean status) {
        if (status && this.progressB.getVisibility() != View.VISIBLE)
            this.progressB.setVisibility(View.VISIBLE);
        if (!status && this.progressB.getVisibility() != View.GONE)
            this.progressB.setVisibility(View.GONE);
    }

    @Override
    public void noInternetConnection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.error_no_iternet_connection);
        builder.show();
    }

    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}