package com.pineapple.woke;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.pineapple.woke.resources.Singleton;

public class MyBroadcastReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BroadcastReceiver", "received");
        Singleton.getInstance().getCurrUser().getCurrStudySession().dismissWokeNotification();
    }
}
