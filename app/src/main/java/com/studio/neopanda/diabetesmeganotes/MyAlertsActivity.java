package com.studio.neopanda.diabetesmeganotes;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAlertsActivity extends AppCompatActivity {

    //UI
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
    @BindView(R.id.name_alert_input)
    EditText alertNameInput;
    @BindView(R.id.desc_alert_input)
    EditText alertDescInput;
    @BindView(R.id.sdate_alert_input)
    DatePicker dateInput;
    @BindView(R.id.container_input_name_alert)
    LinearLayout containerAddAlert;
    @BindView(R.id.container_add_alert_nav_choice)
    LinearLayout containerAddAlertNavigation;
    @BindView(R.id.add_new_alert_btn)
    Button addNewAlertBtn;
    @BindView(R.id.see_all_alerts_btn)
    Button seeAllAlertsBtn;
    @BindView(R.id.alerts_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.exit_alert_entries_btn)
    ImageButton exitJournalBtn;
    @BindView(R.id.rv_container)
    LinearLayout recyclerContainer;

    //DATA
    private String typeAlert = "";
    private String nameAlert = "";
    private String descAlert = "";
    private String sdateAlert = "";
    private String edateAlert = "";
    private List<Alert> alerts;
    private int idEntry = 0;
    private DatabaseHelper dbHelper = new DatabaseHelper(this);
    private int btnCounterSteps = 0;
    private boolean foodSelected = false;
    private boolean sportSelected = false;
    private boolean insulinSelected = false;
    private boolean glycemySelected = false;
    private boolean isNameConfirmed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts_motion);

        ButterKnife.bind(this);
        alerts = new ArrayList<>();

        nextBtnMecanic();
        previousBtnMecanic();
    }

    private void showMotionViews() {
        foodSelectionIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_local_pizza_black_24dp));
        sportSelectionIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_fitness_center_black_24dp));
        insulinSelectionIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_needle_24dp));
        glycemySelectionIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_glycemy_blood_24dp));
    }

    private void hideMainBtns() {
        addNewAlertBtn.setVisibility(View.GONE);
        seeAllAlertsBtn.setVisibility(View.GONE);
    }

    private void showMainBtns() {
        addNewAlertBtn.setVisibility(View.VISIBLE);
        seeAllAlertsBtn.setVisibility(View.VISIBLE);
    }

    private void loadExitBtn() {
        exitJournalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.GONE);
                recyclerView.setElevation(0.0f);
                exitJournalBtn.setVisibility(View.GONE);
                alerts.clear();
                idEntry = 0;
            }
        });
    }

    private void sortingList() {
        Collections.sort(alerts, (obj1, obj2) -> obj2.name.compareToIgnoreCase(obj1.name));
    }

    private void addingIds() {
        for (Alert alert : alerts) {
            alert.setIdEntry(idEntry += 1);
        }
    }

    private void loadRV() {
        recyclerView.setVisibility(View.VISIBLE);
        exitJournalBtn.setVisibility(View.VISIBLE);
        AlertsAdapter adapter = new AlertsAdapter(alerts, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void previousBtnMecanic() {
        addAlertPreviousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnCounterSteps == 1) {
                    typeAlert = "";
                    isNameConfirmed = false;
                    textHelper.setText(getResources().getString(R.string.choose_alert_type_tv));
                    addAlertNextBtn.setText(getResources().getString(R.string.confirm));
                    cubeSelection.setBackground(getResources().getDrawable(R.drawable.btn_simple));
                    addAlertPreviousBtn.setVisibility(View.GONE);
                    showTypeViews();
                    isNameConfirmed = false;
                    btnCounterSteps = 0;
                }
                if (btnCounterSteps == 2) {
                    alertDescInput.setVisibility(View.GONE);
                    textHelper.setText(getResources().getString(R.string.choose_alert_text));
                    alertNameInput.setVisibility(View.VISIBLE);
                    btnCounterSteps = 1;
                }
            }
        });
    }

    private void nextBtnMecanic() {
        addAlertNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnCounterSteps == 0) {
                    checkOverlappingViews();
                    setDescText();
                    checkTypeSelection();

                    if (typeAlert.equals("")) {
                        Toast.makeText(MyAlertsActivity.this, "Vous devez choisir un type d'alerte!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (isNameConfirmed && btnCounterSteps == 0) {
                            textHelper.setText(getResources().getString(R.string.choose_alert_text));
                            addAlertPreviousBtn.setVisibility(View.VISIBLE);
                            hideTypeViews();
                            btnCounterSteps = 1;
                            isNameConfirmed = false;
                        } else {
                            isNameConfirmed = true;
                            addAlertNextBtn.setText(getResources().getString(R.string.next));
                        }
                    }
                } else if (btnCounterSteps == 1) {
                    getInputName();

                    if (nameAlert.equals("")) {
                        Toast.makeText(MyAlertsActivity.this, "Vous devez entrer un nom!", Toast.LENGTH_SHORT).show();
                    } else if (nameAlert.length() > 15) {
                        Toast.makeText(MyAlertsActivity.this, "Nom trop long, le maximum est de 15 caractères.", Toast.LENGTH_SHORT).show();
                    } else {
                        alertNameInput.setVisibility(View.GONE);
                        textHelper.setText(getResources().getString(R.string.choose_alert_desc_tv));
                        alertDescInput.setVisibility(View.VISIBLE);
                        btnCounterSteps = 2;
                    }
                } else if (btnCounterSteps == 2) {
                    getInputDesc();

                    if (descAlert.equals("")) {
                        Toast.makeText(MyAlertsActivity.this, "Vous devez entrer une description!", Toast.LENGTH_SHORT).show();
                    } else if (nameAlert.length() > 75) {
                        Toast.makeText(MyAlertsActivity.this, "Description trop longue, le maximum est de 75 caractères.", Toast.LENGTH_SHORT).show();
                    } else {
                        alertDescInput.setVisibility(View.GONE);
                        textHelper.setText(getResources().getString(R.string.choose_alert_sdate_tv));
                        dateInput.setVisibility(View.VISIBLE);
                        btnCounterSteps = 3;
                    }
                } else if (btnCounterSteps == 3) {
                    getInputSdate();

                    if (sdateAlert.equals("")) {
                        Toast.makeText(MyAlertsActivity.this, "Vous devez choisir la date de début!", Toast.LENGTH_SHORT).show();
                    } else {
                        textHelper.setText(getResources().getString(R.string.choose_alert_edate_tv));
                        btnCounterSteps = 4;
                    }
                } else if (btnCounterSteps == 4) {
                    getInputEdate();
                    addAlertNextBtn.setText(getResources().getString(R.string.terminate));
                    btnCounterSteps = 5;
                } else if (btnCounterSteps == 5) {
                    writeAlertInDB();
                    hideNewAlertSection();
                    showMainBtns();
                    btnCounterSteps = 0;
                }
            }
        });
    }

    private void writeAlertInDB() {
        dbHelper.writeAlertInDB(nameAlert, descAlert, typeAlert, sdateAlert, edateAlert);
        nameAlert = "";
        alertNameInput.setText("");
        alertNameInput.clearFocus();
        descAlert = "";
        alertDescInput.setText("");
        alertDescInput.clearFocus();
        typeAlert = "";
        typeAlertSelected.setText("");
        sdateAlert = "";
        dateInput.clearFocus();
        resetDate(dateInput);
        edateAlert = "";
    }

    public void resetDate(DatePicker myPicker){
        Calendar now = Calendar.getInstance();
        myPicker.updateDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
    }

    private void hideNewAlertSection() {
        dateInput.setVisibility(View.GONE);
        addAlertNextBtn.setVisibility(View.GONE);
        addAlertPreviousBtn.setVisibility(View.GONE);
        containerAddAlert.setAlpha(0.0f);
        textHelper.setAlpha(0.0f);
    }

    private void getInputEdate() {
        String day = String.valueOf(dateInput.getDayOfMonth());
        String month = String.valueOf(dateInput.getMonth());
        String year = String.valueOf(dateInput.getYear());

        edateAlert = day + "-" + month + "-" + year;
    }

    private void getInputSdate() {
        String day = String.valueOf(dateInput.getDayOfMonth());
        String month = String.valueOf(dateInput.getMonth());
        String year = String.valueOf(dateInput.getYear());

        sdateAlert = day + "-" + month + "-" + year;
    }

    private void getInputDesc() {
        descAlert = alertDescInput.getEditableText().toString();
    }

    private void getInputName() {
        nameAlert = alertNameInput.getEditableText().toString();
    }

    private void hideTypeViews() {
        typeAlertSelected.setAlpha(0.0f);
        cubeSelection.setAlpha(0.0f);
        foodSelectionIV.setAlpha(0.0f);
        sportSelectionIV.setAlpha(0.0f);
        insulinSelectionIV.setAlpha(0.0f);
        glycemySelectionIV.setAlpha(0.0f);
        alertNameInput.setVisibility(View.VISIBLE);
    }

    private void showTypeViews() {
        typeAlertSelected.setAlpha(1.0f);
        cubeSelection.setAlpha(1.0f);
        foodSelectionIV.setAlpha(1.0f);
        sportSelectionIV.setAlpha(1.0f);
        insulinSelectionIV.setAlpha(1.0f);
        glycemySelectionIV.setAlpha(1.0f);
        alertNameInput.setVisibility(View.GONE);
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

    //Close database connection ondestroy
    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    @OnClick({R.id.see_all_alerts_btn})
    public void onClickSeeAllAlertsBtn() {
        alerts.addAll(dbHelper.getAlerts());
        sortingList();
        addingIds();
        loadRV();
        loadExitBtn();
    }

    @OnClick({R.id.add_new_alert_btn})
    public void onClickAddAlertBtn() {
        hideMainBtns();
        showMotionViews();
        showNavAddAlertBtn();
        containerAddAlert.setAlpha(1.0f);
        containerAddAlertNavigation.setAlpha(1.0f);
        textHelper.setAlpha(1.0f);
        showTypeViews();
    }

    private void showNavAddAlertBtn() {
        addAlertNextBtn.setVisibility(View.VISIBLE);
        addAlertPreviousBtn.setVisibility(View.VISIBLE);
    }
}
