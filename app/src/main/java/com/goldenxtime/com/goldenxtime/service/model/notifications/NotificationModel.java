package com.goldenxtime.com.goldenxtime.service.model.notifications;


import com.goldenxtime.com.goldenxtime.model.Message;

import java.util.ArrayList;

public interface NotificationModel {

    interface  OnNotificationFinishedListener {

        void onCanceled();

        void onSuccess(ArrayList<Message> messages);

        void onAccountStopped();
    }

    void getNotifications(OnNotificationFinishedListener listener);
}
