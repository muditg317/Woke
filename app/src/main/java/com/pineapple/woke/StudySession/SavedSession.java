package com.pineapple.woke.StudySession;

public class SavedSession {
    private String TAG = "SavedSession";

    private String name;
    private long millisStartTime;
    private int millisInterval;
    private long millisDuration;
    private int numWokeNotifs;

    public SavedSession(long millisStartTime, int millisInterval) {
        this.name = "";
        this.millisDuration = 0;
        this.millisStartTime = millisStartTime;
        this.millisInterval = millisInterval;
        this.numWokeNotifs = 0;
    }

    public long getStartTime() {
        return millisStartTime;
    }

    public long getDuration() {
        return millisDuration;
    }

    public String getDurationDisplay() {
        if(millisDuration > 60*60*1000){
            int intHour = (int)millisDuration/(60*60*1000);
            String stringHour = intHour<10?"0"+Integer.toString(intHour):Integer.toString(intHour);
            int intMin = ((int)millisDuration/(60*1000))-(intHour*60);
            String stringMin = intMin<10?"0"+Integer.toString(intMin):Integer.toString(intMin);
            int intSec = ((int)millisDuration/(1000))-(intHour*60)-(intMin*60);
            String stringSec = intSec<10?"0"+Integer.toString(intSec):Integer.toString(intSec);
            return stringHour + ":" + stringMin + ":" + stringSec;
        }
        else{
            int intMin = ((int)millisDuration/(60*1000));
            String stringMin = intMin<10?"0"+Integer.toString(intMin):Integer.toString(intMin);
            int intSec = ((int)millisDuration/(1000))-(intMin*60);
            String stringSec = intSec<10?"0"+Integer.toString(intSec):Integer.toString(intSec);
            return stringMin + ":" + stringSec;
        }
    }

    public int getInterval() {
        return millisInterval;
    }

    public int getNumWokeNotifs() {
        return numWokeNotifs;
    }

    void setDuration(long millisDuration) {
        this.millisDuration = millisDuration;
    }

    void setNumWokeNotifs(int numWokeNotifs) {
        this.numWokeNotifs = numWokeNotifs;
    }

    public String toString() {
        return "oowa";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
