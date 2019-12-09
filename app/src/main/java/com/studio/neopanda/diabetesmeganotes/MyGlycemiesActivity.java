package com.studio.neopanda.diabetesmeganotes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyGlycemiesActivity extends AppCompatActivity {

    static boolean isDiaryOpen = false;
    //UI
    @BindView(R.id.seven_days_average_glycemy_TV)
    TextView weekGlycemyAverage;
    @BindView(R.id.seven_days_average_insulin_TV)
    TextView weekInsulinAverage;
    @BindView(R.id.title_app_TV_glycemies)
    TextView titleGlycemyScreen;
    @BindView(R.id.add_entry_glycemy)
    Button addGlycemyBtn;
    @BindView(R.id.add_entry_insulin)
    Button addInsulinBtn;
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
    @BindView(R.id.view_entries_glycemy)
    Button viewAllEntriesBtn;
    @BindView(R.id.container_journal)
    FrameLayout journalContainer;
    @BindView(R.id.exit_diary_journal_btn)
    ImageButton exitDiaryJournalBtn;
    @BindView(R.id.input_insulin_units_new_entry)
    EditText insulinUnitsInput;
    @BindView(R.id.validate_new_insulin_btn)
    Button validateInsulinInput;
    @BindView(R.id.container_new_units_insulin)
    LinearLayout containerInsulinUnitsInput;
    @BindView(R.id.view_entries_insulin_units)
    Button viewEntriesInsulinBtn;

    //DATA
    private DatabaseHelper dbHelper = new DatabaseHelper(this);
    private List itemIds;
    private String newEntryGlycemyDate;
    private String newEntryGlycemyLevel;
    private String newInsulinUnits;
    private int fragmentID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_glycemies);

        ButterKnife.bind(this);
        itemIds = new ArrayList<>();

        addGlycemyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddEntryBtn();
            }
        });
        viewAllEntriesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickViewEntriesBtn();
            }
        });
        addInsulinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddInsulinBtn();
            }
        });
        viewEntriesInsulinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickViewInsulinBtn();
            }
        });
        exitDiaryJournalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                if (fragmentID == 1) {
                    fragment = new EntriesDiaryFragment();
                } else if (fragmentID == 2) {
                    fragment = new EntriesInsulinFragment();
                }

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(fragment).commit();
                journalContainer.setVisibility(View.GONE);
                exitDiaryJournalBtn.setVisibility(View.GONE);
            }
        });
    }

    private void onClickViewInsulinBtn() {
        boolean tableNotEmpty = isAnyGlycemyInDB("Insulin");
        if (tableNotEmpty){
            fragmentID = 2;
            EntriesInsulinFragment fragment = new EntriesInsulinFragment();
            journalContainer.setVisibility(View.VISIBLE);
            exitDiaryJournalBtn.setVisibility(View.VISIBLE);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container_journal, fragment).commit();
        } else {
            Toast.makeText(this, "Aucune entrée, essayez d'ajouter des unités d'insuline.", Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickAddInsulinBtn() {
        containerInsulinUnitsInput.setVisibility(View.VISIBLE);

        containerInsulinUnitsInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        validateInsulinInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newInsulinUnits = insulinUnitsInput.getEditableText().toString();

                if (newInsulinUnits.length() > 2) {
                    Toast.makeText(MyGlycemiesActivity.this, "Trop de chiffres, veuillez réessayer.", Toast.LENGTH_SHORT).show();
                } else if (newInsulinUnits.length() == 1 || newInsulinUnits.length() == 2) {
                    writeInsulinUnitsInDB(newInsulinUnits);
                    containerInsulinUnitsInput.setVisibility(View.GONE);
                    Toast.makeText(MyGlycemiesActivity.this, "Unités renseignées avec succès !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MyGlycemiesActivity.this, "Le nombre d'unités ne peut pas être nul, veuillez réessayer.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void writeInsulinUnitsInDB(String units) {
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String todayDate = DateUtils.calculateDateOfToday();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SQliteDatabase.InsulinUnits.COLUMN_NAME_DATE, todayDate);
        values.put(SQliteDatabase.InsulinUnits.COLUMN_NAME_UNITS, units);

        // Insert the new row, returning the primary key value of the new row
        db.insertOrThrow(SQliteDatabase.InsulinUnits.TABLE_NAME, null, values);
    }

    public void onClickViewEntriesBtn() {
        boolean tableNotEmpty = isAnyGlycemyInDB("Glycemies");
        if (tableNotEmpty){
            fragmentID = 1;
            EntriesDiaryFragment fragment = new EntriesDiaryFragment();
            journalContainer.setVisibility(View.VISIBLE);
            exitDiaryJournalBtn.setVisibility(View.VISIBLE);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container_journal, fragment).commit();
        } else {
            Toast.makeText(this, "Aucune entrée, essayez d'ajouter des glycémies.", Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickAddEntryBtn() {
        glycemyInputTV.setVisibility(View.GONE);
        glycemyInputLevel.setVisibility(View.GONE);
        validateNewEntryBtn.setVisibility(View.GONE);
        containerAddEntryPart.setVisibility(View.VISIBLE);
        calendarView.setVisibility(View.VISIBLE);
        dateGlycemyInputTV.setVisibility(View.VISIBLE);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            if (month < 10) {
                newEntryGlycemyDate = year + "-" + 0 + (month + 1) + "-" + dayOfMonth;
                if (dayOfMonth < 10) {
                    newEntryGlycemyDate = year + "-" + 0 + (month + 1) + "-" + 0 + dayOfMonth;
                }
            } else if (dayOfMonth < 10) {
                newEntryGlycemyDate = year + "-" + (month + 1) + "-" + 0 + dayOfMonth;
            } else {
                newEntryGlycemyDate = year + "-" + (month + 1) + "-" + dayOfMonth;
            }
            calendarView.setVisibility(View.GONE);
            dateGlycemyInputTV.setVisibility(View.GONE);
            glycemyInputTV.setVisibility(View.VISIBLE);
            glycemyInputLevel.setVisibility(View.VISIBLE);
            validateNewEntryBtn.setVisibility(View.VISIBLE);
        });

        validateNewEntryBtn.setOnClickListener(v -> {
            newEntryGlycemyLevel = glycemyInputLevel.getEditableText().toString();
            if (newEntryGlycemyLevel.length() < 4) {
                Toast.makeText(this, "Trop peu de nombres rentrés. Veuillez utiliser ce modèle et recommencer : x.xx", Toast.LENGTH_LONG).show();
            } else if (newEntryGlycemyLevel.length() == 4) {
                writeAuthInDB(newEntryGlycemyDate, newEntryGlycemyLevel);
                Toast.makeText(this, "L'entrée a bien été enregistrée !", Toast.LENGTH_SHORT).show();
                containerAddEntryPart.setVisibility(View.GONE);
            } else {
                Toast.makeText(this, "Impossible d'ajouter cette entrée. Veuillez utiliser ce modèle et recommencer : x.xx", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void writeAuthInDB(String date, String glycemy) {
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SQliteDatabase.Glycemies.COLUMN_NAME_DATE, date);
        values.put(SQliteDatabase.Glycemies.COLUMN_NAME_GLYCEMY, glycemy);

        // Insert the new row, returning the primary key value of the new row
        db.insertOrThrow(SQliteDatabase.Glycemies.TABLE_NAME, null, values);
    }

    public boolean isAnyGlycemyInDB(String tableName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String count = "SELECT count(*) FROM " + tableName;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        mcursor.close();
        if (icount > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
