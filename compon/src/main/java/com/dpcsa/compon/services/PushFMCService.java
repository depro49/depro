package com.dpcsa.compon.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import com.dpcsa.compon.R;
import com.dpcsa.compon.base.ComponBaseStartActivity;
import com.dpcsa.compon.interfaces_classes.Channel;
import com.dpcsa.compon.interfaces_classes.Notice;
import com.dpcsa.compon.single.ComponGlob;
import com.dpcsa.compon.single.Injector;
import com.dpcsa.compon.tools.Constants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Map;

import androidx.core.app.NotificationCompat;

public class PushFMCService extends FirebaseMessagingService {

    public String TYPE = "type";
    public int iconId;
    public ComponGlob componGlob;
    private String type;

    @Override
    public void onNewToken(String s) {
        Log.d("QWERT", "New token="+s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        componGlob = Injector.getComponGlob();
        Map<String, String> data = remoteMessage.getData();
        type = data.get(TYPE);
Log.d("QWERT","onMessageReceived type="+type);
        if (type != null && type.length() != 0) {
            for (Notice not : componGlob.notices) {
                if (not.type.equals(type)) {
                    formNotif(not, remoteMessage);
                    break;
                }
            }
        } else {

        }
    }

    private void formNotif(Notice not, RemoteMessage remoteMessage) {
        Intent notificationIntent = new Intent(this, ComponBaseStartActivity.class);
        notificationIntent.putExtra(Constants.NAME_MVP, "main");
        notificationIntent.putExtra(Constants.PUSH_TYPE, type);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationIntent.setAction(type);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, not.idChannel);
        Channel chan = componGlob.channels.get(not.idChannelInt);
        notificationBuilder.setAutoCancel(true)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis());
        if (chan.drawableId != 0) {
            notificationBuilder.setSmallIcon(chan.drawableId);
        }
        if (chan.lightColor != 0) {
            notificationBuilder.setColor(chan.lightColor);
        }

        mNotificationManager.notify(not.idChannelInt, notificationBuilder.build());
    }
}
