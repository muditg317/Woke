package com.pineapple.woke;

import android.animation.ObjectAnimator;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pineapple.woke.resources.Constants;
import com.pineapple.woke.resources.Singleton;
import com.pineapple.woke.resources.User;


public class Activity_Start extends AppCompatActivity {

    View rectangle_background;
    TextView textView_appName;
    EditText editText_userName;
    ImageButton imgButton_submit;
    TextView textView_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Display screen = getWindowManager().getDefaultDisplay();
        rectangle_background = (View) findViewById(R.id.rectangle_background);
        textView_appName = (TextView) findViewById(R.id.textView_appName);
        editText_userName = findViewById(R.id.editText_userName);
        imgButton_submit = findViewById(R.id.imageButton_buttonSubmit);
        textView_submit = findViewById(R.id.textView_buttonSubmit);
        textView_appName.setVisibility(View.INVISIBLE);
        editText_userName.setVisibility(View.INVISIBLE);
        imgButton_submit.setVisibility(View.INVISIBLE);
        textView_submit.setVisibility(View.INVISIBLE);

        imgButton_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStarted(new User(editText_userName.getText().toString()));
            }
        });

        final Animation alphaAnim_text2 = new AlphaAnimation(0.0f, 1.0f);
        alphaAnim_text2.setDuration(1200);
        alphaAnim_text2.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {}
            public void onAnimationEnd(Animation animation) {}
            public void onAnimationRepeat(Animation animation) {}
        });

        final Animation alphaAnim_imgButton1 = new AlphaAnimation(0.0f, 1.0f);
        alphaAnim_imgButton1.setDuration(1200);
        alphaAnim_imgButton1.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {}
            public void onAnimationEnd(Animation animation) {}
            public void onAnimationRepeat(Animation animation) {}
        });

        final Animation alphaAnim_editText1 = new AlphaAnimation(0.0f, 1.0f);
        alphaAnim_editText1.setDuration(1200);
        alphaAnim_editText1.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                imgButton_submit.setVisibility(View.VISIBLE);
                textView_submit.setVisibility(View.VISIBLE);
                imgButton_submit.startAnimation(alphaAnim_imgButton1);
                textView_submit.startAnimation(alphaAnim_text2);
            }
            public void onAnimationEnd(Animation animation) {}
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
                        ObjectAnimator transAnim_text1 = ObjectAnimator.ofFloat(textView_appName, "translationY", -160f);
                        transAnim_text1.setDuration(1200);
                        transAnim_text1.start();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                editText_userName.setVisibility(View.VISIBLE);
                                editText_userName.startAnimation(alphaAnim_editText1);
                            }
                        }, 600);
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

        /*AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();
        Singleton.getInstance().setAppDatabase(db);

        List<User> users = db.userDao().getAll();
        if(!users.isEmpty()) {
            getStarted(users.get(0));
        }*/

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(Constants.CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Singleton.getInstance().setNotificationManager(NotificationManagerCompat.from(this));


        Uri r_notify = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Singleton.getInstance().setMp_notify(MediaPlayer.create(getApplicationContext(), r_notify));
        Uri r_alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Singleton.getInstance().setMp_alarm(MediaPlayer.create(getApplicationContext(), r_alarm));

    }

    private void getStarted(User user) {
        Singleton.getInstance().setCurrUser(user);

        Intent intent = new Intent(this, Activity_Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}
