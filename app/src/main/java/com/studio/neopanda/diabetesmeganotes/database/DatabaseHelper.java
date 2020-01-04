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
import com.studio.neopanda.diabetesmeganotes.models.ObjectiveBinder;
import com.studio.neopanda.diabetesmeganotes.models.User;
import com.studio.neopanda.diabetesmeganotes.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    //CONSTANTS
    // DB version
    private static final int DATABASE_VERSION = 6;
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
                    SQliteDatabase.Users.COLUMN_NAME_ALERTS_ID + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES_USERS =
            "DROP TABLE IF EXISTS " + SQliteDatabase.Users.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_NOTEBINDER =
            "CREATE TABLE " + DataBinder.DataNote.TABLE_NAME + " (" +
                    DataBinder.DataNote._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DataBinder.DataNote.COLUMN_NAME_TEXT + " TEXT," +
                    DataBinder.DataNote.COLUMN_NAME_DATA_ID + " TEXT)";

    private static final String SQL_DELETE_ENTRIES_NOTEBINDER =
            "DROP TABLE IF EXISTS " + DataBinder.DataNote.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_OBJECTIVESBINDER =
            "CREATE TABLE " + DataBinder.DataObjectives.TABLE_NAME + " (" +
                    DataBinder.DataObjectives._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DataBinder.DataObjectives.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    DataBinder.DataObjectives.COLUMN_NAME_DURATION + " INTEGER," +
                    DataBinder.DataObjectives.COLUMN_NAME_TYPE + " TEXT," +
                    DataBinder.DataObjectives.COLUMN_NAME_DATE + " TEXT," +
                    DataBinder.DataObjectives.COLUMN_NAME_DATA_ID + " TEXT)";

    private static final String SQL_DELETE_ENTRIES_OBJECTIVESBINDER =
            "DROP TABLE IF EXISTS " + DataBinder.DataObjectives.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_ALERTSBINDER =
            "CREATE TABLE " + DataBinder.DataAlerts.TABLE_NAME + " (" +
                    DataBinder.DataAlerts._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DataBinder.DataAlerts.COLUMN_NAME_START_DATE + " TEXT," +
                    DataBinder.DataAlerts.COLUMN_NAME_END_DATE + " TEXT," +
                    DataBinder.DataAlerts.COLUMN_NAME_NAME + " TEXT," +
                    DataBinder.DataAlerts.COLUMN_NAME_TYPE + " TEXT," +
                    DataBinder.DataAlerts.COLUMN_NAME_TIME_HOUR + " TEXT," +
                    DataBinder.DataAlerts.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    DataBinder.DataAlerts.COLUMN_NAME_IS_ACTIVE + " TEXT," +
                    DataBinder.DataAlerts.COLUMN_NAME_DATA_ID + " TEXT)";

    private static final String SQL_DELETE_ENTRIES_ALERTSBINDER =
            "DROP TABLE IF EXISTS " + DataBinder.DataAlerts.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_USERS);
        db.execSQL(SQL_CREATE_ENTRIES_ALERTSBINDER);
        db.execSQL(SQL_CREATE_ENTRIES_CURRENT_USERS);
        db.execSQL(SQL_CREATE_ENTRIES_GLYCEMYBINDER);
        db.execSQL(SQL_CREATE_ENTRIES_INSULINBINDER);
        db.execSQL(SQL_CREATE_ENTRIES_NOTEBINDER);
        db.execSQL(SQL_CREATE_ENTRIES_OBJECTIVESBINDER);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES_USERS);
        db.execSQL(SQL_DELETE_ENTRIES_ALERTSBINDER);
        db.execSQL(SQL_DELETE_ENTRIES_CURRENT_USERS);
        db.execSQL(SQL_DELETE_ENTRIES_GLYCEMYBINDER);
        db.execSQL(SQL_DELETE_ENTRIES_INSULINBINDER);
        db.execSQL(SQL_DELETE_ENTRIES_NOTEBINDER);
        db.execSQL(SQL_DELETE_ENTRIES_OBJECTIVESBINDER);
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

    public List<String> getGlycemiesInTimePeriod(String today, String target) {
        List<String> text = new ArrayList<>();
        String selectQuery = "SELECT * FROM GlycemyBinder WHERE Date >= '" + target + "' AND Date <= '" + today + "'";
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
        String selectQuery = "SELECT * FROM GlycemyBinder WHERE Date >= '" + target + "' AND Date <= '" + today + "'";
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

    public String getNote(String userId) {
        String note = "";
        SQLiteDatabase sQliteDatabase = getReadableDatabase();
        String[] field = {
                DataBinder.DataNote.COLUMN_NAME_TEXT,
                DataBinder.DataNote.COLUMN_NAME_DATA_ID};
        Cursor c = sQliteDatabase.query(DataBinder.DataNote.TABLE_NAME, field, null, null, null, null, null);

        int text = c.getColumnIndex(DataBinder.DataNote.COLUMN_NAME_TEXT);
        int dataID = c.getColumnIndex(DataBinder.DataNote.COLUMN_NAME_DATA_ID);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String noteData = c.getString(text);
            String dataIdData = c.getString(dataID);

            if (dataIdData.equals(userId)) {
                note = noteData;
            }
        }

        getWritableDatabase().close();
        c.close();

        return note;
    }

    public void setNoteInDB(String note, String dataId) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DataBinder.DataNote.COLUMN_NAME_TEXT, note);
        values.put(DataBinder.DataNote.COLUMN_NAME_DATA_ID, dataId);

        // Insert the new row, returning the primary key value of the new row
        db.insert(DataBinder.DataNote.TABLE_NAME, null, values);
    }

    public void updateNote(String newValue, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBinder.DataNote.COLUMN_NAME_TEXT, newValue);
        db.update(DataBinder.DataNote.TABLE_NAME, values,
                DataBinder.DataNote._ID + " = ? ", new String[]{Integer.toString(id)});
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

    public void deleteObjectiveInDB(String [] selectionArgs){
        SQLiteDatabase db = getReadableDatabase();
        // Define 'where' part of query.
        String selection = DataBinder.DataObjectives._ID + " LIKE ?";
        // Issue SQL statement => indicate the numbers of rows deleted
        int deletedRows = db.delete(DataBinder.DataObjectives.TABLE_NAME, selection, selectionArgs);
    }

    public void deleteGlycemyInDB(String[] selectionArgs) {
        SQLiteDatabase db = getReadableDatabase();
        // Define 'where' part of query.
        String selection = DataBinder.DataGlycemies._ID + " LIKE ?";
        // Issue SQL statement => indicate the numbers of rows deleted
        int deletedRows = db.delete(DataBinder.DataGlycemies.TABLE_NAME, selection, selectionArgs);
    }

    public void deleteUserInDB(String[] selectionArgs) {
        SQLiteDatabase db = getReadableDatabase();
        // Define 'where' part of query.
        String selection = SQliteDatabase.Users.COLUMN_NAME_USERNAME + " LIKE ?";
        // Issue SQL statement => indicate the numbers of rows deleted
        int deletedRows = db.delete(SQliteDatabase.Users.TABLE_NAME, selection, selectionArgs);
    }

    public void updateInsulinUnitsEntry(String newValue, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBinder.DataInsulin.COLUMN_NAME_UNITS, newValue);
        db.update(DataBinder.DataInsulin.TABLE_NAME, values,
                DataBinder.DataInsulin._ID + " = ? ", new String[]{Integer.toString(id)});
    }

    public void updateGlycemyEntry(String newValue, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBinder.DataGlycemies.COLUMN_NAME_GLYCEMY, newValue);
        db.update(DataBinder.DataGlycemies.TABLE_NAME, values,
                DataBinder.DataGlycemies._ID + " = ? ", new String[]{Integer.toString(id)});
    }


    public void writeObjectiveInDB(String date, String duration, String type, String description, String userId) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DataBinder.DataObjectives.COLUMN_NAME_DATE, date);
        values.put(DataBinder.DataObjectives.COLUMN_NAME_DURATION, duration);
        values.put(DataBinder.DataObjectives.COLUMN_NAME_TYPE, type);
        values.put(DataBinder.DataObjectives.COLUMN_NAME_DESCRIPTION, description);
        values.put(DataBinder.DataObjectives.COLUMN_NAME_DATA_ID, userId);

        // Insert the new row, returning the primary key value of the new row
        db.insertOrThrow(DataBinder.DataObjectives.TABLE_NAME, null, values);
    }

    public List<ObjectiveBinder> getObjectives(String userId) {
        List<ObjectiveBinder> objectiveList = new ArrayList<>();
        SQLiteDatabase sQliteDatabase = getReadableDatabase();
        String[] field = {
                DataBinder.DataObjectives.COLUMN_NAME_DATE,
                DataBinder.DataObjectives.COLUMN_NAME_TYPE,
                DataBinder.DataObjectives.COLUMN_NAME_DURATION,
                DataBinder.DataObjectives.COLUMN_NAME_DESCRIPTION,
                DataBinder.DataObjectives.COLUMN_NAME_DATA_ID};
        Cursor c = sQliteDatabase.query(DataBinder.DataObjectives.TABLE_NAME, field,
                null, null, null, null, null);

        int date = c.getColumnIndex(DataBinder.DataObjectives.COLUMN_NAME_DATE);
        int type = c.getColumnIndex(DataBinder.DataObjectives.COLUMN_NAME_TYPE);
        int duration = c.getColumnIndex(DataBinder.DataObjectives.COLUMN_NAME_DURATION);
        int desc = c.getColumnIndex(DataBinder.DataObjectives.COLUMN_NAME_DESCRIPTION);
        int dataId = c.getColumnIndex(DataBinder.DataObjectives.COLUMN_NAME_DATA_ID);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String dateData = c.getString(date);
            String typeData = c.getString(type);
            String durationData = c.getString(duration);
            String descData = c.getString(desc);
            String idData = c.getString(dataId);

            if (idData.equals(userId)){
                objectiveList.add(new ObjectiveBinder(dateData, typeData, durationData, descData, idData));
            }
        }

        getReadableDatabase().close();
        c.close();

        return objectiveList;
    }

    public void writeAlertInDB(String name, String description, String type, String startMoment, String endMoment, String hour, String userId, String isActive) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DataBinder.DataAlerts.COLUMN_NAME_START_DATE, startMoment);
        values.put(DataBinder.DataAlerts.COLUMN_NAME_END_DATE, endMoment);
        values.put(DataBinder.DataAlerts.COLUMN_NAME_TYPE, type);
        values.put(DataBinder.DataAlerts.COLUMN_NAME_DESCRIPTION, description);
        values.put(DataBinder.DataAlerts.COLUMN_NAME_NAME, name);
        values.put(DataBinder.DataAlerts.COLUMN_NAME_TIME_HOUR, hour);
        values.put(DataBinder.DataAlerts.COLUMN_NAME_DATA_ID, userId);
        values.put(DataBinder.DataAlerts.COLUMN_NAME_IS_ACTIVE, isActive);

        // Insert the new row, returning the primary key value of the new row
        db.insertOrThrow(DataBinder.DataAlerts.TABLE_NAME, null, values);
    }

    public List<Alert> getAlerts(String userId) {
        List<Alert> alertList = new ArrayList<>();
        SQLiteDatabase sQliteDatabase = getReadableDatabase();
        String[] field = {
                DataBinder.DataAlerts.COLUMN_NAME_START_DATE,
                DataBinder.DataAlerts.COLUMN_NAME_END_DATE,
                DataBinder.DataAlerts.COLUMN_NAME_TYPE,
                DataBinder.DataAlerts.COLUMN_NAME_NAME,
                DataBinder.DataAlerts.COLUMN_NAME_DESCRIPTION,
                DataBinder.DataAlerts.COLUMN_NAME_TIME_HOUR,
                DataBinder.DataAlerts.COLUMN_NAME_DATA_ID,
                DataBinder.DataAlerts.COLUMN_NAME_IS_ACTIVE};
        Cursor c = sQliteDatabase.query(DataBinder.DataAlerts.TABLE_NAME, field,
                null, null, null, null, null);

        int sdate = c.getColumnIndex(DataBinder.DataAlerts.COLUMN_NAME_START_DATE);
        int edate = c.getColumnIndex(DataBinder.DataAlerts.COLUMN_NAME_END_DATE);
        int type = c.getColumnIndex(DataBinder.DataAlerts.COLUMN_NAME_TYPE);
        int name = c.getColumnIndex(DataBinder.DataAlerts.COLUMN_NAME_NAME);
        int description = c.getColumnIndex(DataBinder.DataAlerts.COLUMN_NAME_DESCRIPTION);
        int hour = c.getColumnIndex(DataBinder.DataAlerts.COLUMN_NAME_TIME_HOUR);
        int dataID = c.getColumnIndex(DataBinder.DataAlerts.COLUMN_NAME_DATA_ID);
        int active = c.getColumnIndex(DataBinder.DataAlerts.COLUMN_NAME_IS_ACTIVE);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String sdateData = c.getString(sdate);
            String edateData = c.getString(edate);
            String typeData = c.getString(type);
            String nameData = c.getString(name);
            String descData = c.getString(description);
            String hourData = c.getString(hour);
            String idData = c.getString(dataID);
            String activeData = c.getString(active);

            //TODO implement hour input
            if (idData.equals(userId)){
                alertList.add(new Alert(nameData, descData, typeData, sdateData, edateData, hourData, idData, activeData));
            }
        }

        getReadableDatabase().close();
        c.close();

        return alertList;
    }

    public void updateAlertStatus(String activityStatus, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBinder.DataAlerts.COLUMN_NAME_IS_ACTIVE, activityStatus);
        db.update(DataBinder.DataAlerts.TABLE_NAME, values,
                DataBinder.DataAlerts._ID + " = ? ", new String[]{Integer.toString(id)});
    }

}
