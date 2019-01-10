package com.pineapple.woke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pineapple.woke.resources.Singleton;

public class Activity_Settings_Notifications extends AppCompatActivity {
    ImageButton imgButton_back;
    TextView textView_intervalTime;
    SeekBar seekBar_intervalTime;
    private static int MAX_intervalTime = 600;
    private static int MIN_intervalTime = 1;
    double minutesUser;
    boolean first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_notifications);

        imgButton_back = findViewById(R.id.imageButton_buttonBack);
        imgButton_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        first = true;

        textView_intervalTime = findViewById(R.id.textView_intervals_time);
        minutesUser = Singleton.getInstance().getCurrUser().getWokeInterval()/10.0;
        Log.d("INTERVAL", "User: " + Double.toString(minutesUser));
        String intervalTime = (minutesUser > 1.0? ((int)minutesUser+" mins"):((int)(minutesUser*60)+" secs"));
        textView_intervalTime.setText(intervalTime);

        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // updated continuously as the user slides the thumb
                if(!first){
                    double minutesChange = (progress+MIN_intervalTime)/10.0;
                    String intervalTime = (minutesChange > 1.0? ((int)minutesChange+" mins"):((int)(minutesChange*60)+" secs"));
                    textView_intervalTime.setText(intervalTime);
                    Log.d("INTERVAL", "NewChange: " + Double.toString(minutesChange));
                }
                else{
                    first = false;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // called when the user first touches the SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // called after the user finishes moving the SeekBar
                Log.d("INTERVAL", "NewStop: " + Double.toString((seekBar.getProgress()+MIN_intervalTime)/10.0));
                Singleton.getInstance().getCurrUser().setWokeInterval(seekBar.getProgress()+MIN_intervalTime);
            }
        };

        seekBar_intervalTime = findViewById(R.id.seekBar_intervals);
        seekBar_intervalTime.setProgress(((int)(minutesUser*10))-MIN_intervalTime);
        seekBar_intervalTime.setOnSeekBarChangeListener(seekBarChangeListener);
        seekBar_intervalTime.setMax(MAX_intervalTime - MIN_intervalTime);
        Log.d("INTERVAL", "SetProgress: " + Double.toString(minutesUser));
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}
