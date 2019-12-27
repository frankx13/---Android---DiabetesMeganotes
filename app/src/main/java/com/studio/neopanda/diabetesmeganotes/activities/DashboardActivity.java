package com.studio.neopanda.diabetesmeganotes.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.studio.neopanda.diabetesmeganotes.R;
import com.studio.neopanda.diabetesmeganotes.database.DatabaseHelper;
import com.studio.neopanda.diabetesmeganotes.utils.AverageGlycemyUtils;
import com.studio.neopanda.diabetesmeganotes.utils.DateUtils;
import com.studio.neopanda.diabetesmeganotes.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity {

    //UI
    @BindView(R.id.my_glycemy_diary_btn)
    public Button toGlycemiesBtn;
    @BindView(R.id.title_app_TV)
    public TextView titleDashboard;
    @BindView(R.id.fast_stats_60d_TV)
    public TextView statsSixtyDays;
    @BindView(R.id.fast_stats_30d_TV)
    public TextView statsThirtyDays;
    @BindView(R.id.fast_stats_15d_TV)
    public TextView statsFifteenDays;
    @BindView(R.id.fast_stats_7d_TV)
    public TextView statsSevenDays;
    @BindView(R.id.fast_note_TV)
    public TextView fastNoteTV;
    @BindView(R.id.fast_note_EV)
    public EditText fastNoteEV;
    @BindView(R.id.fast_note_btn)
    public Button fastNoteBtn;
    @BindView(R.id.my_objectives_btn)
    public Button objectivesBtn;
    @BindView(R.id.my_infos_btn)
    public Button infosBtn;
    @BindView(R.id.my_alerts_btn)
    public Button alertsBtn;

    //DATA
    private DatabaseHelper dbHelper = new DatabaseHelper(this);
    private List<String> listDates;
    private List<String> listGlycemies;
    private List<Double> listGlycemiesDouble;
    private String targetDate;
    private String todayDate;
    private int glycemyColor;
    private int statsTurns = 0;
    private String note = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ButterKnife.bind(this);
        welcomeUser();

        Utils.logoutUser(titleDashboard, this, DashboardActivity.this);

        listDates = new ArrayList<>();
        listGlycemies = new ArrayList<>();
        listGlycemiesDouble = new ArrayList<>();
        toGlycemiesBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MyGlycemiesActivity.class);
            startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(DashboardActivity.this).toBundle());
            startActivity(intent);
            overridePendingTransition(R.anim.go_up_anim, R.anim.go_down_anim);
        });

        objectivesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ObjectivesActivity.class);
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(DashboardActivity.this).toBundle());
                startActivity(intent);
                overridePendingTransition(R.anim.go_up_anim, R.anim.go_down_anim);
            }
        });

        alertsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyAlertsActivity.class);
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(DashboardActivity.this).toBundle());
                startActivity(intent);
                overridePendingTransition(R.anim.go_up_anim, R.anim.go_down_anim);
            }
        });

        infosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO V2.0 Release this feature in the next update
//                Intent intent = new Intent(getApplicationContext(), InformationsActivity.class);
//                startActivity(intent,
//                        ActivityOptions.makeSceneTransitionAnimation(DashboardActivity.this).toBundle());
//                startActivity(intent);
//                overridePendingTransition(R.anim.go_up_anim, R.anim.go_down_anim);
                Toast.makeText(DashboardActivity.this, "Available in the next update !", Toast.LENGTH_SHORT).show();
            }
        });

        //TODO fix the method with GlycemyBinder table
//        loadAverageStats(0);
        loadNotes();
    }

    private void welcomeUser() {
        String username = dbHelper.getActiveUserInDB().get(0).getUsername();
        Toast.makeText(this, "Salut " + username + " !", Toast.LENGTH_SHORT).show();
    }

    private void loadNotes() {
        String noteInDB = dbHelper.getNote();
        if (!noteInDB.equals("")) {
            fastNoteTV.setText(noteInDB);
        } else {
            fastNoteTV.setText(getResources().getString(R.string.empty_note_text));
        }
        fastNoteTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fastNoteTV.setVisibility(View.GONE);
                fastNoteEV.setVisibility(View.VISIBLE);
                fastNoteBtn.setVisibility(View.VISIBLE);
                fastNoteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        note = fastNoteEV.getEditableText().toString() + " \n\n (Cliquer pour modifier)";
                        dbHelper.setNoteInDB(note);
                        Toast.makeText(DashboardActivity.this,
                                "Note enregistrée avec succès !",
                                Toast.LENGTH_SHORT).show();

                        fastNoteTV.setText(note);
                        fastNoteEV.setVisibility(View.GONE);
                        fastNoteBtn.setVisibility(View.GONE);
                        fastNoteTV.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }

    //TODO Fix method with databinder
//    private void loadAverageStats(int statTurn) {
//        todayDate = DateUtils.calculateDateOfToday();
//
//        switch (statsTurns) {
//            case 0:
//                targetDate = DateUtils.calculateDateFromToday(7);
//                break;
//            case 1:
//                targetDate = DateUtils.calculateDateFromToday(15);
//                break;
//            case 2:
//                targetDate = DateUtils.calculateDateFromToday(30);
//                break;
//            case 3:
//                targetDate = DateUtils.calculateDateFromToday(60);
//                break;
//        }

        //TODO replace the Helper code with the Glycemies from DataBinder
//        listDates = dbHelper.getGlycemiesInTimePeriod(todayDate, targetDate);
//
//        if (listDates != null && !listDates.isEmpty()) {
//            listGlycemies = dbHelper.getAverageGlycemies(todayDate, targetDate);
//            for (String s : listGlycemies) {
//                listGlycemiesDouble.add(Double.valueOf(s));
//            }
//
//            double averageGlycemyLevel = AverageGlycemyUtils.calculateAverageGlycemyAllResults(listGlycemiesDouble);
//            if (averageGlycemyLevel < 0.80 || averageGlycemyLevel > 2.50) {
//                glycemyColor = 3;
//            } else if (averageGlycemyLevel > 1.80) {
//                glycemyColor = 2;
//            } else if (averageGlycemyLevel > 1.40) {
//                glycemyColor = 1;
//            } else {
//                glycemyColor = 0;
//            }
//            paintAverageChars(averageGlycemyLevel, glycemyColor);
//        } else {
//            statsSixtyDays.setText(getResources().getString(R.string.not_enough_entries));
//            statsThirtyDays.setText("");
//            statsFifteenDays.setText("");
//            statsSevenDays.setText("");
//        }
//    }

    //TODO fix loadAverageStats method
    private void paintAverageChars(double averageGlycemyLevel, int color) {
        String preText = "";

        switch (statsTurns) {
            case 0:
                preText = "7J : ";
                break;
            case 1:
                preText = "14J : ";
                break;
            case 2:
                preText = "30J : ";
                break;
            case 3:
                preText = "60J : ";
                break;
        }

        final SpannableStringBuilder sb = new SpannableStringBuilder(preText + averageGlycemyLevel + " g/l");
        int red;
        int green;
        int blue;

        if (color == 0) {
            red = 16;
            green = 122;
            blue = 11;
        } else if (color == 1) {
            red = 233;
            green = 231;
            blue = 89;
        } else if (color == 2) {
            red = 233;
            green = 166;
            blue = 89;
        } else {
            red = 166;
            green = 0;
            blue = 0;
        }

        final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(red, green, blue));

        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        if (statsTurns == 0) {
            sb.setSpan(fcs, 5, 9, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            sb.setSpan(bss, 5, 9, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        } else {
            sb.setSpan(fcs, 6, 10, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            sb.setSpan(bss, 6, 10, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }

        switch (statsTurns) {
            case 0:
                statsSevenDays.setText(sb);
                break;
            case 1:
                statsFifteenDays.setText(sb);
                break;
            case 2:
                statsThirtyDays.setText(sb);
                break;
            case 3:
                statsSixtyDays.setText(sb);
                break;
        }

        if (statsTurns <= 2) {
            statsTurns += 1;
            //TODO fix the method with DataBinder
//            loadAverageStats(statsTurns);
        }
    }

    //Close database connection ondestroy
    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
