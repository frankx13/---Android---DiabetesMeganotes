package com.studio.neopanda.diabetesmeganotes;

import android.provider.BaseColumns;

public class SQliteDatabase {
    private SQliteDatabase() {}

    /* Inner class that defines the table contents */
    public static class Credentials implements BaseColumns {
        public static final String TABLE_NAME = "Credentials";
        public static final String COLUMN_NAME_USERNAME = "Username";
        public static final String COLUMN_NAME_PASSWORD = "Password";
    }

    public static class Glycemies implements BaseColumns {
        public static final String TABLE_NAME = "Glycemies";
        public static final String COLUMN_NAME_DATE = "Date";
        public static final String COLUMN_NAME_GLYCEMY = "Glycemy";
        public static final String COLUMN_NAME_EXTRA_INFOS = "Extras";
    }
}
