package com.studio.neopanda.diabetesmeganotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manageAuthInDB();
    }

    public void manageAuthInDB(){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SQliteDatabase.Credentials.COLUMN_NAME_USERNAME, "Bick");
        values.put(SQliteDatabase.Credentials.COLUMN_NAME_PASSWORD, "pass");

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(SQliteDatabase.Credentials.TABLE_NAME, null, values);
    }
}
