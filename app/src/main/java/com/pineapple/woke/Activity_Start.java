package com.pineapple.woke;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

public class Activity_Start extends AppCompatActivity {

    View rectangle_background;
    TextView textView_appName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Display screen = getWindowManager().getDefaultDisplay();


        rectangle_background = (View) findViewById(R.id.rectangle_background);

        textView_appName = (TextView) findViewById(R.id.textView_appName);
        textView_appName.setVisibility(View.INVISIBLE);


        final Animation alphaAnim_text = new AlphaAnimation(0.00f, 1.00f);
        alphaAnim_text.setDuration(1200);
        alphaAnim_text.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {}
            public void onAnimationEnd(Animation animation) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getStarted();
                    }
                }, 2000);
            }
            public void onAnimationRepeat(Animation animation) {}
        });

        final Animation alphaAnim_rect = new AlphaAnimation(0.00f, 1.00f);
        alphaAnim_rect.setDuration(800);
        alphaAnim_rect.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView_appName.setVisibility(View.VISIBLE);
                        textView_appName.startAnimation(alphaAnim_text);
                    }
                }, 600);
            }
            public void onAnimationEnd(Animation animation) {}
            public void onAnimationRepeat(Animation animation) {}
        });

        rectangle_background.startAnimation(alphaAnim_rect);

    }

    private void getStarted() {
        Intent intent = new Intent(this, Activity_Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}
