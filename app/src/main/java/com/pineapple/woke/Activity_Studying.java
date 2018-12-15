package com.pineapple.woke;

import android.content.Intent;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Studying extends AppCompatActivity {
    ImageButton imgButton_pause;
    ImageButton imgButton_stop;
    ImageButton imgButton_resume;
    TextView textView_pause;
    TextView textView_stop;
    TextView textView_resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studying);

        textView_pause = (TextView)findViewById(R.id.textView_buttonPause);
        textView_stop = (TextView)findViewById(R.id.textView_buttonStop);
        textView_resume = (TextView)findViewById(R.id.textView_buttonResume);
        imgButton_pause =(ImageButton)findViewById(R.id.imageButton_buttonPause);
        imgButton_stop =(ImageButton)findViewById(R.id.imageButton_buttonStop);
        imgButton_resume =(ImageButton)findViewById(R.id.imageButton_buttonResume);

        imgButton_resume.setVisibility(View.INVISIBLE);
        textView_resume.setVisibility(View.INVISIBLE);

        imgButton_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseStudying();
                Toast.makeText(getApplicationContext(),"Study session paused",Toast.LENGTH_LONG).show();
            }
        });

        imgButton_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopStudying();
                Toast.makeText(getApplicationContext(),"Study session finished",Toast.LENGTH_LONG).show();
            }
        });

        imgButton_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resumeStudying();
                Toast.makeText(getApplicationContext(),"Study session resumed",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void pauseStudying() {
        imgButton_pause.setVisibility(View.INVISIBLE);
        imgButton_resume.setVisibility(View.VISIBLE);
        textView_resume.setVisibility(View.VISIBLE);
    }

    private void stopStudying() {
        Intent intent = new Intent(this, Activity_Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        //finish();
    }

    private void resumeStudying() {
        imgButton_pause.setVisibility(View.VISIBLE);
        imgButton_resume.setVisibility(View.INVISIBLE);
        textView_resume.setVisibility(View.INVISIBLE);
    }
}
