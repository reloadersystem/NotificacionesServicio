package com.reloader.reloadernotificaciones;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;


import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String messageTitle = remoteMessage.getNotification().getTitle();
        String messageBody = remoteMessage.getNotification().getBody();

        String click_action = remoteMessage.getNotification().getClickAction();
        String dataMessage = remoteMessage.getData().get("message"); //nombre del js node
        String dataFrom = remoteMessage.getData().get("from_user_id");

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messageTitle)
                .setContentText(messageBody);


        Intent resultIntent = new Intent(click_action);
        resultIntent.putExtra("message", dataMessage);
        resultIntent.putExtra("from_user_id", dataFrom);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);


        int mNotificationId = (int) System.currentTimeMillis();
        NotificationManager mNotififyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mNotififyMgr.notify(mNotificationId, mBuilder.build());


    }
}
