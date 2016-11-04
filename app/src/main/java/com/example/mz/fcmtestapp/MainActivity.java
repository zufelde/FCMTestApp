package com.example.mz.fcmtestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "MZ_FCMTESTAPP";

    private static MainActivity mainActivity;

    private FirebaseApp firebaseApp;
    private TextView logView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseApp = FirebaseApp.initializeApp(this);
        mainActivity = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainActivity = null;
    }

    private RemoteMessage createMessage(boolean setTtl) {
        RemoteMessage.Builder rmBuilder = new RemoteMessage.Builder(firebaseApp.getOptions().getGcmSenderId() + "@gcm.googleapis.com");

        rmBuilder.setMessageId(UUID.randomUUID().toString());

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("action", "ECHO");
        rmBuilder.setData(data);

        if(setTtl) {
            rmBuilder.setTtl(86400);
        }

        return rmBuilder.build();
    }

    public void sendMsgWithoutTtl(View view) {
        setViewUnclickable((TextView) view);
        RemoteMessage msg = createMessage(false);
        logToView("Sending message without TTL and message ID " + msg.getMessageId());
        FirebaseMessaging.getInstance().send(msg);
        setViewClickable((TextView) view);
    }

    public void sendMsgWithTtl(View view) {
        setViewUnclickable((TextView) view);
        RemoteMessage msg = createMessage(true);
        logToView("Sending message with TTL and message ID " + msg.getMessageId());
        FirebaseMessaging.getInstance().send(msg);
        setViewClickable((TextView) view);
    }

    public void logToView(String logText) {
        if(logView == null) {
            logView = (TextView) this.findViewById(R.id.logView);
            logView.setMovementMethod(new ScrollingMovementMethod());
        }

        Log.d(LOG_TAG, logText);
        logView.append(logText + "\n");
    }

    private void setViewUnclickable(TextView view) {
        view.setClickable(false);
        view.setBackgroundColor(getResources().getColor(R.color.buttonBackgrNotClickable));
        view.setTextColor(getResources().getColor(R.color.buttonTextNotClickable));
    }

    private void setViewClickable(TextView view) {
        view.setTextColor(getResources().getColor(R.color.buttonTextClickable));
        view.setBackgroundColor(getResources().getColor(R.color.buttonBackgrClickable));
        view.setClickable(true);
    }

    public static void logToLogView(String logText) {
        if(mainActivity != null) {
            mainActivity.runOnUiThread(new LogRunnable(logText));
        }
    }

    private static class LogRunnable implements Runnable {

        private String logText;

        LogRunnable(String logText) {
            this.logText = logText;
        }

        @Override
        public void run() {
            MainActivity.mainActivity.logToView(this.logText);
        }
    }
}
