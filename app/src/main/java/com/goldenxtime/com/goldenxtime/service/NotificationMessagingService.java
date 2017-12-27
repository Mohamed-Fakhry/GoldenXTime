package com.goldenxtime.com.goldenxtime.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.goldenxtime.com.goldenxtime.R;
import com.goldenxtime.com.goldenxtime.view.MainActivity;
import com.goldenxtime.com.goldenxtime.view.ProperityActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class NotificationMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        boolean flag = remoteMessage.getData().get("id") != null;
        int id = 0;

        String messageTitle = remoteMessage.getNotification().getTitle();

        if (flag) {
            String idS = remoteMessage.getData().get("propertyId");
            id = Integer.valueOf(idS);
        }

        sendNotification(remoteMessage.getNotification().getBody(), messageTitle, id);
    }

    private void sendNotification(String messageBody, String messageTitle, int id) {

        Intent intent;
        if (id != 0) {
            intent = new Intent(this, ProperityActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }

        intent.putExtra("id", id);
        intent.putExtra("notification_message", messageBody);
        intent.putExtra("notification_title", messageTitle);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo_out)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}