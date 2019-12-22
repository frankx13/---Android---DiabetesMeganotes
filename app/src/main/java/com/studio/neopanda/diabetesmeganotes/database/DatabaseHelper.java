package com.studio.neopanda.diabetesmeganotes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.studio.neopanda.diabetesmeganotes.models.Glycemy;
import com.studio.neopanda.diabetesmeganotes.models.InsulinInjection;
import com.studio.neopanda.diabetesmeganotes.models.Objective;
import com.studio.neopanda.diabetesmeganotes.models.Alert;
import com.studio.neopanda.diabetesmeganotes.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    //CONSTANTS
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "MeganotesReader.db";

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
                    SQliteDatabase.Glycemies.COLUMN_NAME_GLYCEMY + " TEXT," +
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

    private static final String SQL_CREATE_ENTRIES_NOTE =
            "CREATE TABLE " + SQliteDatabase.Note.TABLE_NAME + " (" +
                    SQliteDatabase.Note._ID + " INTEGER PRIMARY KEY," +
                    SQliteDatabase.Note.COLUMN_NAME_TEXT + " TEXT)";

    private static final String SQL_DELETE_ENTRIES_NOTE =
            "DROP TABLE IF EXISTS " + SQliteDatabase.Note.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_OBJECTIVES =
            "CREATE TABLE " + SQliteDatabase.Objectives.TABLE_NAME + " (" +
                    SQliteDatabase.Objectives._ID + " INTEGER PRIMARY KEY," +
                    SQliteDatabase.Objectives.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    SQliteDatabase.Objectives.COLUMN_NAME_DURATION + " INTEGER," +
                    SQliteDatabase.Objectives.COLUMN_NAME_TYPE + " TEXT," +
                    SQliteDatabase.Objectives.COLUMN_NAME_DATE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES_OBJECTIVES =
            "DROP TABLE IF EXISTS " + SQliteDatabase.Objectives.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_ALERTS =
            "CREATE TABLE " + SQliteDatabase.Alerts.TABLE_NAME + " (" +
                    SQliteDatabase.Alerts._ID + " INTEGER PRIMARY KEY," +
                    SQliteDatabase.Alerts.COLUMN_NAME_START_DATE + " TEXT," +
                    SQliteDatabase.Alerts.COLUMN_NAME_END_DATE + " TEXT," +
                    SQliteDatabase.Alerts.COLUMN_NAME_NAME + " TEXT," +
                    SQliteDatabase.Alerts.COLUMN_NAME_TYPE + " TEXT," +
                    SQliteDatabase.Alerts.COLUMN_NAME_DESCRIPTION + " TEXT)";

    private static final String SQL_DELETE_ENTRIES_ALERTS =
            "DROP TABLE IF EXISTS " + SQliteDatabase.Alerts.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_CREDENTIALS);
        db.execSQL(SQL_CREATE_ENTRIES_GLYCEMIES);
        db.execSQL(SQL_CREATE_ENTRIES_INSULIN);
        db.execSQL(SQL_CREATE_ENTRIES_NOTE);
        db.execSQL(SQL_CREATE_ENTRIES_OBJECTIVES);
        db.execSQL(SQL_CREATE_ENTRIES_ALERTS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES_CREDENTIALS);
        db.execSQL(SQL_DELETE_ENTRIES_GLYCEMIES);
        db.execSQL(SQL_DELETE_ENTRIES_INSULIN);
        db.execSQL(SQL_DELETE_ENTRIES_NOTE);
        db.execSQL(SQL_DELETE_ENTRIES_OBJECTIVES);
        db.execSQL(SQL_DELETE_ENTRIES_ALERTS);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public List<String> getGlycemiesInTimePeriod(String today, String target) {
        List<String> text = new ArrayList<>();
        String selectQuery = "SELECT * FROM Glycemies WHERE Date >= '" + target + "' AND Date <= '" + today + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    text.add(cursor.getString(3));
                }
                while (cursor.moveToNext());
            } else
                return null;
            db.close();
            cursor.close();
        } catch (Exception e) {
            System.out.println("Exception throw in SQLiteDBHandler" + e);
        }
        return text;
    }

    public List<String> getAverageGlycemies(String today, String target) {
        List<String> text = new ArrayList<>();
        String selectQuery = "SELECT * FROM Glycemies WHERE Date >= '" + target + "' AND Date <= '" + today + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    text.add(cursor.getString(1));
                }
                while (cursor.moveToNext());
            } else
                return null;
            db.close();
            cursor.close();
        } catch (Exception e) {
            System.out.println("Exception throw in SQLiteDBHandler" + e);
        }
        return text;
    }

    public String getNote() {
        String note;

        String selectQuery = "SELECT * FROM Note";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToLast();
            note = cursor.getString(1);
            cursor.close();
            return note;
        } else {
            return "Vous pouvez ajouter une note en cliquant ici :)";
        }
    }

    public void setNoteInDB(String note) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SQliteDatabase.Note.COLUMN_NAME_TEXT, note);

        // Insert the new row, returning the primary key value of the new row
        db.insert(SQliteDatabase.Note.TABLE_NAME, null, values);
    }

    public void setCredentialsInDB(String username, String password) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SQliteDatabase.Credentials.COLUMN_NAME_USERNAME, username);
        values.put(SQliteDatabase.Credentials.COLUMN_NAME_PASSWORD, password);

        // Insert the new row, returning the primary key value of the new row
        db.insert(SQliteDatabase.Credentials.TABLE_NAME, null, values);
    }

    public void writeInsulinUnitsInDB(String units) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();
        String todayDate = DateUtils.calculateDateOfToday();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SQliteDatabase.InsulinUnits.COLUMN_NAME_DATE, todayDate);
        values.put(SQliteDatabase.InsulinUnits.COLUMN_NAME_UNITS, units);

        // Insert the new row, returning the primary key value of the new row
        db.insertOrThrow(SQliteDatabase.InsulinUnits.TABLE_NAME, null, values);
    }

    public void writeGlycemyInDB(String date, String glycemy) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SQliteDatabase.Glycemies.COLUMN_NAME_DATE, date);
        values.put(SQliteDatabase.Glycemies.COLUMN_NAME_GLYCEMY, glycemy);

        // Insert the new row, returning the primary key value of the new row
        db.insertOrThrow(SQliteDatabase.Glycemies.TABLE_NAME, null, values);
    }

    //Checking if table is null
    public boolean isTableNotEmpty(String tableName) {
        SQLiteDatabase db = getWritableDatabase();

        String count = "SELECT count(*) FROM " + tableName;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        mcursor.close();
        return icount > 0;
    }

    public void deleteAuthInDB() {
        SQLiteDatabase db = getReadableDatabase();
        // Define 'where' part of query.
        String selection = SQliteDatabase.Credentials.COLUMN_NAME_USERNAME + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {"Josef"};
        // Issue SQL statement => indicate the numbers of rows deleted
        int deletedRows = db.delete(SQliteDatabase.Credentials.TABLE_NAME, selection, selectionArgs);
    }

    public void updateAuthInDB() {
        SQLiteDatabase db = getWritableDatabase();

        // New value for one column
        String title = "Joseff";
        ContentValues values = new ContentValues();
        values.put(SQliteDatabase.Credentials.COLUMN_NAME_USERNAME, title);

        // Which row to update, based on the title
        String selection = SQliteDatabase.Credentials.COLUMN_NAME_USERNAME + " LIKE ?";
        String[] selectionArgs = {"Joseph"};

        //Return the numbers of updated rows
        int count = db.update(
                SQliteDatabase.Credentials.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public List<InsulinInjection> getInsulinInjections() {
        List<InsulinInjection> glycemiesList = new ArrayList<>();
        SQLiteDatabase sQliteDatabase = getReadableDatabase();
        String[] field = {SQliteDatabase.InsulinUnits.COLUMN_NAME_DATE, SQliteDatabase.InsulinUnits.COLUMN_NAME_UNITS};
        Cursor c = sQliteDatabase.query(SQliteDatabase.InsulinUnits.TABLE_NAME, field, null, null, null, null, null);

        int date = c.getColumnIndex(SQliteDatabase.InsulinUnits.COLUMN_NAME_DATE);
        int insulinUnits = c.getColumnIndex(SQliteDatabase.InsulinUnits.COLUMN_NAME_UNITS);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String dateData = c.getString(date);
            String insulinData = c.getString(insulinUnits);

            glycemiesList.add(new InsulinInjection(dateData, insulinData));
        }

        getReadableDatabase().close();
        c.close();

        return glycemiesList;
    }

    public List<Glycemy> getGlycemies() {
        List<Glycemy> glycemiesList = new ArrayList<>();
        SQLiteDatabase sQliteDatabase = getReadableDatabase();
        String[] field = {SQliteDatabase.Glycemies.COLUMN_NAME_DATE, SQliteDatabase.Glycemies.COLUMN_NAME_GLYCEMY};
        Cursor c = sQliteDatabase.query(SQliteDatabase.Glycemies.TABLE_NAME, field, null, null, null, null, null);

        int date = c.getColumnIndex(SQliteDatabase.Glycemies.COLUMN_NAME_DATE);
        int level = c.getColumnIndex(SQliteDatabase.Glycemies.COLUMN_NAME_GLYCEMY);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String dateData = c.getString(date);
            String levelData = c.getString(level);

            glycemiesList.add(new Glycemy(dateData, levelData));
        }

        getReadableDatabase().close();
        c.close();

        return glycemiesList;
    }

    public void writeObjectiveInDB(String date, String duration, String type, String description) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SQliteDatabase.Objectives.COLUMN_NAME_DATE, date);
        values.put(SQliteDatabase.Objectives.COLUMN_NAME_DURATION, duration);
        values.put(SQliteDatabase.Objectives.COLUMN_NAME_TYPE, type);
        values.put(SQliteDatabase.Objectives.COLUMN_NAME_DESCRIPTION, description);

        // Insert the new row, returning the primary key value of the new row
        db.insertOrThrow(SQliteDatabase.Objectives.TABLE_NAME, null, values);
    }

    public List<Objective> getObjectives() {
        List<Objective> objectiveList = new ArrayList<>();
        SQLiteDatabase sQliteDatabase = getReadableDatabase();
        String[] field = {
                SQliteDatabase.Objectives.COLUMN_NAME_DATE,
                SQliteDatabase.Objectives.COLUMN_NAME_TYPE,
                SQliteDatabase.Objectives.COLUMN_NAME_DURATION,
                SQliteDatabase.Objectives.COLUMN_NAME_DESCRIPTION};
        Cursor c = sQliteDatabase.query(SQliteDatabase.Objectives.TABLE_NAME, field,
                null, null, null, null, null);

        int date = c.getColumnIndex(SQliteDatabase.Objectives.COLUMN_NAME_DATE);
        int type = c.getColumnIndex(SQliteDatabase.Objectives.COLUMN_NAME_TYPE);
        int duration = c.getColumnIndex(SQliteDatabase.Objectives.COLUMN_NAME_DURATION);
        int desc = c.getColumnIndex(SQliteDatabase.Objectives.COLUMN_NAME_DESCRIPTION);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String dateData = c.getString(date);
            String typeData = c.getString(type);
            String durationData = c.getString(duration);
            String descData = c.getString(desc);

            objectiveList.add(new Objective(dateData, typeData, durationData, descData));
        }

        getReadableDatabase().close();
        c.close();

        return objectiveList;
    }

    public void writeAlertInDB(String name, String description, String type, String startMoment, String endMoment) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SQliteDatabase.Alerts.COLUMN_NAME_START_DATE, startMoment);
        values.put(SQliteDatabase.Alerts.COLUMN_NAME_END_DATE, endMoment);
        values.put(SQliteDatabase.Alerts.COLUMN_NAME_TYPE, type);
        values.put(SQliteDatabase.Alerts.COLUMN_NAME_DESCRIPTION, description);
        values.put(SQliteDatabase.Alerts.COLUMN_NAME_NAME, name);

        // Insert the new row, returning the primary key value of the new row
        db.insertOrThrow(SQliteDatabase.Alerts.TABLE_NAME, null, values);
    }

    public List<Alert> getAlerts() {
        List<Alert> alertList = new ArrayList<>();
        SQLiteDatabase sQliteDatabase = getReadableDatabase();
        String[] field = {
                SQliteDatabase.Alerts.COLUMN_NAME_START_DATE,
                SQliteDatabase.Alerts.COLUMN_NAME_END_DATE,
                SQliteDatabase.Alerts.COLUMN_NAME_TYPE,
                SQliteDatabase.Alerts.COLUMN_NAME_NAME,
                SQliteDatabase.Alerts.COLUMN_NAME_DESCRIPTION};
        Cursor c = sQliteDatabase.query(SQliteDatabase.Alerts.TABLE_NAME, field,
                null, null, null, null, null);

        int sdate = c.getColumnIndex(SQliteDatabase.Alerts.COLUMN_NAME_START_DATE);
        int edate = c.getColumnIndex(SQliteDatabase.Alerts.COLUMN_NAME_END_DATE);
        int type = c.getColumnIndex(SQliteDatabase.Alerts.COLUMN_NAME_TYPE);
        int name = c.getColumnIndex(SQliteDatabase.Alerts.COLUMN_NAME_NAME);
        int description = c.getColumnIndex(SQliteDatabase.Alerts.COLUMN_NAME_DESCRIPTION);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String sdateData = c.getString(sdate);
            String edateData = c.getString(edate);
            String typeData = c.getString(type);
            String nameData = c.getString(name);
            String descData = c.getString(description);

            //TODO implement hour input
            alertList.add(new Alert(nameData, descData, typeData, sdateData, edateData, "10:00"));
        }

        getReadableDatabase().close();
        c.close();

        return alertList;
    }
}
