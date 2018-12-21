package com.pineapple.woke.resources;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

public class StudySession {

    private String TAG = "StudySession";

    private double wokeMinutes;
    private long millisLastUpdate;
    private long millisElapsed;
    private long millisElapsedToWoke;
    private long displayWokeTime;
    private boolean studying;
    private int wokeNotifs;

    private final TextView textView_next;
    private final TextView textView_time;

    private CountDownTimer ticker;

    public StudySession(TextView tV_n, TextView tV_t){
        wokeMinutes = Singleton.getInstance().getCurrUser().getNotifInterval();
        millisLastUpdate = Calendar.getInstance().getTimeInMillis();
        millisElapsed = 0;
        millisElapsedToWoke = 0;
        displayWokeTime = 0;
        studying = true;
        wokeNotifs = 0;
        textView_next = tV_n;
        textView_time = tV_t;

        ticker = new CountDownTimer((int)(wokeMinutes*60*1000), 100) {
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

                if(millisElapsedToWoke >= (int)(wokeMinutes*60*1000)) {
                    wokeNotifs++;
                    Log.d(onTickTAG, "wokeNotifs: " + Integer.toString(wokeNotifs));
                    textView_next.setText("GET WOKE: "+wokeNotifs);
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
                //continueTimer();
                ticker.start();
                Log.d(TAG, "onFinish");
            }
        };
    }

    public long getMillisElapsed() {
        return millisElapsed;
    }

    public void onResume(){
        if(studying) {
            ticker.start();
        }
        //registerReceiver(br, new IntentFilter(BroadcastService.COUNTDOWN_BR));
        //Log.i(TAG, "Registered broacast receiver");
    }

    public void onDestroy(){
        ticker.cancel();
        ticker.onTick(100);
        studying = false;
    }

    public void pauseStudying(){
        ticker.cancel();
        ticker.onTick(100);
        studying = false;
    }

    public void stopStudying(){
        ticker.cancel();
        if(studying){
            ticker.onTick(100);
            studying = false;
        }
    }

    public void resumeStudying(){
        studying = true;
        millisLastUpdate = Calendar.getInstance().getTimeInMillis();
        ticker.start();
    }
}
