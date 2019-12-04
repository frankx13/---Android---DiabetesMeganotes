package com.studio.neopanda.diabetesmeganotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity {

    @BindView(R.id.my_glycemy_diary_btn)
    Button toGlycemiesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ButterKnife.bind(this);
        toGlycemiesBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MyGlycemiesActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
