package com.pineapple.woke;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pineapple.woke.resources.Constants;

import java.util.Calendar;

public class Activity_Studying extends AppCompatActivity {

    private final static String TAG = "Activity_Studying";


    ImageButton imgButton_pause;
    ImageButton imgButton_stop;
    ImageButton imgButton_resume;
    TextView textView_pause;
    TextView textView_stop;
    TextView textView_resume;
    TextView textView_time;
    TextView textView_next;


    double wokeMinutes = 0.05;
    long millisLastUpdate;
    long millisElapsed;
    long millisElapsedToWoke;
    long displayWokeTime;
    boolean studying;
    int wokeNotifs;

    CountDownTimer ticker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studying);

        millisLastUpdate = Calendar.getInstance().getTimeInMillis();
        millisElapsed = 0;
        millisElapsedToWoke = 0;
        displayWokeTime = 0;
        studying = true;
        wokeNotifs = 0;


        textView_pause = findViewById(R.id.textView_buttonPause);
        textView_stop = findViewById(R.id.textView_buttonStop);
        textView_resume = findViewById(R.id.textView_buttonResume);
        imgButton_pause = findViewById(R.id.imageButton_buttonPause);
        imgButton_stop = findViewById(R.id.imageButton_buttonStop);
        imgButton_resume = findViewById(R.id.imageButton_buttonResume);
        textView_time = findViewById(R.id.textView_time);
        textView_next = findViewById(R.id.textView_next);

        imgButton_resume.setVisibility(View.INVISIBLE);
        textView_resume.setVisibility(View.INVISIBLE);

        imgButton_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseStudying();
            }
        });

        imgButton_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopStudying();
            }
        });

        imgButton_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resumeStudying();
            }
        });

        //startService(new Intent(this, BroadcastService.class));
        //Log.i(TAG, "Started service");

        ticker = new CountDownTimer((int)(wokeMinutes*60*1000), 100) {
            public void onTick(long millisUntilFinished) {
                long currTime = Calendar.getInstance().getTimeInMillis();
                millisElapsed += (currTime-millisLastUpdate);
                millisElapsedToWoke += (currTime-millisLastUpdate);
                if(textView_next.getText().toString().contains("GET WOKE")) {
                    displayWokeTime += (currTime-millisLastUpdate);
                }
                int minutes = (int)(millisElapsed/1000/60);
                int seconds = (int)(millisElapsed%(60*1000))/1000;
                if(millisElapsedToWoke >= (int)(wokeMinutes*60*1000)) {
                    wokeNotifs++;
                    textView_next.setText("GET WOKE: "+wokeNotifs);
                    millisElapsedToWoke = 0;
                    displayWokeTime = 0;
                }
                if(displayWokeTime >= 1000) {
                    textView_next.setText("Next Woke notification in " + wokeMinutes + " minutes");
                }
                textView_time.setText((minutes<10?"0":"")+minutes+":"+(seconds<10?"0":"")+seconds);
                millisLastUpdate = currTime;
            }

            public void onFinish() {
                //continueTimer();
                ticker.start();
            }
        };
    }


    @Override
    public void onResume() {
        super.onResume();
        if(studying) {
            ticker.start();
        }
        //registerReceiver(br, new IntentFilter(BroadcastService.COUNTDOWN_BR));
        //Log.i(TAG, "Registered broacast receiver");
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Intent intent = new Intent();
        ticker.cancel();
        ticker.onTick(100);
        studying = false;
        intent.putExtra(Constants.studySessionMillis,millisElapsed);
        setResult(0,intent);
        //startActivity(intent);
        finish();
        super.onDestroy();
    }

    private void pauseStudying() {
        imgButton_pause.setVisibility(View.INVISIBLE);
        imgButton_resume.setVisibility(View.VISIBLE);
        textView_resume.setVisibility(View.VISIBLE);
        ticker.cancel();
        ticker.onTick(100);
        studying = false;
        Toast.makeText(getApplicationContext(),"Study session paused",Toast.LENGTH_SHORT).show();
    }

    private void stopStudying() {
        Toast.makeText(getApplicationContext(),"Study session finished",Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, Activity_Home.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Intent intent = new Intent();
        ticker.cancel();
        ticker.onTick(100);
        studying = false;
        intent.putExtra(Constants.studySessionMillis,millisElapsed);
        setResult(0,intent);
        //startActivity(intent);
        finish();
    }

    private void resumeStudying() {
        imgButton_pause.setVisibility(View.VISIBLE);
        imgButton_resume.setVisibility(View.INVISIBLE);
        textView_resume.setVisibility(View.INVISIBLE);
        studying = true;
        millisLastUpdate = Calendar.getInstance().getTimeInMillis();
        ticker.start();
        Toast.makeText(getApplicationContext(),"Study session resumed",Toast.LENGTH_SHORT).show();
    }
}
