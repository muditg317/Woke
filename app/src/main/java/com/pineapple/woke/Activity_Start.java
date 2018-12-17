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
import android.widget.EditText;
import android.widget.TextView;

import com.pineapple.woke.resources.Singleton;
import com.pineapple.woke.resources.User;

public class Activity_Start extends AppCompatActivity {

    View rectangle_background;
    TextView textView_appName;
    EditText editText_userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Display screen = getWindowManager().getDefaultDisplay();
        rectangle_background = (View) findViewById(R.id.rectangle_background);
        textView_appName = (TextView) findViewById(R.id.textView_appName);
        editText_userName = findViewById(R.id.editText_userName);
        textView_appName.setVisibility(View.INVISIBLE);
        editText_userName.setVisibility(View.INVISIBLE);

        final Animation alphaAnim_editText1 = new AlphaAnimation(0.0f, 1.0f);
        alphaAnim_editText1.setDuration(1200);
        alphaAnim_editText1.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {}
            public void onAnimationEnd(Animation animation) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 2000);
            }
            public void onAnimationRepeat(Animation animation) {}
        });

        final Animation transAnim_text1 = new TranslateAnimation(0.00f, 1.00f, 0.0f, 1.0f);
        transAnim_text1.setDuration(1200);
        transAnim_text1.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {}
            public void onAnimationEnd(Animation animation) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        editText_userName.setVisibility(View.VISIBLE);
                        editText_userName.startAnimation(alphaAnim_editText1);
                    }
                }, 2000);
            }
            public void onAnimationRepeat(Animation animation) {}
        });

        final Animation alphaAnim_text1 = new AlphaAnimation(0.00f, 1.00f);
        alphaAnim_text1.setDuration(1200);
        alphaAnim_text1.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {}
            public void onAnimationEnd(Animation animation) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView_appName.startAnimation(transAnim_text1);
                    }
                }, 2000);
            }
            public void onAnimationRepeat(Animation animation) {}
        });

        final Animation alphaAnim_rect1 = new AlphaAnimation(0.00f, 1.00f);
        alphaAnim_rect1.setDuration(800);
        alphaAnim_rect1.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView_appName.setVisibility(View.VISIBLE);
                        textView_appName.startAnimation(alphaAnim_text1);
                    }
                }, 600);
            }
            public void onAnimationEnd(Animation animation) {}
            public void onAnimationRepeat(Animation animation) {}
        });

        rectangle_background.startAnimation(alphaAnim_rect1);

    }

    private void getStarted() {
        Singleton.getInstance().setCurrUser(new User("mudit"));

        Intent intent = new Intent(this, Activity_Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}
