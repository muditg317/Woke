package com.pineapple.woke.RoomDatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
@TypeConverters(SessionConverter.class)
public interface UserDao {

    @Query("SELECT * FROM user")
    LiveData<List<User>> getAll();

//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE name LIKE :name LIMIT 1")
    User userByName(String name);

    @Insert(onConflict = REPLACE)
    void insertAllUsers(User... users);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);
}
