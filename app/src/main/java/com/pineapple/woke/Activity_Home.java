package com.pineapple.woke;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Home extends AppCompatActivity {
    ImageButton imgButton_start;
    TextView textView_welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textView_welcome.setText("Welcome ");

        imgButton_start =(ImageButton)findViewById(R.id.imageButton_buttonStudy);
        imgButton_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStudying();
                Toast.makeText(getApplicationContext(),"Study session started",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void startStudying() {
        Intent intent = new Intent(this, Activity_Studying.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        //finish();
    }
}
