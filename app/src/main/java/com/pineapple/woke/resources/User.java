package com.pineapple.woke.resources;

import android.util.Log;

import java.util.ArrayList;

public class User {

    private String name;
    private double notifInterval;
    private ArrayList<StudySession> studySessions;

    public User(){
        name = null;
    }
    public User(String n, double min){
        name = n;
        notifInterval = min;
        studySessions = new ArrayList<StudySession>();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getNotifInterval() {
        return notifInterval;
    }
    public void setNotifInterval(double notifInterval) {
        this.notifInterval = notifInterval;
    }

    public ArrayList<StudySession> getStudySessions() {
        return studySessions;
    }
    public void addStudySession(StudySession s){
        studySessions.add(s);
        Log.d("User", "Study Session added to user");
        Log.d("User", "Study Session duration: " + Long.toString(s.getMillisElapsed()));
    }
}
