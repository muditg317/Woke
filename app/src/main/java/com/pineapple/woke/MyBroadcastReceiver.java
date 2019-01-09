package com.pineapple.woke;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.pineapple.woke.resources.Singleton;

public class MyBroadcastReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Singleton.getInstance().getCurrUser().getCurrStudySession().dismissWokeNotification();
    }
}
