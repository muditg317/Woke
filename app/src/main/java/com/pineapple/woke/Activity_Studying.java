package com.pineapple.woke;

import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
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
import com.pineapple.woke.resources.MyBroadcastReceiver;
import com.pineapple.woke.resources.MyCallback;
import com.pineapple.woke.resources.Singleton;
import com.pineapple.woke.StudySession.StudySession;
import com.pineapple.woke.resources.Utils;

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

    MediaPlayer mp_notify;
    MediaPlayer mp_alarm;

//    boolean[] first;


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


        Log.d("Activity_Studying","study session: " + Singleton.getInstance().getCurrUser().getCurrStudySession());
        if(Singleton.getInstance().getCurrUser().getCurrStudySession() == null) {
            Log.d("Activity_Studying","creating new study session");
            session = new StudySession(textView_next, textView_time, Singleton.getInstance().getCurrUser().getNotif_interval());
            session.setNotifyCallback(new MyCallback<Boolean>() {
                @Override
                public void accept(Boolean alarm) {

                    Log.d("notify callback","study session: " + Singleton.getInstance().getCurrUser().getCurrStudySession());
                    String type;

                    if (!alarm) {
                        Log.d("notify callback", "creating notify notification");
                        type = Constants.ALERTTYPE_NOTIFY;
                    } else {
                        Log.d("notify callback", "creating alarm notification");
                        type = Constants.ALERTTYPE_ALARM;
                    }

                    if (Utils.isAppRunning(Activity_Studying.this)) {
                        showAlertDialog(type);
                    } else {
                        makeNotification(type);
                    }
                }
            });
            Singleton.getInstance().getCurrUser().setCurrStudySession(session);
            Singleton.getInstance().getCurrUser().addStudySession(session.getSaveState());
            Log.d("Activity_Studying","made study session: " + Singleton.getInstance().getCurrUser().getCurrStudySession());
        } else {
            Log.d("Activity_Studying","loading existing study session");
            session = Singleton.getInstance().getCurrUser().getCurrStudySession();
            session.setViews(textView_next, textView_time);
            session.setNotifyCallback(new MyCallback<Boolean>() {
                @Override
                public void accept(Boolean alarm) {

                    Log.d("notify callback","study session: " + Singleton.getInstance().getCurrUser().getCurrStudySession());
                    String type;

                    if (!alarm) {
                        Log.d("notify callback", "creating notify notification");
                        type = Constants.ALERTTYPE_NOTIFY;
                    } else {
                        Log.d("notify callback", "creating alarm notification");
                        type = Constants.ALERTTYPE_ALARM;
                    }

                    if (Utils.isAppRunning(Activity_Studying.this)) {
                        showAlertDialog(type);
                    } else {
                        makeNotification(type);
                    }
                }
            });
            Log.d("BroadcastReceiver","dismissing");
            Singleton.getInstance().getCurrUser().getCurrStudySession().dismissWokeNotification();
        }

        Uri r_notify = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Singleton.getInstance().setMp_notify(MediaPlayer.create(getApplicationContext(), r_notify));
        Uri r_alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Singleton.getInstance().setMp_alarm(MediaPlayer.create(getApplicationContext(), r_alarm));

//        first = new boolean[]{true};
        mp_notify = Singleton.getInstance().getMp_notify();
        mp_alarm = Singleton.getInstance().getMp_alarm();
//        mp_alarm.stop();
//
//        mp_alarm.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                if(!first[0]) {
//                    mp.start();
//                } else {
//                    first[0] = false;
//                }
//            }
//        });

    }

    private void makeNotification(String type){
        //tap intent
        Intent intent = new Intent(this, Activity_Studying.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
        PendingIntent pendingIntent_tap = PendingIntent.getActivity(this, 0, intent, 0);

        //action button intent
        Intent wokeIntent = new Intent(Activity_Studying.this, MyBroadcastReceiver.class);
        wokeIntent.putExtra("type", type);
        PendingIntent pendingIntent_button = PendingIntent.getBroadcast(Activity_Studying.this, 1, wokeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Boolean ongoing = false;
        Boolean autoCancel = true;
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if(type.equals(Constants.ALERTTYPE_ALARM)){
            ongoing = true;
            autoCancel = false;
            sound = null;
            if(!mp_alarm.isPlaying()) {
                Log.d("mp_alarm", "start");
                mp_alarm.start();
            }
        }

        String content = getString(R.string.notifContent) + " It's been " + Singleton.getInstance().getCurrUser().getNotif_interval() / 1000 / 60 + " minutes!";

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(Activity_Studying.this, Constants.CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(getString(R.string.notifTitle))
                .setContentText(content)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                //.setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent_tap)
                .addAction(R.drawable.ic_notification_button, getString(R.string.woke), pendingIntent_button)
                .setDeleteIntent(pendingIntent_button)
                .setSound(sound)
                .setVibrate(new long[]{0, 1000, 500, 1000, 500})
                .setAutoCancel(autoCancel)
                .setOngoing(ongoing)
                .setColorized(true)
                .setTimeoutAfter(1000);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(Constants.wokeNotificationID, mBuilder.build());
    }

    private void showAlertDialog(String type) {
        FragmentManager fm = getSupportFragmentManager();

        if(type.equals(Constants.ALERTTYPE_NOTIFY)){
            if(fm.findFragmentByTag("fragment_notify")==null){
                Log.d("SHOWALERTDIALOG", "no fragment_notify");
                final DialogFragment_Notif dialogFragment_notify = DialogFragment_Notif.newInstance(getString(R.string.dialog_notify_title), getString(R.string.dialog_notify_message), type);
                dialogFragment_notify.show(fm, "fragment_notify");
            }
            else{
                Log.d("SHOWALERTDIALOG", "found fragment_notify");
            }
            if(!mp_notify.isPlaying()) {
                Log.d("mp_notify", "start");
                mp_notify.start();
            }
        }
        else if(type.equals(Constants.ALERTTYPE_ALARM) && fm.findFragmentByTag("fragment_alarm")==null){
            if(fm.findFragmentByTag("fragment_notify")!=null){
                ((DialogFragment_Notif)fm.findFragmentByTag("fragment_notify")).getDialog().dismiss();
                Log.d("SHOWALERTDIALOG", "found and dismissed fragment_notify");
            }
            else{
                Log.d("SHOWALERTDIALOG", "no fragment_notify");
            }
            final DialogFragment_Notif dialogFragment_alarm = DialogFragment_Notif.newInstance(getString(R.string.dialog_alarm_title), getString(R.string.dialog_alarm_message), type);
            dialogFragment_alarm.show(fm, "fragment_alarm");
            if(!mp_alarm.isPlaying()) {
                Log.d("mp_alarm", "start");
                mp_alarm.start();
            }
        }
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
        session.finish();
        Intent intent = new Intent(this, Activity_Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
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
