package com.goldenxtime.com.goldenxtime.view.notification;

import com.goldenxtime.com.goldenxtime.model.Message;

import java.util.ArrayList;

public interface NotificationView {

    void showNotifications(ArrayList<Message> messages);

    void onAccountStopped();

    void setProgressB(boolean status);

    boolean isNetworkAvailable();

    void noInternetConnection();
}
