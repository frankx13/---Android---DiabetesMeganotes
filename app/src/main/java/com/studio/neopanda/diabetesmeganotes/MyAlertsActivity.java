package com.studio.neopanda.diabetesmeganotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAlertsActivity extends AppCompatActivity {

    @BindView(R.id.motion_layout)
    MotionLayout motionLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts_motion);

        ButterKnife.bind(this);
        animate();
    }

    private void animate(){


    }
}
