package com.goldenxtime.com.goldenxtime.view.rent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.goldenxtime.com.goldenxtime.R;
import com.goldenxtime.com.goldenxtime.model.Rent;
import com.goldenxtime.com.goldenxtime.service.LoginPref;
import com.goldenxtime.com.goldenxtime.view.adapter.TimeLineAdapter;
import com.goldenxtime.com.goldenxtime.view.adapter.ViewPageFragment;
import com.goldenxtime.com.goldenxtime.view.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RentsFragment extends ViewPageFragment implements RentView {

    @BindView(R.id.rentRV)
    RecyclerView rentRV;
    @BindView(R.id.noContentL)
    LinearLayout noContentL;
    @BindView(R.id.progressB)
    ProgressBar progressB;

    Unbinder unbinder;
    RentPresenter rentPresenter;
    TimeLineAdapter<Rent.Collection> timeLineAdapter;
    int properityId;

    public RentsFragment() {}

    public static RentsFragment newInstance(int id) {
        RentsFragment fragment = new RentsFragment();
        fragment.setTitle("الايجارات");
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
        View view = inflater.inflate(R.layout.fragment_rents, container, false);
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
            if (rent.getCollections() == null || rent.getCollections().isEmpty()) {
                noContentL.setVisibility(View.VISIBLE);
                timeLineAdapter = new TimeLineAdapter(rent.getCollections());
                rentRV.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                rentRV.setAdapter(timeLineAdapter);
                rentRV.scrollToPosition(rent.getCollections().size() - 1);
            } else {
                timeLineAdapter = new TimeLineAdapter(rent.getCollections());
                rentRV.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                rentRV.setAdapter(timeLineAdapter);
                rentRV.scrollToPosition(rent.getCollections().size() - 1);
                noContentL.setVisibility(View.GONE);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void showNoRent() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
       try {
           if (status && this.progressB.getVisibility() != View.VISIBLE)
               this.progressB.setVisibility(View.VISIBLE);
           if (!status && this.progressB.getVisibility() != View.GONE)
               this.progressB.setVisibility(View.GONE);
       } catch (Exception e) {}
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