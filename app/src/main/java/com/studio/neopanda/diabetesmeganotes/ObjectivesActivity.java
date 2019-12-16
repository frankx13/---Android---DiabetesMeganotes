package com.studio.neopanda.diabetesmeganotes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ObjectivesActivity extends AppCompatActivity {

    @BindView(R.id.add_entry_objective)
    Button addObjectiveBtn;
    @BindView(R.id.exit_entry_objectives_btn)
    ImageButton exitEntryBtn;
    @BindView(R.id.container_add_objective)
    LinearLayout newObjectiveContainer;
    @BindView(R.id.validate_new_objective_btn)
    Button validateNewEntryBtn;
    @BindView(R.id.input_objective_number_days)
    EditText inputObjectiveDays;
    @BindView(R.id.input_objective_description)
    EditText inputObjectiveDescription;
    @BindView(R.id.type_food_tv)
    TextView typeFood;
    @BindView(R.id.type_sport_tv)
    TextView typeSport;
    @BindView(R.id.type_insulin_tv)
    TextView typeInsulin;
    @BindView(R.id.type_glycemy_tv)
    TextView typeGlycemy;
    @BindView(R.id.recyclerView_objectives)
    RecyclerView recyclerViewObjectives;

    private String deadlineObjective = "";
    private String typeObjective = "";
    private String descriptionObjective = "";
    private String todayDate = "";
    private int typeObjectiveSelector = 0;
    private DatabaseHelper dbHelper = new DatabaseHelper(this);
    private List<Objective> objectives;
    private boolean isTableNotEmpty = false;
    private int idEntry = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectives);

        ButterKnife.bind(this);

        objectives = new ArrayList<>();

        dbHelper.isTableNotEmpty("Objectives");
        if (!isTableNotEmpty){
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

    private void sortingList() {
        Collections.sort(objectives, (obj1, obj2) -> obj2.getDate().compareToIgnoreCase(obj1.getDate()));
    }

    private void addingIds() {
        for (Objective o : objectives) {
            o.setIdEntry(idEntry += 1);
        }
    }

    private void loadDataInRV(List<Objective> objectivesList){
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
