package com.pineapple.woke.RoomDatabase;

import android.arch.persistence.room.TypeConverter;

import com.pineapple.woke.StudySession.SavedSession;
import com.pineapple.woke.resources.Constants;

import java.util.ArrayList;

public class SessionConverter {

    @TypeConverter
    public static ArrayList<SavedSession> fromString(String value) {
        String[] sessionStrings = value.split("::");
        ArrayList<SavedSession> sessionList = new ArrayList<>();
        for(String session : sessionStrings) {
            int indMST = session.indexOf(Constants.MST) + (Constants.MST).length();
            int indWM = session.indexOf(Constants.WM) + (Constants.WM).length();
            int indME = session.indexOf(Constants.ME) + (Constants.ME).length();
            int indWN = session.indexOf(Constants.WN) + (Constants.WN).length();
            long millisStartTime = Long.parseLong(session.substring(indMST,indMST+session.indexOf(" ",indMST)));
            int wokeMillis = Integer.parseInt(session.substring(indWM,indWM+session.indexOf(" ",indWM)));
            long millisElapsed = Long.parseLong(session.substring(indME,indME+session.indexOf(" ",indME)));
            int wokeNotifs = Integer.parseInt(session.substring(indWN,indWN+session.indexOf(" ",indWN)));
            sessionList.add(new SavedSession(millisStartTime, wokeMillis, millisElapsed, wokeNotifs));
        }
        return sessionList;
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<SavedSession> list) {
        StringBuilder builder = new StringBuilder();
        for(SavedSession session : list) {
            builder.append(session.toString());
            builder.append("::");
        }
        builder.delete(builder.length()-2,builder.length());
        return builder.toString();
    }


}
