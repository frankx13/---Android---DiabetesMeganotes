package com.studio.neopanda.diabetesmeganotes.database;

import android.provider.BaseColumns;

@SuppressWarnings("ALL")
public class SQliteDatabase {
    private SQliteDatabase() {

    }

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

    public static class InsulinUnits implements BaseColumns {
        public static final String TABLE_NAME = "Insulin";
        public static final String COLUMN_NAME_DATE = "Date";
        public static final String COLUMN_NAME_UNITS = "Units";
        public static final String COLUMN_NAME_EXTRA_INFOS = "Extras";
    }

    public static class Note implements BaseColumns {
        public static final String TABLE_NAME = "Note";
        public static final String COLUMN_NAME_TEXT = "Text";
    }

    public static class Objectives implements BaseColumns {
        public static final String TABLE_NAME = "Objectives";
        public static final String COLUMN_NAME_DATE = "Date";
        public static final String COLUMN_NAME_DURATION = "Durée";
        public static final String COLUMN_NAME_TYPE = "Type";
        public static final String COLUMN_NAME_DESCRIPTION = "Description";
    }

    public static class Alerts implements BaseColumns {
        public static final String TABLE_NAME = "Alerts";
        public static final String COLUMN_NAME_START_DATE = "Sdate";
        public static final String COLUMN_NAME_END_DATE = "Edate";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_TYPE = "Type";
        public static final String COLUMN_NAME_DESCRIPTION = "Description";
    }
}