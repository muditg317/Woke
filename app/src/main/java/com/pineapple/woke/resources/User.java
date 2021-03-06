package com.pineapple.woke.resources;

import android.util.Log;

import com.pineapple.woke.StudySession.SavedSession;
import com.pineapple.woke.StudySession.StudySession;
import com.pineapple.woke.resources.Constants;

import java.util.ArrayList;

public class User {

    private String name = "";

    private double notif_interval;
    private int notif_frequency;
    private int notif_delay;

    private ArrayList<SavedSession> studySessions;

    private StudySession currStudySession;

    public User(String name){
        this.name = name;
        this.notif_interval = Constants.NOTIF_INTERVAL_DEFAULT;
        this.notif_frequency = Constants.NOTIF_FREQUENCY_DEFAULT;
        this.notif_delay = Constants.NOTIF_DELAY_DEFAULT;
        studySessions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getNotif_interval() {
        return notif_interval;
    }
    public void setNotif_interval(double notif_interval) {
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
    void setStudySessions(ArrayList<SavedSession> studySessions) {
        this.studySessions = studySessions;
    }

    public StudySession getCurrStudySession() {
        return currStudySession;
    }

    public void setCurrStudySession(StudySession currStudySession) {
        this.currStudySession = currStudySession;
    }
}
