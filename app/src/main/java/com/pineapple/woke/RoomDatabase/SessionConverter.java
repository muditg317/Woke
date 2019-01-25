package com.pineapple.woke.RoomDatabase;

import android.arch.persistence.room.TypeConverter;

import com.pineapple.woke.StudySession.SavedSession;

import java.util.ArrayList;

public class SessionConverter {

    @TypeConverter
    public static ArrayList<SavedSession> fromString(String value) {
        String[] sessionStrings = value.split("::");
        ArrayList<SavedSession> sessionList = new ArrayList<>();
        for(String session : sessionStrings) {
            //SavedSession savedSession = new SavedSession(session.substring());
            sessionList.add(new SavedSession(100,900));
        }
        return sessionList;
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<SavedSession> list) {
        StringBuilder builder = new StringBuilder();
        for(SavedSession session : list) {
            builder.append(session.toString());
        }
        return builder.toString();
    }


}
