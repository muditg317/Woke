package com.pineapple.woke.RoomDatabase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private final LiveData<List<User>> userList;

    private AppDatabase appDatabase;

    public UserViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        userList = appDatabase.userDao().getAll();
    }

    public LiveData<List<User>> getUserList() {
        return userList;
    }

    public void deleteUser(User user) {
        new DeleteAsyncTask(appDatabase).execute(user);
    }

    private static class DeleteAsyncTask extends AsyncTask<User, Void, Void> {

        private AppDatabase db;

        DeleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final User... params) {
            db.userDao().deleteUser(params[0]);
            return null;
        }

    }


    public void addUser(final User user) {
        new AddAsyncTask(appDatabase).execute(user);
    }

    private static class AddAsyncTask extends AsyncTask<User, Void, Void> {

        private AppDatabase db;

        AddAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final User... params) {
            db.userDao().insertAllUsers(params);
            return null;
        }

    }

    public void updateUser(final User user) {
        new UpdateAsyncTask(appDatabase).execute(user);
    }

    private static class UpdateAsyncTask extends AsyncTask<User, Void, Void> {

        private AppDatabase db;

        UpdateAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final User... params) {
            db.userDao().updateUser(params[0]);
            return null;
        }

    }

}
