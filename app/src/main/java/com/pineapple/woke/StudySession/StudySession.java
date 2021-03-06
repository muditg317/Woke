package com.pineapple.woke.StudySession;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import com.pineapple.woke.resources.Constants;
import com.pineapple.woke.resources.MyCallback;
import com.pineapple.woke.resources.Singleton;

import java.util.Calendar;

public class StudySession extends CountDownTimer {

    private String TAG = "StudySession";

    private int wokeMillis;
    private long millisLastUpdate;
    private long millisElapsed;
    private long millisElapsedToWoke;
    private long displayWokeTime;
    private boolean studying;
    private int wokeNotifs;
    private boolean notified;

    private int missedNotifications;

    private TextView textView_next;
    private TextView textView_time;

    private SavedSession saveState;
    private MyCallback notifyCallback;

    private MediaPlayer mp_alarm;

    public StudySession(TextView textView_next, TextView textView_time, double wokeMinutes) {
        super((long)(wokeMinutes*60*1000), Constants.COUNTDOWNINTERVAL);
        wokeMillis = (int)(wokeMinutes*60*1000);
        millisLastUpdate = Calendar.getInstance().getTimeInMillis();
        millisElapsed = 0;
        millisElapsedToWoke = 0;
        displayWokeTime = 0;
        studying = true;
        wokeNotifs = 0;
        notified = false;
        missedNotifications = 0;
        this.textView_next = textView_next;
        this.textView_time = textView_time;

        saveState = new SavedSession(millisLastUpdate,wokeMillis);
    }

    public void setViews(TextView textView_next, TextView textView_time) {
        this.textView_next = textView_next;
        this.textView_time = textView_time;
    }

    public void onTick(long millisUntilFinished) {
        String onTickTAG = "onTick(" + Long.toString(millisUntilFinished) + ")";
        //Log.d(onTickTAG, "onTick called");
        long currTime = Calendar.getInstance().getTimeInMillis();
        //Log.d(onTickTAG, "currTime: " + Long.toString(currTime));

        //Log.d(onTickTAG, "currTime-millisLastUpdate: " + Long.toString((currTime-millisLastUpdate)));

        millisElapsed += (currTime-millisLastUpdate);
        //Log.d(onTickTAG, "millisElapsed: " + Long.toString(millisElapsed));
        millisElapsedToWoke += (currTime-millisLastUpdate);
        //Log.d(onTickTAG, "millisElapsedToWoke: " + Long.toString(millisElapsedToWoke));

        if(notified) {
            displayWokeTime += (currTime - millisLastUpdate);
            //Log.d(onTickTAG, "displayWokeTime: " + Long.toString(displayWokeTime));
        }

        if(notified && displayWokeTime >= wokeMillis - 1000) {
            missedNotifications += 1;
            Log.d("StudySession","missed a notification: " + Integer.toString(missedNotifications));
            notified = false;
        }

        if(millisElapsedToWoke >= wokeMillis) {
            triggerWokeNotification();
            //Log.d(onTickTAG, "wokeNotifs: " + Integer.toString(wokeNotifs));
            //textView_next.setText("GET WOKE: "+wokeNotifs);
            millisElapsedToWoke = 0;
            displayWokeTime = 0;
        }
        else {
            int secs = (int)((wokeMillis-millisElapsedToWoke)/1000)+1;
            if(secs < 60){
                textView_next.setText("Next Woke notification in " + secs + " seconds");
            }
            else {
                textView_next.setText("Next Woke notification in " + ((int)Math.ceil((secs/60.0))) + " minutes");
            }
        }


        int minutes = (int)(millisElapsed/1000/60);
        int seconds = (int)(millisElapsed%(60*1000))/1000;
        textView_time.setText((minutes<10?"0":"")+minutes+":"+(seconds<10?"0":"")+seconds);
        millisLastUpdate = currTime;
        //Log.d(onTickTAG, "millisLastUpdate: " + Long.toString(millisLastUpdate));
    }

    private void triggerWokeNotification() {
        if(missedNotifications < Singleton.getInstance().getCurrUser().getNotif_frequency()) {
            wokeNotifs++;
            Log.d("STUDYSESSION", Integer.toString(wokeNotifs));
            notifyCallback.accept(false);
            notified = true;
        } else {
            wokeNotifs++;
            Log.d("STUDYSESSION", Integer.toString(wokeNotifs));
            notifyCallback.accept(true);
            notified = true;

        }
    }

    public void onFinish() {
        start();
        //Log.d(TAG, "onFinish");
    }

    private void updateSaveState() {
        saveState.setDuration(millisElapsed);
        saveState.setWokeNotifs(wokeNotifs);
        //Singleton.getInstance().getAppDatabase().userDao().update(Singleton.getInstance().getCurrUser());
    }

    public void pauseStudying() {
        cancel();
        if(studying){
            onTick(100);
            studying = false;
        }
        updateSaveState();
    }

    public void finish() {
        pauseStudying();
        Singleton.getInstance().getCurrUser().setCurrStudySession(null);
        //updateSaveState();
    }

    public void resumeStudying() {
        studying = true;
        millisLastUpdate = Calendar.getInstance().getTimeInMillis();
        start();
        updateSaveState();
    }

    public boolean isStudying() {
        return studying;
    }

    public SavedSession getSaveState() {
        updateSaveState();
        return saveState;
    }

    public void setNotifyCallback(MyCallback<Boolean> notifyCallback) {
        this.notifyCallback = notifyCallback;
    }

    public int getWokeInterval() {
        return wokeMillis;
    }

    public void dismissWokeNotification() {
        notified = false;
        missedNotifications = 0;
        mp_alarm = Singleton.getInstance().getMp_alarm();
        if(mp_alarm.isPlaying()) {
            Log.d("StudySession", "dismissed alarm");
            mp_alarm.pause();
            mp_alarm.seekTo(0);
        } else {
            Log.d("StudySession", "dismissed notification");
        }
    }

}
