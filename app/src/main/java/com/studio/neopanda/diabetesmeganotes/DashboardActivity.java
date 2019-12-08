package com.studio.neopanda.diabetesmeganotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity {

    //UI
    @BindView(R.id.my_glycemy_diary_btn)
    Button toGlycemiesBtn;
    @BindView(R.id.fast_stats_60d_TV)
    TextView statsSixtyDays;
    @BindView(R.id.fast_stats_30d_TV)
    TextView statsThirtyDays;
    @BindView(R.id.fast_stats_15d_TV)
    TextView statsFifteenDays;
    @BindView(R.id.fast_stats_7d_TV)
    TextView statsSevenDays;

    //DATA
    private DatabaseHelper dbHelper = new DatabaseHelper(this);
    private String[] queriesResult;
    private List<String> listResults;
    private String targetDate;
    private String todayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ButterKnife.bind(this);
        listResults = new ArrayList<>();
        toGlycemiesBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MyGlycemiesActivity.class);
            startActivity(intent);
            finish();
        });

        loadAverageStats();
    }

    private void loadAverageStats() {
        todayDate = DateUtils.calculateDateOfToday();
        targetDate = DateUtils.calculateDateFromToday(7);

        listResults = dbHelper.getWeekCount(todayDate, targetDate);
        Log.e("gzrgrz", "loadAverageStats: " + (listResults));
    }
}
