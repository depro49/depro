package com.example.vinaigrette;

import android.content.Intent;

import com.dpcsa.compon.interfaces_classes.Notice;
import com.dpcsa.compon.services.PushFMCService;
import com.google.firebase.messaging.RemoteMessage;

public class PushService extends PushFMCService {
    @Override
    public void formCustomNotification(Intent notificationIntent, Notice not, RemoteMessage remoteMessage) {

    }
}
