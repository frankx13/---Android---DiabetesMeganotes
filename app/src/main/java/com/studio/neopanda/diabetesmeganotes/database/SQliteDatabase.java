package com.studio.neopanda.diabetesmeganotes.database;

import android.provider.BaseColumns;

@SuppressWarnings("ALL")
public class SQliteDatabase {
    private SQliteDatabase() {

    }

    public static class CurrentUser implements BaseColumns{
        public static final String TABLE_NAME = "CurrentUser";
        public static final String COLUMN_NAME_USERNAME = "Username";
        public static final String COLUMN_NAME_IMAGE_SELECTED = "Image";
    }

    public static class Users implements BaseColumns{
        public static final String TABLE_NAME = "Users";
        public static final String COLUMN_NAME_USERNAME = "Username";
        public static final String COLUMN_NAME_IMAGE_SELECTED = "Image";
        public static final String COLUMN_NAME_GLYCEMIES_ID = "Glycemies_id";
        public static final String COLUMN_NAME_INSULIN_ID = "InsulinUnits_id";
        public static final String COLUMN_NAME_NOTE_ID = "Note_id";
        public static final String COLUMN_NAME_OBJECTIVES_ID = "Objectives_id";
        public static final String COLUMN_NAME_ALERTS_ID = "Alerts_id";
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
