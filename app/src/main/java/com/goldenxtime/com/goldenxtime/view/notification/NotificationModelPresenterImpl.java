package com.goldenxtime.com.goldenxtime.view.notification;

import com.goldenxtime.com.goldenxtime.model.Message;
import com.goldenxtime.com.goldenxtime.service.model.notifications.NotificationModel;
import com.goldenxtime.com.goldenxtime.service.model.notifications.NotificationModelImpl;

import java.util.ArrayList;

public class NotificationModelPresenterImpl implements NotificationsPresenter
        , NotificationModel.OnNotificationFinishedListener {

    NotificationModel notificationModel;
    NotificationView notificationView;

    public NotificationModelPresenterImpl(NotificationView notificationView) {
        this.notificationView = notificationView;
        notificationModel = new NotificationModelImpl();
    }

    @Override
    public void getNotifications() {
        if(notificationView.isNetworkAvailable()) {
            notificationView.setProgressB(true);
            notificationModel.getNotifications(this);
        } else {
            notificationView.setProgressB(false);
            notificationView.noInternetConnection();
        }
    }

    @Override
    public void onSuccess(ArrayList<Message> messages) {
        notificationView.setProgressB(false);
        notificationView.showNotifications(messages);
    }

    @Override
    public void onAccountStopped() {
        notificationView.setProgressB(false);
        notificationView.onAccountStopped();
    }

    @Override
    public void onCanceled() {
        notificationView.setProgressB(false);
    }
}