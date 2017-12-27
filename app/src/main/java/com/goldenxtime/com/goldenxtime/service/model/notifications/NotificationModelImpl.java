package com.goldenxtime.com.goldenxtime.service.model.notifications;

import com.goldenxtime.com.goldenxtime.model.Message;
import com.goldenxtime.com.goldenxtime.service.SetupService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationModelImpl implements NotificationModel {

    @Override
    public void getNotifications(final OnNotificationFinishedListener listener) {
        SetupService.getService.getNotifications().enqueue(new Callback<ArrayList<Message>>() {
            @Override
            public void onResponse(Call<ArrayList<Message>> call, Response<ArrayList<Message>> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    if (response.code() == 401)
                        listener.onAccountStopped();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Message>> call, Throwable t) {
                listener.onCanceled();
            }
        });
    }
}
