package com.pineapple.woke.StudySession;

import android.arch.persistence.room.Entity;

@Entity
public class SavedSession {
    private String TAG = "SavedSession";

    private long millisStartTime;
    private int wokeMillis;
    private long millisElapsed;
    private int wokeNotifs;

    public SavedSession(long millisStartTime, int wokeMillis) {
        this.millisStartTime = millisStartTime;
        this.wokeMillis = wokeMillis;
    }

    public long getStartTime() {
        return millisStartTime;
    }

    public long getDuration() {
        return millisElapsed;
    }

    public int getInterval() {
        return wokeMillis;
    }

    public int getWokeNotifs() {
        return wokeNotifs;
    }

    void setDuration(long millisElapsed) {
        this.millisElapsed = millisElapsed;
    }

    void setWokeNotifs(int wokeNotifs) {
        this.wokeNotifs = wokeNotifs;
    }

    public String toString() {
        return "oowa";
    }

}
