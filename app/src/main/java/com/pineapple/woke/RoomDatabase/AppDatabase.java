package com.pineapple.woke.RoomDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(entities = {User.class}, version = 1)
@TypeConverters(SessionConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

}
