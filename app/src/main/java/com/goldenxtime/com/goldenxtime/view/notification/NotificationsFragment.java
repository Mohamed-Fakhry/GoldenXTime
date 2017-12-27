package com.goldenxtime.com.goldenxtime.view.notification;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.goldenxtime.com.goldenxtime.R;
import com.goldenxtime.com.goldenxtime.model.Message;
import com.goldenxtime.com.goldenxtime.service.LoginPref;
import com.goldenxtime.com.goldenxtime.view.adapter.TimeLineAdapter;
import com.goldenxtime.com.goldenxtime.view.adapter.ViewPageFragment;
import com.goldenxtime.com.goldenxtime.view.login.LoginActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NotificationsFragment extends ViewPageFragment implements NotificationView {

    @BindView(R.id.notificationRV)
    RecyclerView notificationRV;
    @BindView(R.id.noContentL)
    LinearLayout noContentL;
    @BindView(R.id.progressB)
    ProgressBar progressB;

    Unbinder unbinder;
    NotificationsPresenter notificationsPresenter;

    public static NotificationsFragment newInstance() {
        NotificationsFragment fragment = new NotificationsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_notifications, container, false);
        unbinder = ButterKnife.bind(this, view);
        notificationsPresenter = new NotificationModelPresenterImpl(this);
        notificationsPresenter.getNotifications();
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
        notificationsPresenter.getNotifications();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showNotifications(ArrayList<Message> messages) {
        try {
            if (messages == null || messages.isEmpty())
                noContentL.setVisibility(View.VISIBLE);
            else {
                TimeLineAdapter<Message> timeLineAdapter = new TimeLineAdapter(messages);
                notificationRV.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                notificationRV.setAdapter(timeLineAdapter);
                notificationRV.scrollToPosition(messages.size() - 1);
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