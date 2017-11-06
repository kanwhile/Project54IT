package com.projects.mypillapp.Model;

import android.provider.BaseColumns;

/**
 * Created by นนทรักษ์ on 19/11/2558.
 */
public class Pill_history {
    public static final String TABLE = "pill_history";

    public class Column {
        public static final String DOSE = "dose";
        public static final String PILL_ID = "pill_id";
        public static final String TIMESTAMP = "timestamp";
        public static final String PILL_TIME = "pill_time";
    }

    public int id =-1;
    public int dose;
    public int pill_id;
    public String timestamp;
    public String pill_time;
}
