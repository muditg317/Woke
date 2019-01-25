package com.pineapple.woke.RoomDatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.util.Log;

import com.pineapple.woke.StudySession.SavedSession;
import com.pineapple.woke.StudySession.StudySession;
import com.pineapple.woke.resources.Constants;

import java.util.ArrayList;

@Entity
public class User {

    @PrimaryKey
    private String name;

    private int notif_interval;
    private int notif_frequency;
    private int notif_delay;

    private ArrayList<SavedSession> studySessions;

    @Ignore
    private StudySession currStudySession;

    @Ignore
    public User(){ }

    public User(String name){
        this.name = name;
        this.notif_interval = Constants.DEFAULT_NOTIF_INTERVAL;
        this.notif_frequency = Constants.DEFAULT_NOTIF_FREQUENCY;
        this.notif_delay = Constants.DEFAULT_NOTIF_DELAY;
        studySessions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getNotif_interval() {
        return notif_interval/10.0;
    }
    public void setNotif_interval(int notif_interval) {
        this.notif_interval = notif_interval;
    }

    public int getNotif_frequency() {
        return notif_frequency;
    }
    public void setNotif_frequency(int notif_frequency) {
        this.notif_frequency = notif_frequency;
    }

    public int getNotif_delay() {
        return notif_delay;
    }
    public void setNotif_delay(int notif_delay) {
        this.notif_delay = notif_delay;
    }

    public ArrayList<SavedSession> getStudySessions() {
        return studySessions;
    }
    public void addStudySession(SavedSession s){
        studySessions.add(0,s);
        Log.d("User", "Study Session added to: "+name);
        Log.d("User", "Study Session duration: " + Long.toString(s.getDuration()));
    }

    public StudySession getCurrStudySession() {
        return currStudySession;
    }

    public void setCurrStudySession(StudySession currStudySession) {
        this.currStudySession = currStudySession;
    }
}
