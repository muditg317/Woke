package com.pineapple.woke;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pineapple.woke.resources.MyCallback;
import com.pineapple.woke.resources.Singleton;
import com.pineapple.woke.StudySession.StudySession;

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
    StudySession session;

    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studying);

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

        notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

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

        session = new StudySession(textView_next, textView_time,Singleton.getInstance().getCurrUser().getWokeInterval());
        session.setNotifyCallback(new MyCallback<Integer>() {
            @Override
            public void accept(Integer integer) {
                Notification notify = new Notification.Builder(getApplicationContext())
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(android.R.drawable.stat_notify_more)
                        .setTicker("GET WOKE!")
                        .setContentTitle("GET WOKE!")
                        .setContentText("It's been: "+(session.getWokeInterval()/1000/60)+" minutes!")
                        .setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), 0))
                        .build();
                notify.notify();
            }
        });
        Singleton.getInstance().getCurrUser().addStudySession(session.getSaveState());
    }


    @Override
    public void onResume() {
        super.onResume();
        if(session.isStudying()) {
            session.start();
        }
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
        session.finish();
        //intent.putExtra(Constants.studySessionMillis,session.getMillisElapsed());

        setResult(0,intent);
        //startActivity(intent);
        finish();
        super.onDestroy();
    }

    private void pauseStudying() {
        imgButton_pause.setVisibility(View.INVISIBLE);
        imgButton_resume.setVisibility(View.VISIBLE);
        textView_resume.setVisibility(View.VISIBLE);
        session.pauseStudying();
        Toast.makeText(getApplicationContext(),"Study session paused",Toast.LENGTH_SHORT).show();
    }

    private void stopStudying() {
        Toast.makeText(getApplicationContext(),"Study session finished",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        session.finish();
        //Singleton.getInstance().getCurrUser().addStudySession(session.getSaveState());
        setResult(0,intent);
        finish();
    }

    private void resumeStudying() {
        imgButton_pause.setVisibility(View.VISIBLE);
        imgButton_resume.setVisibility(View.INVISIBLE);
        textView_resume.setVisibility(View.INVISIBLE);
        session.resumeStudying();
        Toast.makeText(getApplicationContext(),"Study session resumed",Toast.LENGTH_SHORT).show();
    }
}
