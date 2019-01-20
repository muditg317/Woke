package com.pineapple.woke;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Activity_Settings_Home extends AppCompatActivity {
    ImageButton imgButton_back;
    ImageButton imgButton_notifications;
    ImageButton imgButton_alarms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_home);

        imgButton_back =findViewById(R.id.imageButton_buttonBack);
        imgButton_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imgButton_notifications =findViewById(R.id.imageButton_notification);
        imgButton_notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNotifications();
            }
        });

        imgButton_alarms =findViewById(R.id.imageButton_alarm);
        imgButton_alarms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAlarms();
            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

    private void toNotifications(){
        Intent intent = new Intent(this, Activity_Settings_Notifications.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        //finish();
    }

    private void toAlarms(){
        Intent intent = new Intent(this, Activity_Settings_Alarms.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        //finish();
    }
}
