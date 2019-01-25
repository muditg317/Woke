package com.pineapple.woke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pineapple.woke.resources.Constants;
import com.pineapple.woke.resources.Singleton;

public class Activity_Settings_Alarms extends AppCompatActivity {
    ImageButton imgButton_back;

    TextView textView_delay_num;
    SeekBar seekBar_delay;
    int user_delay;

    boolean[] first;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_alarms);

        imgButton_back = findViewById(R.id.imageButton_buttonBack);
        imgButton_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        first = new boolean[]{true};

        initDelay();
    }

    private void initDelay(){
        textView_delay_num = findViewById(R.id.textView_delay_value);
        user_delay = Singleton.getInstance().getCurrUser().getNotif_delay();
        Log.d("DELAY", "User: " + Integer.toString(user_delay));
        String str_delay = user_delay +"m";
        textView_delay_num.setText(str_delay);

        SeekBar.OnSeekBarChangeListener seekBarChangeListener_delay = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // updated continuously as the user slides the thumb
                if(!first[0]){
                    int change = progress;
                    String str_delay = Integer.toString(change)+"m";
                    textView_delay_num.setText(str_delay);
                    Log.d("DELAY", "NewChange: " + Integer.toString(change));
                }
                else{
                    first[0] = false;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // called when the user first touches the SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // called after the user finishes moving the SeekBar
                Log.d("DELAY", "NewStop: " + Integer.toString(seekBar.getProgress()));
                Singleton.getInstance().getCurrUser().setNotif_delay(seekBar.getProgress());
            }
        };

        seekBar_delay = findViewById(R.id.seekBar_delay);
        seekBar_delay.setProgress(user_delay);
        seekBar_delay.setOnSeekBarChangeListener(seekBarChangeListener_delay);
        seekBar_delay.setMax(Constants.NOTIF_DELAY_MAX);
        Log.d("DELAY", "SetProgress: " + Integer.toString(user_delay));
    }
}
