package com.studio.neopanda.diabetesmeganotes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    //UI
    @BindView(R.id.creds_username_ET)
    EditText authUsernameET;
    @BindView(R.id.creds_pwd_ET)
    EditText authPwdET;
    @BindView(R.id.validation_auth_btn)
    Button validateAuthBtn;

    //DATA
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    List itemIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        itemIds = new ArrayList<>();

        validateAuthBtn.setOnClickListener(v -> onClickValidateAuthBtn());
    }

    public void onClickValidateAuthBtn() {
        String username = authUsernameET.getEditableText().toString();
        String pwd = authPwdET.getEditableText().toString();

        readAuthInDB(username);
        if (itemIds.size() < 1) {
            writeAuthInDB(username, pwd);
            Toast.makeText(this, "Le compte a bien été créé", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Connexion en cours", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public long writeAuthInDB(String username, String password) {
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SQliteDatabase.Credentials.COLUMN_NAME_USERNAME, username);
        values.put(SQliteDatabase.Credentials.COLUMN_NAME_PASSWORD, password);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(SQliteDatabase.Credentials.TABLE_NAME, null, values);
        db.insert(SQliteDatabase.Credentials.TABLE_NAME, null, values);

        return newRowId;
    }

    public void readAuthInDB(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                SQliteDatabase.Credentials.COLUMN_NAME_USERNAME,
                SQliteDatabase.Credentials.COLUMN_NAME_PASSWORD
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = SQliteDatabase.Credentials.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = {username};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                SQliteDatabase.Credentials.COLUMN_NAME_USERNAME + " DESC";

        Cursor cursor = db.query(
                SQliteDatabase.Credentials.TABLE_NAME,   // The table to query
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

    public void deleteAuthInDB() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Define 'where' part of query.
        String selection = SQliteDatabase.Credentials.COLUMN_NAME_USERNAME + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {"Josef"};
        // Issue SQL statement => indicate the numbers of rows deleted
        int deletedRows = db.delete(SQliteDatabase.Credentials.TABLE_NAME, selection, selectionArgs);
    }

    public void updateAuthInDB() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

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

    //Close database connection ondestroy
    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
