package com.pineapple.woke;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pineapple.woke.resources.Singleton;

public class Activity_Home extends AppCompatActivity {
    ImageButton imgButton_start;
    ImageButton imgButton_settings;
    TextView textView_welcome;

    private final int studySessionRC = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textView_welcome = findViewById(R.id.textView_welcome);
        String welcomeText = "Welcome " /*+ Singleton.getInstance().getCurrUser().getName()*/;
        Log.d("DEBUG", welcomeText);
        textView_welcome.setText(welcomeText);

        imgButton_start =findViewById(R.id.imageButton_buttonStudy);
        imgButton_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStudying();
            }
        });

        imgButton_settings =findViewById(R.id.imageButton_buttonSettings);
        imgButton_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSettings();
            }
        });
    }

    private void startStudying() {
        Intent intent = new Intent(this, Activity_Studying.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Toast.makeText(getApplicationContext(),"Study session started",Toast.LENGTH_LONG).show();
        startActivityForResult(intent,studySessionRC);//wait for returned study session length
        //finish();
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == studySessionRC) {
            //int millisSession = data.getIntExtra(Constants.studySessionMillis,0);
            //TODO: update history UI
        }
    }
    */

    private void toSettings(){
        Intent intent = new Intent(this, Activity_Settings_Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
