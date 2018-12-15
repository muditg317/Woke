package com.pineapple.woke;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

public class Activity_Start extends AppCompatActivity {

    View gradientRect;
    TextView gsmstTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Display screen = getWindowManager().getDefaultDisplay();


        gradientRect = (View) findViewById(R.id.gradientRectangle);

        gsmstTextView = (TextView) findViewById(R.id.gsmstTextView);
        gsmstTextView.setVisibility(View.INVISIBLE);


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

        final Animation transAnim_rect = new AlphaAnimation(0.00f, 1.00f);
        transAnim_rect.setDuration(800);
        transAnim_rect.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gsmstTextView.setVisibility(View.VISIBLE);
                        gsmstTextView.startAnimation(alphaAnim_text);
                    }
                }, 600);
            }
            public void onAnimationEnd(Animation animation) {}
            public void onAnimationRepeat(Animation animation) {}
        });

        gradientRect.startAnimation(transAnim_rect);

    }

    private void getStarted() {
        Intent intent = new Intent(this, Activity_Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        //finish();
    }
}
