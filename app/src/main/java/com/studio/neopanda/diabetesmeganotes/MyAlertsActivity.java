package com.studio.neopanda.diabetesmeganotes;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAlertsActivity extends AppCompatActivity {


    //UI
    @BindView(R.id.motion_layout)
    MotionLayout motionLayout;
    @BindView(R.id.add_alert_next_btn)
    Button addAlertNextBtn;
    @BindView(R.id.add_alert_previous_btn)
    Button addAlertPreviousBtn;
    @BindView(R.id.alert_type_indicator_tv)
    TextView typeAlertSelected;
    @BindView(R.id.pizza_selection_alerts)
    ImageView foodSelectionIV;
    @BindView(R.id.sport_selection_alerts)
    ImageView sportSelectionIV;
    @BindView(R.id.insulin_selection_alerts)
    ImageView insulinSelectionIV;
    @BindView(R.id.glycemy_selection_alerts)
    ImageView glycemySelectionIV;
    @BindView(R.id.iv_selection_cube_alerts)
    ImageView cubeSelection;
    @BindView(R.id.tv_alert_selection)
    TextView textHelper;

    //DATA
    String typeAlert = "";
    boolean foodSelected = false;
    boolean sportSelected = false;
    boolean insulinSelected = false;
    boolean glycemySelected = false;
    boolean isConfirmed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts_motion);

        ButterKnife.bind(this);

        nextBtnMecanic();
        previousBtnMecanic();
    }

    private void previousBtnMecanic() {
        addAlertPreviousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeAlert = "";
                isConfirmed = false;
                addAlertNextBtn.setText(getResources().getString(R.string.confirm));
                cubeSelection.setBackground(getResources().getDrawable(R.drawable.btn_simple));
                addAlertPreviousBtn.setVisibility(View.GONE);
                showTypeViews();
            }
        });
    }

    private void nextBtnMecanic() {
        addAlertNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOverlappingViews();
                setDescText();
                checkTypeSelection();

                if (typeAlert.equals("")) {
                    Toast.makeText(MyAlertsActivity.this, "Vous devez choisir un type d'alerte!", Toast.LENGTH_SHORT).show();
                } else {
                    if (isConfirmed) {
                        addAlertPreviousBtn.setVisibility(View.VISIBLE);
                    } else {
                        isConfirmed = true;
                        addAlertNextBtn.setText(getResources().getString(R.string.next));
                        cubeSelection.setBackground(getResources().getDrawable(R.drawable.btn_simple_green));
                        textHelper.setText(getResources().getString(R.string.choose_alert_text));
                        hideTypeViews();
                    }
                }
            }
        });
    }

    private void hideTypeViews(){
        typeAlertSelected.setVisibility(View.GONE);
        cubeSelection.setVisibility(View.GONE);
        foodSelectionIV.setVisibility(View.GONE);
        sportSelectionIV.setVisibility(View.GONE);
        insulinSelectionIV.setVisibility(View.GONE);
        glycemySelectionIV.setVisibility(View.GONE);
    }

    private void showTypeViews(){
        typeAlertSelected.setVisibility(View.VISIBLE);
        cubeSelection.setVisibility(View.VISIBLE);
        foodSelectionIV.setVisibility(View.VISIBLE);
        sportSelectionIV.setVisibility(View.VISIBLE);
        insulinSelectionIV.setVisibility(View.VISIBLE);
        glycemySelectionIV.setVisibility(View.VISIBLE);
    }

    private void checkOverlappingViews() {
        foodSelected = isViewOverlapping(foodSelectionIV, cubeSelection);
        sportSelected = isViewOverlapping(sportSelectionIV, cubeSelection);
        insulinSelected = isViewOverlapping(insulinSelectionIV, cubeSelection);
        glycemySelected = isViewOverlapping(glycemySelectionIV, cubeSelection);
    }

    private void setDescText() {
        if (foodSelected) {
            typeAlert = "Alimentation";
        } else if (sportSelected) {
            typeAlert = "Sport";
        } else if (insulinSelected) {
            typeAlert = "Insuline";
        } else if (glycemySelected) {
            typeAlert = "Glycémie";
        } else {
            typeAlert = "";
        }
    }

    private void checkTypeSelection() {
        if (!typeAlert.equals("")) {
            typeAlertSelected.setText(typeAlert);
        }
    }

    private boolean isViewOverlapping(View firstView, View secondView) {
        int[] firstPosition = new int[2];
        int[] secondPosition = new int[2];

        firstView.getLocationOnScreen(firstPosition);
        secondView.getLocationOnScreen(secondPosition);

        // Rect constructor parameters: left, top, right, bottom
        Rect rectFirstView = new Rect(firstPosition[0], firstPosition[1],
                firstPosition[0] + firstView.getMeasuredWidth(), firstPosition[1] + firstView.getMeasuredHeight());
        Rect rectSecondView = new Rect(secondPosition[0], secondPosition[1],
                secondPosition[0] + secondView.getMeasuredWidth(), secondPosition[1] + secondView.getMeasuredHeight());
        return rectFirstView.intersect(rectSecondView);
    }
}
