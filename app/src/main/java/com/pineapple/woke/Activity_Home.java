package com.pineapple.woke;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pineapple.woke.resources.Constants;
import com.pineapple.woke.resources.Singleton;

public class Activity_Home extends AppCompatActivity {
    ImageButton imgButton_start;
    TextView textView_welcome;

    private final int studySessionRC = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textView_welcome = findViewById(R.id.textView_welcome);
        String welcomeText = "Welcome " + Singleton.getInstance().getCurrUser().getName();
        Log.d("DEBUG", welcomeText);
        textView_welcome.setText(welcomeText);

        imgButton_start =(ImageButton)findViewById(R.id.imageButton_buttonStudy);
        imgButton_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStudying();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == studySessionRC) {
            //int millisSession = data.getIntExtra(Constants.studySessionMillis,0);
            //TODO: save this somewhere
        }
    }
}
