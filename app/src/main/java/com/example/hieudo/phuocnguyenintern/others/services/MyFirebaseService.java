package com.example.hieudo.phuocnguyenintern.others.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.hieudo.phuocnguyenintern.R;
import com.example.hieudo.phuocnguyenintern.ui.home.homeScreen.HomeScreenActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MyFirebaseService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseService";

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.e(TAG,s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        
        if(remoteMessage.getNotification()!= null)
        {
            showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        }
        remoteMessage.getData();
        Log.d(TAG, "Notification Data: " + remoteMessage.getData());
        String body = "name: " + remoteMessage.getData().get("name") + " mission: " + remoteMessage.getData().get("mission") + " status: " + remoteMessage.getData().get("status");
        String title = ""+remoteMessage.getData().get("name");
        showNotification(title, body);
    }

    private void showNotification(String title, String body) {

        Intent intent = new Intent(this, HomeScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "com.example.hieudo.phuocnguyenintern";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("StackExchange");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.home_logo)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setContentInfo("Info");

        notificationManager.notify(new Random().nextInt(),notificationBuilder.build());

    }
}
