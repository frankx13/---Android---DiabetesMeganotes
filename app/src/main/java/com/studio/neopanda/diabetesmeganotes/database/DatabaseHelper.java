package com.studio.neopanda.diabetesmeganotes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.studio.neopanda.diabetesmeganotes.models.Alert;
import com.studio.neopanda.diabetesmeganotes.models.CurrentUser;
import com.studio.neopanda.diabetesmeganotes.models.GlycemyBinder;
import com.studio.neopanda.diabetesmeganotes.models.InsulinBinder;
import com.studio.neopanda.diabetesmeganotes.models.Objective;
import com.studio.neopanda.diabetesmeganotes.models.User;
import com.studio.neopanda.diabetesmeganotes.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    //CONSTANTS
    // DB version
    private static final int DATABASE_VERSION = 8;
    private static final String DATABASE_NAME = "MeganotesReader.db";

    private static final String SQL_CREATE_ENTRIES_CURRENT_USERS =
            "CREATE TABLE " + SQliteDatabase.CurrentUser.TABLE_NAME + " (" +
                    SQliteDatabase.CurrentUser._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SQliteDatabase.CurrentUser.COLUMN_NAME_USERNAME + " TEXT," +
                    SQliteDatabase.CurrentUser.COLUMN_NAME_IMAGE_SELECTED + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES_CURRENT_USERS =
            "DROP TABLE IF EXISTS " + SQliteDatabase.CurrentUser.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_INSULINBINDER =
            "CREATE TABLE " + DataBinder.DataInsulin.TABLE_NAME + " (" +
                    DataBinder.DataInsulin._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DataBinder.DataInsulin.COLUMN_NAME_DATE + " TEXT," +
                    DataBinder.DataInsulin.COLUMN_NAME_UNITS + " INTEGER," +
                    DataBinder.DataInsulin.COLUMN_NAME_DATA_ID + " TEXT)";

    private static final String SQL_DELETE_ENTRIES_INSULINBINDER =
            "DROP TABLE IF EXISTS " + DataBinder.DataInsulin.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_GLYCEMYBINDER =
            "CREATE TABLE " + DataBinder.DataGlycemies.TABLE_NAME + " (" +
                    DataBinder.DataGlycemies._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DataBinder.DataGlycemies.COLUMN_NAME_GLYCEMY + " TEXT," +
                    DataBinder.DataGlycemies.COLUMN_NAME_DATE + " TEXT," +
                    DataBinder.DataGlycemies.COLUMN_NAME_EXTRA_INFOS + " TEXT," +
                    DataBinder.DataGlycemies.TABLE_TO_STOCK + " TEXT," +
                    DataBinder.DataGlycemies.COLUMN_NAME_DATA_ID + " TEXT)";

    private static final String SQL_DELETE_ENTRIES_GLYCEMYBINDER =
            "DROP TABLE IF EXISTS " + DataBinder.DataGlycemies.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_USERS =
            "CREATE TABLE " + SQliteDatabase.Users.TABLE_NAME + " (" +
                    SQliteDatabase.Users._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SQliteDatabase.Users.COLUMN_NAME_USERNAME + " TEXT," +
                    SQliteDatabase.Users.COLUMN_NAME_IMAGE_SELECTED + " INTEGER," +
                    SQliteDatabase.Users.COLUMN_NAME_GLYCEMIES_ID + " INTEGER," +
                    SQliteDatabase.Users.COLUMN_NAME_INSULIN_ID + " INTEGER," +
                    SQliteDatabase.Users.COLUMN_NAME_NOTE_ID + " INTEGER," +
                    SQliteDatabase.Users.COLUMN_NAME_OBJECTIVES_ID + " INTEGER," +
                    SQliteDatabase.Users.COLUMN_NAME_ALERTS_ID + " INTEGER," +
                    "FOREIGN KEY (" + SQliteDatabase.Users.COLUMN_NAME_GLYCEMIES_ID + ") REFERENCES " + SQliteDatabase.InsulinUnits._ID + " ," +
                    "FOREIGN KEY (" + SQliteDatabase.Users.COLUMN_NAME_INSULIN_ID + ") REFERENCES " + SQliteDatabase.InsulinUnits._ID + " ," +
                    "FOREIGN KEY (" + SQliteDatabase.Users.COLUMN_NAME_NOTE_ID + ") REFERENCES " + SQliteDatabase.Note._ID + " ," +
                    "FOREIGN KEY (" + SQliteDatabase.Users.COLUMN_NAME_OBJECTIVES_ID + ") REFERENCES " + SQliteDatabase.Objectives._ID + " ," +
                    "FOREIGN KEY (" + SQliteDatabase.Users.COLUMN_NAME_ALERTS_ID + ") REFERENCES " + SQliteDatabase.Glycemies._ID + ")";

    private static final String SQL_DELETE_ENTRIES_USERS =
            "DROP TABLE IF EXISTS " + SQliteDatabase.Users.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_INSULIN =
            "CREATE TABLE " + SQliteDatabase.InsulinUnits.TABLE_NAME + " (" +
                    SQliteDatabase.InsulinUnits._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SQliteDatabase.InsulinUnits.COLUMN_NAME_UNITS + " INTEGER," +
                    SQliteDatabase.InsulinUnits.COLUMN_NAME_EXTRA_INFOS + " TEXT," +
                    SQliteDatabase.InsulinUnits.COLUMN_NAME_DATE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES_INSULIN =
            "DROP TABLE IF EXISTS " + SQliteDatabase.InsulinUnits.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_NOTE =
            "CREATE TABLE " + SQliteDatabase.Note.TABLE_NAME + " (" +
                    SQliteDatabase.Note._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SQliteDatabase.Note.COLUMN_NAME_TEXT + " TEXT)";

    private static final String SQL_DELETE_ENTRIES_NOTE =
            "DROP TABLE IF EXISTS " + SQliteDatabase.Note.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_OBJECTIVES =
            "CREATE TABLE " + SQliteDatabase.Objectives.TABLE_NAME + " (" +
                    SQliteDatabase.Objectives._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SQliteDatabase.Objectives.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    SQliteDatabase.Objectives.COLUMN_NAME_DURATION + " INTEGER," +
                    SQliteDatabase.Objectives.COLUMN_NAME_TYPE + " TEXT," +
                    SQliteDatabase.Objectives.COLUMN_NAME_DATE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES_OBJECTIVES =
            "DROP TABLE IF EXISTS " + SQliteDatabase.Objectives.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_ALERTS =
            "CREATE TABLE " + SQliteDatabase.Alerts.TABLE_NAME + " (" +
                    SQliteDatabase.Alerts._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
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
        db.execSQL(SQL_CREATE_ENTRIES_USERS);
        db.execSQL(SQL_CREATE_ENTRIES_INSULIN);
        db.execSQL(SQL_CREATE_ENTRIES_NOTE);
        db.execSQL(SQL_CREATE_ENTRIES_OBJECTIVES);
        db.execSQL(SQL_CREATE_ENTRIES_ALERTS);
        db.execSQL(SQL_CREATE_ENTRIES_CURRENT_USERS);
        db.execSQL(SQL_CREATE_ENTRIES_GLYCEMYBINDER);
        db.execSQL(SQL_CREATE_ENTRIES_INSULINBINDER);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES_USERS);
        db.execSQL(SQL_DELETE_ENTRIES_INSULIN);
        db.execSQL(SQL_DELETE_ENTRIES_NOTE);
        db.execSQL(SQL_DELETE_ENTRIES_OBJECTIVES);
        db.execSQL(SQL_DELETE_ENTRIES_ALERTS);
        db.execSQL(SQL_DELETE_ENTRIES_CURRENT_USERS);
        db.execSQL(SQL_DELETE_ENTRIES_GLYCEMYBINDER);
        db.execSQL(SQL_DELETE_ENTRIES_INSULINBINDER);
        onCreate(db);
    }

    public void writeActiveUserInDB(String username, String imgSelection) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SQliteDatabase.CurrentUser.COLUMN_NAME_USERNAME, username);
        values.put(SQliteDatabase.CurrentUser.COLUMN_NAME_IMAGE_SELECTED, imgSelection);

        // Insert the new row, returning the primary key value of the new row
        db.insert(SQliteDatabase.CurrentUser.TABLE_NAME, null, values);
    }

    public void bindGlycemyData(String glycemy, String date, String tableToStock,
                                String userID, String extras) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DataBinder.DataGlycemies.COLUMN_NAME_GLYCEMY, glycemy);
        values.put(DataBinder.DataGlycemies.COLUMN_NAME_DATE, date);
        values.put(DataBinder.DataGlycemies.TABLE_TO_STOCK, tableToStock);
        values.put(DataBinder.DataGlycemies.COLUMN_NAME_DATA_ID, userID);
        values.put(DataBinder.DataGlycemies.COLUMN_NAME_EXTRA_INFOS, extras);

        // Insert the new row, returning the primary key value of the new row
        db.insert(DataBinder.DataGlycemies.TABLE_NAME, null, values);
    }


    public List<GlycemyBinder> getBindedGlycemyData(String dataID) {
        List<GlycemyBinder> usersList = new ArrayList<>();
        SQLiteDatabase sQliteDatabase = getReadableDatabase();
        String[] field = {DataBinder.DataGlycemies.COLUMN_NAME_GLYCEMY, DataBinder.DataGlycemies.COLUMN_NAME_DATE, DataBinder.DataGlycemies.COLUMN_NAME_DATA_ID};
        Cursor c = sQliteDatabase.query(DataBinder.DataGlycemies.TABLE_NAME, field, null, null, null, null, null);

        int level = c.getColumnIndex(DataBinder.DataGlycemies.COLUMN_NAME_GLYCEMY);
        int date = c.getColumnIndex(DataBinder.DataGlycemies.COLUMN_NAME_DATE);
        int dataId = c.getColumnIndex(DataBinder.DataGlycemies.COLUMN_NAME_DATA_ID);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String levelData = c.getString(date);
            String dateData = c.getString(level);
            String dIdData = c.getString(dataId);

            if (dIdData.equals(dataID)) {
                usersList.add(new GlycemyBinder(dateData, levelData, dIdData));
            }
        }

        getReadableDatabase().close();
        c.close();

        return usersList;
    }

    public void bindInsulinData(String units, String userId) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();
        String todayDate = DateUtils.calculateDateOfToday();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DataBinder.DataInsulin.COLUMN_NAME_DATE, todayDate);
        values.put(DataBinder.DataInsulin.COLUMN_NAME_UNITS, units);
        values.put(DataBinder.DataInsulin.COLUMN_NAME_DATA_ID, userId);

        // Insert the new row, returning the primary key value of the new row
        db.insertOrThrow(DataBinder.DataInsulin.TABLE_NAME, null, values);
    }

    public List<InsulinBinder> getBindedInsulinData(String dataID) {
        List<InsulinBinder> injectionsList = new ArrayList<>();
        SQLiteDatabase sQliteDatabase = getReadableDatabase();
        String[] field = {
                DataBinder.DataInsulin.COLUMN_NAME_DATE,
                DataBinder.DataInsulin.COLUMN_NAME_UNITS,
                DataBinder.DataInsulin.COLUMN_NAME_DATA_ID};
        Cursor c = sQliteDatabase.query(DataBinder.DataInsulin.TABLE_NAME, field, null, null, null, null, null);

        int date = c.getColumnIndex(DataBinder.DataInsulin.COLUMN_NAME_DATE);
        int insulinUnits = c.getColumnIndex(DataBinder.DataInsulin.COLUMN_NAME_UNITS);
        int userId = c.getColumnIndex(DataBinder.DataInsulin.COLUMN_NAME_DATA_ID);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String dateData = c.getString(date);
            String insulinData = c.getString(insulinUnits);
            String dataIdData = c.getString(userId);

            if (dataIdData.equals(dataID)) {
                injectionsList.add(new InsulinBinder(dateData, insulinData, dataIdData));
            }
        }

        getWritableDatabase().close();
        c.close();

        return injectionsList;
    }

    public void resetActiveUserInDB(String[] selectionArgs) {
        SQLiteDatabase db = getReadableDatabase();
        // Define 'where' part of query.
        String selection = SQliteDatabase.CurrentUser.COLUMN_NAME_USERNAME + " LIKE ?";
        // Issue SQL statement => indicate the numbers of rows deleted
        db.delete(SQliteDatabase.CurrentUser.TABLE_NAME, selection, selectionArgs);
    }

    public List<CurrentUser> getActiveUserInDB() {
        List<CurrentUser> usersList = new ArrayList<>();
        SQLiteDatabase sQliteDatabase = getReadableDatabase();
        String[] field = {SQliteDatabase.CurrentUser.COLUMN_NAME_USERNAME, SQliteDatabase.CurrentUser.COLUMN_NAME_IMAGE_SELECTED};
        Cursor c = sQliteDatabase.query(SQliteDatabase.CurrentUser.TABLE_NAME, field, null, null, null, null, null);

        int username = c.getColumnIndex(SQliteDatabase.CurrentUser.COLUMN_NAME_USERNAME);
        int img = c.getColumnIndex(SQliteDatabase.CurrentUser.COLUMN_NAME_IMAGE_SELECTED);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String dateData = c.getString(username);
            String levelData = c.getString(img);

            usersList.add(new CurrentUser(dateData, levelData));
        }

        getReadableDatabase().close();
        c.close();

        return usersList;
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    //TODO replace the method with the Glycemies data from DataBinder
//    public List<String> getGlycemiesInTimePeriod(String today, String target) {
//        List<String> text = new ArrayList<>();
//        String selectQuery = "SELECT * FROM Glycemies WHERE Date >= '" + target + "' AND Date <= '" + today + "'";
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        try {
//            Cursor cursor = db.rawQuery(selectQuery, null);
//            if (cursor != null && cursor.getCount() > 0) {
//                cursor.moveToFirst();
//                do {
//                    text.add(cursor.getString(3));
//                }
//                while (cursor.moveToNext());
//            } else
//                return null;
//            db.close();
//            cursor.close();
//        } catch (Exception e) {
//            System.out.println("Exception throw in SQLiteDBHandler" + e);
//        }
//        return text;
//    }

    //TODO replace the method with the Glycemies data from DataBinder
//    public List<String> getAverageGlycemies(String today, String target) {
//        List<String> text = new ArrayList<>();
//        String selectQuery = "SELECT * FROM Glycemies WHERE Date >= '" + target + "' AND Date <= '" + today + "'";
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        try {
//            Cursor cursor = db.rawQuery(selectQuery, null);
//            if (cursor != null && cursor.getCount() > 0) {
//                cursor.moveToFirst();
//                do {
//                    text.add(cursor.getString(1));
//                }
//                while (cursor.moveToNext());
//            } else
//                return null;
//            db.close();
//            cursor.close();
//        } catch (Exception e) {
//            System.out.println("Exception throw in SQLiteDBHandler" + e);
//        }
//        return text;
//    }

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

    public void writeUserssInDB(String username, String imgSelection) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SQliteDatabase.Users.COLUMN_NAME_USERNAME, username);
        values.put(SQliteDatabase.Users.COLUMN_NAME_IMAGE_SELECTED, imgSelection);

        // Insert the new row, returning the primary key value of the new row
        db.insert(SQliteDatabase.Users.TABLE_NAME, null, values);
    }

    public List<User> getUsers() {
        List<User> usersList = new ArrayList<>();
        SQLiteDatabase sQliteDatabase = getReadableDatabase();
        String[] field = {SQliteDatabase.Users.COLUMN_NAME_USERNAME, SQliteDatabase.Users.COLUMN_NAME_IMAGE_SELECTED};
        Cursor c = sQliteDatabase.query(SQliteDatabase.Users.TABLE_NAME, field, null, null, null, null, null);

        int username = c.getColumnIndex(SQliteDatabase.Users.COLUMN_NAME_USERNAME);
        int img = c.getColumnIndex(SQliteDatabase.Users.COLUMN_NAME_IMAGE_SELECTED);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String usernameData = c.getString(username);
            String imgData = c.getString(img);

            usersList.add(new User(usernameData, imgData));
        }

        getReadableDatabase().close();
        c.close();

        return usersList;
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

    public void deleteInjectionInDB(String[] selectionArgs) {
        SQLiteDatabase db = getReadableDatabase();
        // Define 'where' part of query.
        String selection = DataBinder.DataInsulin._ID + " LIKE ?";
        // Issue SQL statement => indicate the numbers of rows deleted
        int deletedRows = db.delete(DataBinder.DataInsulin.TABLE_NAME, selection, selectionArgs);
    }

    public void deleteUserInDB(String[] selectionArgs) {
        SQLiteDatabase db = getReadableDatabase();
        // Define 'where' part of query.
        String selection = SQliteDatabase.Users.COLUMN_NAME_USERNAME + " LIKE ?";
        // Issue SQL statement => indicate the numbers of rows deleted
        int deletedRows = db.delete(SQliteDatabase.Users.TABLE_NAME, selection, selectionArgs);
    }

    public void updateAuthInDB() {
        SQLiteDatabase db = getWritableDatabase();

        // New value for one column
        String title = "Joseff";
        ContentValues values = new ContentValues();
        values.put(SQliteDatabase.Users.COLUMN_NAME_USERNAME, title);

        // Which row to update, based on the title
        String selection = SQliteDatabase.Users.COLUMN_NAME_USERNAME + " LIKE ?";
        String[] selectionArgs = {"Joseph"};

        //Return the numbers of updated rows
        int count = db.update(
                SQliteDatabase.Users.TABLE_NAME,
                values,
                selection,
                selectionArgs);
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
