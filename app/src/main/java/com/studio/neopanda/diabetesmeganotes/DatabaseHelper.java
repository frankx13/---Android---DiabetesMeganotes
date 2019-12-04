package com.studio.neopanda.diabetesmeganotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MeganotesReader.db";
    private static final String SQL_CREATE_ENTRIES_CREDENTIALS =
            "CREATE TABLE " + SQliteDatabase.Credentials.TABLE_NAME + " (" +
                    SQliteDatabase.Credentials._ID + " INTEGER PRIMARY KEY," +
                    SQliteDatabase.Credentials.COLUMN_NAME_PASSWORD + " TEXT," +
                    SQliteDatabase.Credentials.COLUMN_NAME_USERNAME + " TEXT)";
    private static final String SQL_DELETE_ENTRIES_CREDENTIALS =
            "DROP TABLE IF EXISTS " + SQliteDatabase.Credentials.TABLE_NAME;
    private static final String SQL_CREATE_ENTRIES_GLYCEMIES =
            "CREATE TABLE " + SQliteDatabase.Glycemies.TABLE_NAME + " (" +
                    SQliteDatabase.Glycemies._ID + " INTEGER PRIMARY KEY," +
                    SQliteDatabase.Glycemies.COLUMN_NAME_GLYCEMY + " DOUBLE," +
                    SQliteDatabase.Glycemies.COLUMN_NAME_EXTRA_INFOS + " TEXT," +
                    SQliteDatabase.Glycemies.COLUMN_NAME_DATE + " TEXT)";
    private static final String SQL_DELETE_ENTRIES_GLYCEMIES =
            "DROP TABLE IF EXISTS " + SQliteDatabase.Glycemies.TABLE_NAME;
    private static final String SQL_CREATE_ENTRIES_INSULIN =
            "CREATE TABLE " + SQliteDatabase.InsulinUnits.TABLE_NAME + " (" +
                    SQliteDatabase.InsulinUnits._ID + " INTEGER PRIMARY KEY," +
                    SQliteDatabase.InsulinUnits.COLUMN_NAME_UNITS + " INTEGER," +
                    SQliteDatabase.InsulinUnits.COLUMN_NAME_EXTRA_INFOS + " TEXT," +
                    SQliteDatabase.InsulinUnits.COLUMN_NAME_DATE + " TEXT)";
    private static final String SQL_DELETE_ENTRIES_INSULIN =
            "DROP TABLE IF EXISTS " + SQliteDatabase.InsulinUnits.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_CREDENTIALS);
        db.execSQL(SQL_CREATE_ENTRIES_GLYCEMIES);
        db.execSQL(SQL_CREATE_ENTRIES_INSULIN);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES_CREDENTIALS);
        db.execSQL(SQL_DELETE_ENTRIES_GLYCEMIES);
        db.execSQL(SQL_DELETE_ENTRIES_INSULIN);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}