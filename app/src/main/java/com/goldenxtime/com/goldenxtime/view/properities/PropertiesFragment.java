package com.goldenxtime.com.goldenxtime.view.properities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.goldenxtime.com.goldenxtime.R;
import com.goldenxtime.com.goldenxtime.model.Property;
import com.goldenxtime.com.goldenxtime.service.LoginPref;
import com.goldenxtime.com.goldenxtime.view.adapter.PropertiesAdapter;
import com.goldenxtime.com.goldenxtime.view.adapter.ViewPageFragment;
import com.goldenxtime.com.goldenxtime.view.layoutmanager.RtlGridLayoutManager;
import com.goldenxtime.com.goldenxtime.view.login.LoginActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PropertiesFragment extends ViewPageFragment implements PropertiesView {

    @BindView(R.id.propertiesRV)
    RecyclerView propertiesRV;
    @BindView(R.id.noContentL)
    LinearLayout noContentL;
    @BindView(R.id.progressB)
    ProgressBar progressB;

    PropertiesAdapter propertiesAdapter;
    ProperitiesPresenter presenter;
    Unbinder unbinder;

    public static PropertiesFragment newInstance() {
        PropertiesFragment fragment = new PropertiesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_properties, container, false);

        unbinder = ButterKnife.bind(this, view);

        initPropertiesRV();
        presenter = new PropertiesPersenterImpl(this);
        presenter.getProperties();

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
        presenter.getProperties();
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    public void initPropertiesRV() {
        propertiesRV.setLayoutManager(new RtlGridLayoutManager(getActivity(), 2));
    }

    public void setPropertiesAdapter(ArrayList<Property> properties) {
        this.propertiesAdapter = new PropertiesAdapter(properties, getActivity());
        propertiesRV.setAdapter(propertiesAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showProperties(ArrayList<Property> properties) {
        try {
            if (properties == null || properties.isEmpty()) {
                noContentL.setVisibility(View.VISIBLE);
                setPropertiesAdapter(properties);
            } else {
                setPropertiesAdapter(properties);
                noContentL.setVisibility(View.GONE);
            }
        } catch (Exception e) {

        }
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

    public void logout() {
        LoginPref loginPref = new LoginPref(getActivity());
        loginPref.removeAccessToken(getContext());
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }
}