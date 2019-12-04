package com.studio.neopanda.diabetesmeganotes;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyGlycemiesActivity extends AppCompatActivity {

    //UI
    @BindView(R.id.seven_days_average_glycemy_TV)
    TextView weekGlycemyAverage;
    @BindView(R.id.seven_days_average_insulin_TV)
    TextView weekInsulinAverage;

    //DATA
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    List itemIds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_glycemies);

        ButterKnife.bind(this);
        itemIds = new ArrayList<>();
        String sevenDaysAgoDate = calculateDateFromToday(7);
    }

    public String calculateDateFromToday(int daysFromToday){

        long millis=System.currentTimeMillis();
        java.sql.Date todayDate=new java.sql.Date(millis);
        java.sql.Time nowTime = new java.sql.Time(millis);

        String[] todayDateSplit = todayDate.toString().trim().split("-", 3);

        int year = Integer.valueOf(todayDateSplit[0]);
        int month = Integer.valueOf(todayDateSplit[1]);
        int day = Integer.valueOf(todayDateSplit[2]);

        int yearsToReach;
        int monthsToReach;
        int daysToReach;

        int daysCounter = daysFromToday;
        int monthsCounter = 0;
        int yearsCounter = 0;

        if (daysFromToday >= 365){
            yearsCounter = daysFromToday / 365;
            daysCounter = daysCounter - (365 * yearsCounter);
            daysFromToday = daysFromToday - (365 * yearsCounter);
        }
        if (daysFromToday > 30){
            monthsCounter = daysCounter / 30;
            daysCounter = daysCounter - (30 * monthsCounter);
        }

        if (yearsCounter > 0){
            yearsToReach = Integer.valueOf(year) - yearsCounter;
        } else {
            yearsToReach = year;
        }
        if (monthsCounter > 0){
            monthsToReach = Integer.valueOf(month) - monthsCounter;
            if (monthsToReach < 0){
                monthsToReach = monthsToReach + 12;
            }
        } else {
            monthsToReach = month;
        }
        if (daysCounter > 0){
            daysToReach = day - daysCounter;
            if (daysToReach < 0){
                daysToReach = daysToReach + 30;
            }
        } else {
            daysToReach = day;
        }

        String result;

        if (monthsToReach < 10){
            if (daysToReach < 10){
                result = yearsToReach + "-" + 0 + monthsToReach + "-" + 0 + daysToReach;
            } else {
                result = yearsToReach + "-" + 0 + monthsToReach + "-" + daysToReach;
            }
        } else if (daysToReach < 10){
            result = yearsToReach + "-" + monthsToReach + "-" + 0 + daysToReach;
        } else {
            result = yearsToReach + "-" + monthsToReach + "-" + daysToReach;
        }

        return result;
    }

    public void readAuthInDB(String date) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                SQliteDatabase.InsulinUnits.COLUMN_NAME_DATE,
                SQliteDatabase.InsulinUnits.COLUMN_NAME_UNITS,
                SQliteDatabase.InsulinUnits.COLUMN_NAME_EXTRA_INFOS
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = SQliteDatabase.InsulinUnits.COLUMN_NAME_DATE;
        String[] selectionArgs = {date};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                SQliteDatabase.InsulinUnits.COLUMN_NAME_DATE + " DESC";

        Cursor cursor = db.query(
                SQliteDatabase.InsulinUnits.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        //This part is adding all valid results to the itemIds List
//        List itemIds = new ArrayList<>();
        while (cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(SQliteDatabase.Credentials._ID));
            itemIds.add(itemId);
        }
        cursor.close();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
