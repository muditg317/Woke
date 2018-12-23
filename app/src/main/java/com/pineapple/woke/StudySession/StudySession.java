package com.pineapple.woke.StudySession;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import com.pineapple.woke.resources.MyCallback;

import org.w3c.dom.Text;

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

    private final TextView textView_next;
    private final TextView textView_time;

    private SavedSession saveState;
    private MyCallback notifyCallback;

    public StudySession(TextView textView_next, TextView textView_time, double wokeMinutes) {
        super((int)(wokeMinutes*60*1000), 100);
        wokeMillis = (int)(wokeMinutes*60*1000);
        millisLastUpdate = Calendar.getInstance().getTimeInMillis();
        millisElapsed = 0;
        millisElapsedToWoke = 0;
        displayWokeTime = 0;
        studying = true;
        wokeNotifs = 0;
        this.textView_next = textView_next;
        this.textView_time = textView_time;

        saveState = new SavedSession(millisLastUpdate,wokeMillis);
    }

    public void onTick(long millisUntilFinished) {
        String onTickTAG = "onTick(" + Long.toString(millisUntilFinished) + ")";
        Log.d(onTickTAG, "onTick called");
        long currTime = Calendar.getInstance().getTimeInMillis();
        Log.d(onTickTAG, "currTime: " + Long.toString(currTime));

        Log.d(onTickTAG, "currTime-millisLastUpdate: " + Long.toString((currTime-millisLastUpdate)));

        millisElapsed += (currTime-millisLastUpdate);
        Log.d(onTickTAG, "millisElapsed: " + Long.toString(millisElapsed));
        millisElapsedToWoke += (currTime-millisLastUpdate);
        Log.d(onTickTAG, "millisElapsedToWoke: " + Long.toString(millisElapsedToWoke));

        if(millisElapsedToWoke >= wokeMillis) {
            wokeNotifs++;
            Log.d(onTickTAG, "wokeNotifs: " + Integer.toString(wokeNotifs));
            textView_next.setText("GET WOKE: "+wokeNotifs);
            notifyCallback.accept(wokeMillis);
            millisElapsedToWoke = 0;
            displayWokeTime = 0;
        }

        if(textView_next.getText().toString().contains("GET WOKE")) {
            displayWokeTime += (currTime - millisLastUpdate);
            Log.d(onTickTAG, "displayWokeTime: " + Long.toString(displayWokeTime));
        }
        if(displayWokeTime >= 1000) {
            textView_next.setText("Next Woke notification in " + (millisUntilFinished/1000.0) + " seconds");
        }

        int minutes = (int)(millisElapsed/1000/60);
        int seconds = (int)(millisElapsed%(60*1000))/1000;
        textView_time.setText((minutes<10?"0":"")+minutes+":"+(seconds<10?"0":"")+seconds);
        millisLastUpdate = currTime;
        Log.d(onTickTAG, "millisLastUpdate: " + Long.toString(millisLastUpdate));
    }

    public void onFinish() {
        start();
        Log.d(TAG, "onFinish");
    }

    public void updateSaveState() {
        saveState.setDuration(millisElapsed);
        saveState.setWokeNotifs(wokeNotifs);
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

    public void setNotifyCallback(MyCallback<Integer> notifyCallback) {
        this.notifyCallback = notifyCallback;
    }

    public int getWokeInterval() {
        return wokeMillis;
    }
}
