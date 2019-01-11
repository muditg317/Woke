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

    TextView textView_intervals_num;
    SeekBar seekBar_intervals;
    private static int MAX_intervals = 600;
    private static int MIN_intervals = 1;
    double user_interval;

    TextView textView_frequency_num;
    SeekBar seekBar_frequency;
    private static int MAX_frequency = 5;
    private static int MIN_frequency = 1;
    int user_frequency;

    TextView textView_delay_num;
    SeekBar seekBar_delay;
    private static int MAX_delay = 60;
    int user_delay;

    boolean[] first;

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

        first = new boolean[]{true, true, true};

        initInterval();
        initFrequency();
        initDelay();

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

    private void initInterval(){
        textView_intervals_num = findViewById(R.id.textView_intervals_value);
        user_interval = Singleton.getInstance().getCurrUser().getNotif_interval();
        Log.d("INTERVAL", "User: " + Double.toString(user_interval));
        String str_interval = (user_interval > 1.0? ((int) user_interval +"m"):((int)(user_interval *60)+"s"));
        textView_intervals_num.setText(str_interval);

        SeekBar.OnSeekBarChangeListener seekBarChangeListener_interval = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // updated continuously as the user slides the thumb
                if(!first[0]){
                    double change = (progress+ MIN_intervals)/10.0;
                    String str_interval = (change > 1.0? ((int)change+"m"):((int)(change*60)+"s"));
                    textView_intervals_num.setText(str_interval);
                    Log.d("INTERVAL", "NewChange: " + Double.toString(change));
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
                Log.d("INTERVAL", "NewStop: " + Double.toString((seekBar.getProgress()+ MIN_intervals)/10.0));
                Singleton.getInstance().getCurrUser().setNotif_interval(seekBar.getProgress()+ MIN_intervals);
            }
        };

        seekBar_intervals = findViewById(R.id.seekBar_intervals);
        seekBar_intervals.setProgress(((int)(user_interval *10))- MIN_intervals);
        seekBar_intervals.setOnSeekBarChangeListener(seekBarChangeListener_interval);
        seekBar_intervals.setMax(MAX_intervals - MIN_intervals);
        Log.d("INTERVAL", "SetProgress: " + Double.toString(user_interval));
    }

    private void initFrequency(){
        textView_frequency_num = findViewById(R.id.textView_frequency_value);
        user_frequency = Singleton.getInstance().getCurrUser().getNotif_frequency();
        Log.d("FREQUENCY", "User: " + Integer.toString(user_frequency));
        String str_frequency = Integer.toString(user_frequency);
        textView_frequency_num.setText(str_frequency);

        SeekBar.OnSeekBarChangeListener seekBarChangeListener_frequency = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // updated continuously as the user slides the thumb
                if(!first[1]){
                    int change = (progress+MIN_frequency);
                    String str_frequency = Integer.toString(change);
                    textView_frequency_num.setText(str_frequency);
                    Log.d("FREQUENCY", "NewChange: " + Integer.toString(change));
                }
                else{
                    first[1] = false;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // called when the user first touches the SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // called after the user finishes moving the SeekBar
                Log.d("FREQUENCY", "NewStop: " + Integer.toString(seekBar.getProgress()+MIN_frequency));
                Singleton.getInstance().getCurrUser().setNotif_frequency(seekBar.getProgress()+MIN_frequency);
            }
        };

        seekBar_frequency = findViewById(R.id.seekBar_frequency);
        seekBar_frequency.setProgress(user_frequency - MIN_frequency);
        seekBar_frequency.setOnSeekBarChangeListener(seekBarChangeListener_frequency);
        seekBar_frequency.setMax(MAX_frequency - MIN_frequency);
        Log.d("FREQUENCY", "SetProgress: " + Integer.toString(user_frequency));
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
                if(!first[2]){
                    int change = progress;
                    String str_delay = Integer.toString(change)+"m";
                    textView_delay_num.setText(str_delay);
                    Log.d("DELAY", "NewChange: " + Integer.toString(change));
                }
                else{
                    first[2] = false;
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
        seekBar_delay.setMax(MAX_delay);
        Log.d("DELAY", "SetProgress: " + Integer.toString(user_delay));
    }
}
