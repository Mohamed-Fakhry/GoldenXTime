package com.goldenxtime.com.goldenxtime.view.rent;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.goldenxtime.com.goldenxtime.R;
import com.goldenxtime.com.goldenxtime.model.Rent;
import com.goldenxtime.com.goldenxtime.service.LoginPref;
import com.goldenxtime.com.goldenxtime.view.adapter.BaseView;
import com.goldenxtime.com.goldenxtime.view.adapter.TextSliderView;
import com.goldenxtime.com.goldenxtime.view.adapter.ViewPageFragment;
import com.goldenxtime.com.goldenxtime.view.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RentFragment extends ViewPageFragment implements RentView {

    @BindView(R.id.fSecL)
    LinearLayout fSecL;
    @BindView(R.id.sSecL)
    LinearLayout sSecL;
    @BindView(R.id.renterNameTV)
    TextView renterNameTV;
    @BindView(R.id.rentStartDateTV)
    TextView rentStartDateTV;
    @BindView(R.id.rentEndDateTV)
    TextView rentEndDateTV;
    @BindView(R.id.rentTypeTV)
    TextView rentTypeTV;
    @BindView(R.id.rentValueTV)
    TextView rentValueTV;
    @BindView(R.id.caseHTV)
    TextView caseHTV;
    @BindView(R.id.caseBTV)
    TextView caseBTV;
    @BindView(R.id.caseL)
    LinearLayout caseL;
    @BindView(R.id.contractPicsB)
    Button contractPicsB;
    @BindView(R.id.progressB)
    ProgressBar progressB;

    Unbinder unbinder;
    RentPresenter rentPresenter;
    int properityId;
    Rent rent;

    public RentFragment() {
    }

    public static RentFragment newInstance(int id) {
        RentFragment fragment = new RentFragment();
        fragment.setTitle("تفاصيل الايجار");
        Bundle args = new Bundle();
        args.putInt("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rent, container, false);
        unbinder = ButterKnife.bind(this, view);
        rentPresenter = new RentPersenterImpl(this);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            properityId = bundle.getInt("id");
            rentPresenter.getRent(String.valueOf(properityId));
        }
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
        rentPresenter.getRent(String.valueOf(properityId));
    }

    @Override
    public void showRentDetails(Rent rent) {
        try {
            this.rent = rent;
            fSecL.setVisibility(View.VISIBLE);
            renterNameTV.setText(rent.getRenter());
            rentStartDateTV.setText(rent.getStartDate());
            rentEndDateTV.setText(rent.getEndDate());
            rentValueTV.setText(String.valueOf(rent.getValue()));
            rentTypeTV.setText(rent.getTypeDisplayName());
            if (rent.getCaseRent() != null && rent.getCaseRent().size() > 0) {
                if (caseL.getVisibility() == View.GONE)
                    caseL.setVisibility(View.VISIBLE);
                Rent.Case aCase = rent.getCaseRent().get(0);
                caseHTV.setText("تم رفع دعوة قضائية");
                caseBTV.setText(aCase.getReasone());
            } else if (caseL.getVisibility() == View.VISIBLE)
                caseL.setVisibility(View.GONE);

            if (rent.getPhotos() != null && !rent.getPhotos().isEmpty()) {
                contractPicsB.setVisibility(View.VISIBLE);
            } else {
                contractPicsB.setVisibility(View.GONE);
            }
        } catch (Exception e) {
        }
    }

    @OnClick(R.id.contractPicsB)
    public void showContract() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_properity_contract_pictures, null);

        SliderLayout slider = (SliderLayout) view.findViewById(R.id.slider);
        for (int i = 0; i < rent.getPhotos().size(); i++) {
            BaseView baseSliderView = new TextSliderView(getActivity());
            baseSliderView.image(rent.getPhotos().get(i).getPictureUrl());
            slider.addSlider(baseSliderView);
        }

        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    @Override
    public void showNoRent() {
        sSecL.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAccountStopped() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("يرجى التواصل مع الادارة");
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                logout();
            }
        });
        builder.show();
    }

    public void logout() {
        LoginPref loginPref = new LoginPref(getActivity());
        loginPref.removeAccessToken(getContext());

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
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