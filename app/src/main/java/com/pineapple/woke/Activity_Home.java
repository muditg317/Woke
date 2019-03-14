package com.pineapple.woke;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pineapple.woke.StudySession.SavedSession;
import com.pineapple.woke.resources.ListViewHeightExpander;
import com.pineapple.woke.resources.Singleton;

import java.util.ArrayList;

public class Activity_Home extends AppCompatActivity {
    ImageButton imgButton_start;
    ImageButton imgButton_settings;
    TextView textView_welcome;

    ArrayList<SavedSession> savedSessions;
    ListView listView;
    private static HistoryAdapter adapter;

    private final int studySessionRC = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textView_welcome = findViewById(R.id.textView_welcome);
        String welcomeText = "Welcome " + Singleton.getInstance().getCurrUser().getName();
        Log.d("DEBUG", welcomeText);
        textView_welcome.setText(welcomeText);

        imgButton_start =findViewById(R.id.imageButton_buttonStudy);
        imgButton_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStudying();
            }
        });

        imgButton_settings =findViewById(R.id.imageButton_buttonSettings);
        imgButton_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSettings();
            }
        });


        listView=(ListView)findViewById(R.id.listView_history);

        savedSessions = Singleton.getInstance().getCurrUser().getStudySessions();

        adapter= new HistoryAdapter(savedSessions,getApplicationContext());

        listView.setAdapter(adapter);
        ListViewHeightExpander.setListViewHeightBasedOnChildren(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SavedSession savedSession = savedSessions.get(position);

                Snackbar.make(view, savedSession.getName()+"\n"+savedSession.getDurationDisplay()+" Woke Notifs: "+savedSession.getNumWokeNotifs(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });
    }

    private void startStudying() {
        Intent intent = new Intent(this, Activity_Studying.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Toast.makeText(getApplicationContext(),"Study session started",Toast.LENGTH_LONG).show();
        startActivityForResult(intent,studySessionRC);//wait for returned study session length
        //finish();
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == studySessionRC) {
            //int millisSession = data.getIntExtra(Constants.studySessionMillis,0);
            //TODO: update history UI
        }
    }
    */

    private void toSettings(){
        Intent intent = new Intent(this, Activity_Settings_Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
