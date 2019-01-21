package com.pineapple.woke;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.pineapple.woke.resources.Constants;
import com.pineapple.woke.resources.Singleton;

public class MyBroadcastReceiver extends BroadcastReceiver{

    NotificationManagerCompat notificationManagerCompat;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BroadcastReceiver", "received");

        String action=intent.getStringExtra("type");
        dismiss(action);

        notificationManagerCompat = Singleton.getInstance().getNotificationManager();
        notificationManagerCompat.cancel(Constants.wokeNotificationID);

        //This is used to close the notification tray
        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(it);
    }

    private void dismiss(String type){
        Log.d("BroadcastReceiver","dismissing: " + type);
        Singleton.getInstance().getCurrUser().getCurrStudySession().dismissWokeNotification();
    }
}
