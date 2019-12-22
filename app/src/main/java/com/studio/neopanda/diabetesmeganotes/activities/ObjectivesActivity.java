package com.studio.neopanda.diabetesmeganotes.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.studio.neopanda.diabetesmeganotes.R;
import com.studio.neopanda.diabetesmeganotes.adapters.ObjectivesAdapter;
import com.studio.neopanda.diabetesmeganotes.database.DatabaseHelper;
import com.studio.neopanda.diabetesmeganotes.models.Objective;
import com.studio.neopanda.diabetesmeganotes.utils.DateUtils;
import com.studio.neopanda.diabetesmeganotes.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ObjectivesActivity extends AppCompatActivity {

    @BindView(R.id.add_entry_objective)
    public Button addObjectiveBtn;
    @BindView(R.id.exit_entry_objectives_btn)
    public ImageButton exitEntryBtn;
    @BindView(R.id.container_add_objective)
    public LinearLayout newObjectiveContainer;
    @BindView(R.id.validate_new_objective_btn)
    public Button validateNewEntryBtn;
    @BindView(R.id.input_objective_number_days)
    public EditText inputObjectiveDays;
    @BindView(R.id.input_objective_description)
    public EditText inputObjectiveDescription;
    @BindView(R.id.type_food_tv)
    public TextView typeFood;
    @BindView(R.id.type_sport_tv)
    public TextView typeSport;
    @BindView(R.id.type_insulin_tv)
    public TextView typeInsulin;
    @BindView(R.id.type_glycemy_tv)
    public TextView typeGlycemy;
    @BindView(R.id.recyclerView_objectives)
    public RecyclerView recyclerViewObjectives;
    @BindView(R.id.title_app_TV)
    public TextView titleApp;

    private String deadlineObjective = "";
    private String typeObjective = "";
    private String descriptionObjective = "";
    private String todayDate = "";
    private int typeObjectiveSelector = 0;
    private DatabaseHelper dbHelper = new DatabaseHelper(this);
    private List<Objective> objectives;
    private boolean isTableNotEmpty = false;
    private int idEntry = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_objectives);

        ButterKnife.bind(this);
        prepareTransitions();
        objectives = new ArrayList<>();

        Utils.backToDashboard(titleApp, this, ObjectivesActivity.this);

        dbHelper.isTableNotEmpty("Objectives");
        if (!isTableNotEmpty) {
            objectives = dbHelper.getObjectives();
            //TODO define number max of objectives
//            objectives = objectives.subList(0, 3);

            sortingList();
            addingIds();

            loadDataInRV(objectives);
        }

        addObjectiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addObjectiveBtn.setVisibility(View.GONE);
                newObjectiveContainer.setVisibility(View.VISIBLE);
                exitEntryBtn.setVisibility(View.VISIBLE);
                validateNewEntryBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (inputObjectiveDays.getEditableText().toString().equals("")) {
                            Toast.makeText(ObjectivesActivity.this, "Veuillez remplir une durée!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (typeObjectiveSelector == 0) {
                            Toast.makeText(ObjectivesActivity.this, "Veuillez sélectionnez un type pour continuer!", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (typeObjectiveSelector == 1) {
                            typeObjective = "Food";
                        } else if (typeObjectiveSelector == 2) {
                            typeObjective = "Sport";
                        } else if (typeObjectiveSelector == 3) {
                            typeObjective = "Insulin";
                        } else if (typeObjectiveSelector == 4) {
                            typeObjective = "Glycemy";
                        }

                        if (inputObjectiveDescription.getEditableText().toString().equals("")) {
                            Toast.makeText(ObjectivesActivity.this, "Veuillez remplir une description!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        deadlineObjective = inputObjectiveDays.getEditableText().toString();
                        descriptionObjective = inputObjectiveDescription.getEditableText().toString();
                        todayDate = DateUtils.calculateDateOfToday();

                        dbHelper.writeObjectiveInDB(todayDate, deadlineObjective, typeObjective, descriptionObjective);

                        newObjectiveContainer.setVisibility(View.GONE);
                        exitEntryBtn.setVisibility(View.GONE);
                        addObjectiveBtn.setVisibility(View.VISIBLE);

                        Toast.makeText(ObjectivesActivity.this, "Objectif ajouté avec succès!", Toast.LENGTH_SHORT).show();
                    }
                });

                exitEntryBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newObjectiveContainer.setVisibility(View.GONE);
                        exitEntryBtn.setVisibility(View.GONE);
                        addObjectiveBtn.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }

    private void prepareTransitions(){
        // set an exit transition
        getWindow().setExitTransition(new Explode());
        // set an enter transition
//        getWindow().setEnterTransition(new Slide());
    }

    private void sortingList() {
        Collections.sort(objectives, (obj1, obj2) -> obj2.getDate().compareToIgnoreCase(obj1.getDate()));
    }

    private void addingIds() {
        for (Objective o : objectives) {
            o.setIdEntry(idEntry += 1);
        }
    }

    private void loadDataInRV(List<Objective> objectivesList) {
        ObjectivesAdapter adapter = new ObjectivesAdapter(this, objectivesList);
        recyclerViewObjectives.setAdapter(adapter);
        recyclerViewObjectives.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick(R.id.type_food_tv)
    public void onClickFoodType() {
        typeObjectiveSelector = 1;
        typeFood.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        typeSport.setBackgroundColor(getResources().getColor(android.R.color.white));
        typeInsulin.setBackgroundColor(getResources().getColor(android.R.color.white));
        typeGlycemy.setBackgroundColor(getResources().getColor(android.R.color.white));
    }

    @OnClick(R.id.type_sport_tv)
    public void onClickSportType() {
        typeObjectiveSelector = 2;
        typeFood.setBackgroundColor(getResources().getColor(android.R.color.white));
        typeSport.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        typeInsulin.setBackgroundColor(getResources().getColor(android.R.color.white));
        typeGlycemy.setBackgroundColor(getResources().getColor(android.R.color.white));
    }

    @OnClick(R.id.type_insulin_tv)
    public void onClickInsulinType() {
        typeObjectiveSelector = 1;
        typeObjectiveSelector = 3;
        typeFood.setBackgroundColor(getResources().getColor(android.R.color.white));
        typeSport.setBackgroundColor(getResources().getColor(android.R.color.white));
        typeInsulin.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        typeGlycemy.setBackgroundColor(getResources().getColor(android.R.color.white));
    }

    @OnClick(R.id.type_glycemy_tv)
    public void onClickGlycemyType() {
        typeObjectiveSelector = 4;
        typeFood.setBackgroundColor(getResources().getColor(android.R.color.white));
        typeSport.setBackgroundColor(getResources().getColor(android.R.color.white));
        typeInsulin.setBackgroundColor(getResources().getColor(android.R.color.white));
        typeGlycemy.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
    }
}
