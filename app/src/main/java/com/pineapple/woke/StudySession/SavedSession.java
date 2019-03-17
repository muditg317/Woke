package com.pineapple.woke.StudySession;

import com.pineapple.woke.resources.Constants;

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

    public SavedSession(long millisStartTime, int wokeMillis, long millisElapsed, int wokeNotifs) {
        this.millisStartTime = millisStartTime;
        this.wokeMillis = wokeMillis;
        this.millisElapsed = millisElapsed;
        this.wokeNotifs = wokeNotifs;
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
        return "Saved Session at "
                + Constants.MST + millisStartTime
                + Constants.WM + wokeMillis
                + Constants.ME + millisElapsed
                + Constants.WN + wokeNotifs;
    }

}
