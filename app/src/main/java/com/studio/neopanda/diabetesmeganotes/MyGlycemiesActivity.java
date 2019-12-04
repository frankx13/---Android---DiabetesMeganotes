package com.studio.neopanda.diabetesmeganotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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
    @BindView(R.id.title_app_TV_glycemies)
    TextView titleGlycemyScreen;
    @BindView(R.id.add_entry_glycemy)
    Button addGlycemyBtn;
    @BindView(R.id.new_entry_container)
    LinearLayout containerAddEntryPart;
    @BindView(R.id.datepicker_new_entry_glycemy)
    CalendarView calendarView;
    @BindView(R.id.glycemy_level_input_ET)
    EditText glycemyInputLevel;
    @BindView(R.id.level_glycemy_new_entry_TV)
    TextView glycemyInputTV;
    @BindView(R.id.date_glycemy_new_entry_TV)
    TextView dateGlycemyInputTV;
    @BindView(R.id.validate_new_entry_btn)
    Button validateNewEntryBtn;

    //DATA
    private DatabaseHelper dbHelper = new DatabaseHelper(this);
    private List itemIds;
    private String newEntryGlycemyDate;
    private String newEntryGlycemyLevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_glycemies);

        ButterKnife.bind(this);
        itemIds = new ArrayList<>();

        String sevenDaysAgoDate = DateUtils.calculateDateFromToday(7);

        addGlycemyBtn.setOnClickListener(v -> containerAddEntryPart.setVisibility(View.VISIBLE));

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            newEntryGlycemyDate = year + "-" + month + "-" + dayOfMonth;
            calendarView.setVisibility(View.GONE);
            dateGlycemyInputTV.setVisibility(View.GONE);
            glycemyInputTV.setVisibility(View.VISIBLE);
            glycemyInputLevel.setVisibility(View.VISIBLE);
            validateNewEntryBtn.setVisibility(View.VISIBLE);
        });

        validateNewEntryBtn.setOnClickListener(v -> {
            newEntryGlycemyLevel = glycemyInputLevel.getEditableText().toString();
            writeAuthInDB(newEntryGlycemyDate, newEntryGlycemyLevel);
            Toast.makeText(this, "Your entry has successfully been added !", Toast.LENGTH_SHORT).show();
            containerAddEntryPart.setVisibility(View.GONE);
        });
    }

    public long writeAuthInDB(String date, String glycemy) {
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SQliteDatabase.Glycemies.COLUMN_NAME_DATE, date);
        values.put(SQliteDatabase.Glycemies.COLUMN_NAME_GLYCEMY, glycemy);

        Log.e("CREATECOLUMN2", " " + values.getAsString(SQliteDatabase.Glycemies.COLUMN_NAME_GLYCEMY));

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(SQliteDatabase.Glycemies.TABLE_NAME, null, values);
        db.insert(SQliteDatabase.Glycemies.TABLE_NAME, null, values);

        return newRowId;
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
