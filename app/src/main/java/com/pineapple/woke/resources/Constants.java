package com.pineapple.woke.resources;

import android.graphics.Color;
import android.support.annotation.Dimension;

/**
 * Created by Mudit on 6/11/2018.
 */

public class Constants {
    public static final int wokeNotificationID = 123;
    public static final String CHANNEL_ID = "woke notification channel id";

    public static final String PACKAGE_NAME = "com.pineapple.woke";
    public static final String ACTION_WOKE = "com.pineapple.woke.ACTION_WOKE";

    /**
    User
     */
    public static final double NOTIF_INTERVAL_DEFAULT = 30.0;
    public static final double NOTIF_INTERVAL_MAX = 60.0;
    public static final double NOTIF_INTERVAL_MIN = 15.0;
    public static final int NOTIF_FREQUENCY_DEFAULT = 1;
    public static final int NOTIF_FREQUENCY_MAX = 3;
    public static final int NOTIF_FREQUENCY_MIN = 1;
    public static final int NOTIF_DELAY_DEFAULT = 0;
    public static final int NOTIF_DELAY_MAX = 60;

    /**
     * StudySession
     */
    public static final int COUNTDOWNINTERVAL = 100;
    public static final String ALERTTYPE_NOTIFY = "notify";
    public static final String ALERTTYPE_ALARM = "alarm";
}
