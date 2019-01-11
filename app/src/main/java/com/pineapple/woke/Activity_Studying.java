package com.pineapple.woke;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pineapple.woke.dialogs.DialogFragment_Notif;
import com.pineapple.woke.resources.Constants;
import com.pineapple.woke.resources.MyCallback;
import com.pineapple.woke.resources.Singleton;
import com.pineapple.woke.StudySession.StudySession;
import com.pineapple.woke.resources.Utils;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class Activity_Studying extends AppCompatActivity {

    private final static String TAG = "Activity_Studying";

    ImageButton imgButton_pause;
    ImageButton imgButton_stop;
    ImageButton imgButton_resume;
    TextView textView_pause;
    TextView textView_stop;
    TextView textView_resume;
    TextView textView_time;
    TextView textView_next;
    StudySession session;

    NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studying);

        textView_pause = findViewById(R.id.textView_buttonPause);
        textView_stop = findViewById(R.id.textView_buttonStop);
        textView_resume = findViewById(R.id.textView_buttonResume);
        imgButton_pause = findViewById(R.id.imageButton_buttonPause);
        imgButton_stop = findViewById(R.id.imageButton_buttonStop);
        imgButton_resume = findViewById(R.id.imageButton_buttonResume);
        textView_time = findViewById(R.id.textView_time);
        textView_next = findViewById(R.id.textView_next);

        imgButton_resume.setVisibility(View.INVISIBLE);
        textView_resume.setVisibility(View.INVISIBLE);

        notificationManager = Singleton.getInstance().getNotificationManager();

        imgButton_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseStudying();
            }
        });

        imgButton_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopStudying();
            }
        });

        imgButton_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resumeStudying();
            }
        });

        session = new StudySession(textView_next, textView_time,Singleton.getInstance().getCurrUser().getNotif_interval()/10.0);
        session.setNotifyCallback(new MyCallback<Integer>() {
            @Override
            public void accept(Integer wokeInterval) {

                Log.d("notify callback","creating notification");
                //TODO: doesn't this just check if the app is running? not if the app is open? might be a reason why the notification doesn't pop up outside the app
                if(Utils.isAppRunning(Activity_Studying.this, Constants.PACKAGE_NAME)) {
                    // Create an explicit intent for an Activity in your app
                /*Intent intent = new Intent(this, Activity_Studying.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);*/
                    showAlertDialog();
                } else {
                    Intent wokeIntent = new Intent(Activity_Studying.this, MyBroadcastReceiver.class);
                    wokeIntent.setAction(Constants.ACTION_WOKE);
                    wokeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
                    PendingIntent wokePendingIntent =
                            PendingIntent.getBroadcast(Activity_Studying.this, 0, wokeIntent, 0);

                    String content = getString(R.string.notifContent) + " It's been " + wokeInterval / 1000 / 60 + " minutes!";

                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(Activity_Studying.this, Constants.CHANNEL_ID)
                            .setSmallIcon(R.drawable.logo)
                            .setContentTitle(getString(R.string.notifTitle))
                            .setContentText(content)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setCategory(NotificationCompat.CATEGORY_REMINDER)
                            .addAction(R.drawable.ic_notification_button, getString(R.string.woke),
                                    wokePendingIntent)
                            .setAutoCancel(true);
                    // notificationId is a unique int for each notification that you must define
                    notificationManager.notify(Constants.wokeNotificationID, mBuilder.build());
                }


            }
        });
        Singleton.getInstance().getCurrUser().setCurrStudySession(session);
        Singleton.getInstance().getCurrUser().addStudySession(session.getSaveState());
    }

    private void showAlertDialog() {
        FragmentManager fm = getSupportFragmentManager();
        final DialogFragment_Notif dialogFragment_notif = DialogFragment_Notif.newInstance(getString(R.string.dialog_notif_title), getString(R.string.dialog_notif_message));

        dialogFragment_notif.show(fm, "fragment_alert");

        //((AlertDialog)(dialogFragment_notif.getDialog())).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorPrimaryBlue));
    }


    @Override
    public void onResume() {
        super.onResume();
        if(session.isStudying()) {
            session.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Intent intent = new Intent();
        session.finish();
        //intent.putExtra(Constants.studySessionMillis,session.getMillisElapsed());

        setResult(0,intent);
        //startActivity(intent);
        finish();
        super.onDestroy();
    }

    private void pauseStudying() {
        imgButton_pause.setVisibility(View.INVISIBLE);
        imgButton_resume.setVisibility(View.VISIBLE);
        textView_resume.setVisibility(View.VISIBLE);
        session.pauseStudying();
        Toast.makeText(getApplicationContext(),"Study session paused",Toast.LENGTH_SHORT).show();
    }

    private void stopStudying() {
        Toast.makeText(getApplicationContext(),"Study session finished",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        session.finish();
        //Singleton.getInstance().getCurrUser().addStudySession(session.getSaveState());
        setResult(0,intent);
        finish();
    }

    private void resumeStudying() {
        imgButton_pause.setVisibility(View.VISIBLE);
        imgButton_resume.setVisibility(View.INVISIBLE);
        textView_resume.setVisibility(View.INVISIBLE);
        session.resumeStudying();
        Toast.makeText(getApplicationContext(),"Study session resumed",Toast.LENGTH_SHORT).show();
    }
}
