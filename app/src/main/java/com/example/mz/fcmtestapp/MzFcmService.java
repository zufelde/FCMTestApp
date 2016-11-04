package com.example.mz.fcmtestapp;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static com.example.mz.fcmtestapp.MainActivity.logToLogView;

/**
 * Created by Moritz on 27.10.2016.
 */

public class MzFcmService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        logToLogView("onMessageReceived called with message " + remoteMessage.getMessageId() + "; "
                + remoteMessage.getMessageType() + "; " + remoteMessage.getData() + "; " + remoteMessage.getNotification() + "; "
                + remoteMessage.getTtl() + "; " + remoteMessage.getSentTime() + "; " + remoteMessage.getCollapseKey());
    }

    @Override
    public void onDeletedMessages() {
        logToLogView("onDeletedMessages called");
    }

    @Override
    public void onMessageSent(String s) {
        logToLogView("onMessageSent called with message ID " + s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        logToLogView("onSendError called with message ID " + s + " and Exception " + e.toString());
    }
}
