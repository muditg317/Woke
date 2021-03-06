package com.pineapple.woke.resources;

/**import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.pineapple.davinci.studentutils.Student;*/

import android.media.MediaPlayer;
import android.support.v4.app.NotificationManagerCompat;

import java.io.Serializable;

/**
 * Created by Mudit on 6/11/2018.
 */

public class Singleton implements Serializable {

    private static volatile Singleton ourInstance;
    private boolean signedInSuccess;
    private User currUser;
    private NotificationManagerCompat notificationManagerCompat;
    private MediaPlayer mp_notify;
    private MediaPlayer mp_alarm;
    /*private GoogleSignInClient gsiClient;
    private GoogleApiClient gapiClient;
    private GoogleSignInAccount gsiAccount;
    private FirebaseUser fireUser;
    private DatabaseReference databaseRef;*/

    private Singleton() {
        //Prevent from the reflection api
        if (ourInstance != null){
            throw new MyException("Use getInstance() method to get the single instance of this class.");
        }
        signedInSuccess = false;
    }

    public static Singleton getInstance() {
        if (ourInstance == null) { //if there is no instance available... create new one
            synchronized (Singleton.class) {
                if (ourInstance == null) ourInstance = new Singleton();
            }
        }
        return ourInstance;
    }

    protected Singleton readResolve() {
        //Make singleton from serialize and deserialize operation.
        return getInstance();
    }

    public User getCurrUser() {
        if(currUser == null) {
            throw new MyException("There is no student profile yet");
        }
        return currUser;
    }

    public void setCurrUser(User currUser) {
        if(this.currUser != null) {
            throw new MyException("Cannot create new profile");
        }
        this.currUser = currUser;
    }

    public void setMp_notify(MediaPlayer mp_notify) {
        this.mp_notify = mp_notify;
    }

    public MediaPlayer getMp_notify() {
        if(mp_notify == null) {
            throw new MyException("There is no notify sound yet");
        }
        return mp_notify;
    }

    public void setMp_alarm(MediaPlayer mp_alarm) {
        this.mp_alarm = mp_alarm;
    }

    public MediaPlayer getMp_alarm() {
        if(mp_alarm == null) {
            throw new MyException("There is no alarm sound yet");
        }
        return mp_alarm;
    }

    /*public GoogleSignInClient getGsiClient() {
        if(gsiClient == null) {
            throw new MyException("There is no gsi account yet");
        }
        return gsiClient;
    }

    public void setGsiClient(GoogleSignInClient gsiClient) {
        if(this.gsiClient != null) {
            throw new MyException("gsi account already exists");
        }
        this.gsiClient = gsiClient;
    }

    public GoogleApiClient getGapiClient() {
        if(gapiClient == null) {
            throw new MyException("There is no gapi client yet");
        }
        return gapiClient;
    }

    public void setGapiClient(GoogleApiClient gapiClient) {
        if(this.gapiClient != null) {
            throw new MyException("gapi client already exists");
        }
        this.gapiClient = gapiClient;
    }

    public GoogleSignInAccount getGsiAccount() {
        if(gsiAccount == null) {
            throw new MyException("There is no gsi account yet");
        }
        return gsiAccount;
    }

    public void setGsiAccount(GoogleSignInAccount gsiAccount) {
        if(this.gsiAccount != null) {
            throw new MyException("gsi account already exists");
        }
        this.gsiAccount = gsiAccount;
    }

    public FirebaseUser getFireUser() {
        if(fireUser == null) {
            throw new MyException("There is no firebase user yet");
        }
        return fireUser;
    }

    public void setFireUser(FirebaseUser fireUser) {
        if(this.fireUser != null) {
            throw new MyException("firebase user already exists");
        }
        this.fireUser = fireUser;
    }

    public DatabaseReference getDatabaseRef() {
        if(databaseRef == null) {
            throw new MyException("There is no firebase database yet");
        }
        return databaseRef;
    }

    public void setDatabaseRef(DatabaseReference databaseRef) {
        if(this.databaseRef != null) {
            throw new MyException("firebase database already exists");
        }
        this.databaseRef = databaseRef;
    }*/

    public boolean isSignedInSuccess() {
        return signedInSuccess;
    }

    public void setSignedInSuccess(boolean signedInSuccess) {
        this.signedInSuccess = signedInSuccess;
    }

    public void signOut() {
        this.currUser = null;
        /*this.gapiClient = null;
        this.gsiClient = null;
        this.gsiAccount = null;
        this.fireUser = null;
        this.databaseRef = null;*/
        this.signedInSuccess = false;
    }

    public NotificationManagerCompat getNotificationManager() {
        return notificationManagerCompat;
    }

    public void setNotificationManager(NotificationManagerCompat notificationManagerCompat) {
        this.notificationManagerCompat = notificationManagerCompat;
    }

}