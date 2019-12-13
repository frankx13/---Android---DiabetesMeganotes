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
            dbHelper.setCredentialsInDB(username, pwd);
            Toast.makeText(this, "Le compte a bien été créé", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Connexion en cours", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }
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

    //Close database connection ondestroy
    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
