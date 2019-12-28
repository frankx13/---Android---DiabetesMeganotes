package com.studio.neopanda.diabetesmeganotes.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.studio.neopanda.diabetesmeganotes.R;
import com.studio.neopanda.diabetesmeganotes.adapters.ObjectivesAdapter;
import com.studio.neopanda.diabetesmeganotes.database.DatabaseHelper;
import com.studio.neopanda.diabetesmeganotes.models.ObjectiveBinder;
import com.studio.neopanda.diabetesmeganotes.utils.DateUtils;
import com.studio.neopanda.diabetesmeganotes.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ObjectivesActivity extends AppCompatActivity {

    //UI
    @BindView(R.id.add_entry_objective)
    public Button addObjectiveBtn;
    @BindView(R.id.container_add_objective)
    public LinearLayout newObjectiveContainer;
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
    @BindView(R.id.new_objective_iscustom_tv)
    public TextView newObjectiveCustomTV;
    @BindView(R.id.container_new_objective_custom_choice)
    public LinearLayout containerCustomNewObjective;
    @BindView(R.id.add_predefined_objective_btn)
    public Button addPredefObjectiveBtn;
    @BindView(R.id.add_custom_objective_btn)
    public Button addCustomObjectiveBtn;
    @BindView(R.id.container_validation_btns)
    public LinearLayout containerValidationBtns;
    @BindView(R.id.validate_entry_objectives_btn)
    public ImageButton validateEntryBtn;
    @BindView(R.id.exit_entry_objectives_btn)
    public ImageButton exitEntryBtn;
    @BindView(R.id.objectives_scroller)
    ScrollView objectivesScroller;
    @BindView(R.id.scroller_packs)
    ScrollView packsScroller;
    @BindView(R.id.show_entries_objective)
    Button showEntriesObjectives;
    @BindView(R.id.exit_show_objectives_btn)
    ImageButton exitShowObjectivesBtn;
    @BindView(R.id.first_pack_objective)
    TextView firstPack;
    @BindView(R.id.second_pack_objective)
    TextView secondPack;
    @BindView(R.id.third_pack_objective)
    TextView thirdPack;
    @BindView(R.id.forth_pack_objective)
    TextView forthPack;

    //DATA
    private String deadlineObjective = "";
    private String typeObjective = "";
    private String descriptionObjective = "";
    private String todayDate = "";
    private String userId = "";
    private int typeObjectiveSelector = 0;
    private DatabaseHelper dbHelper = new DatabaseHelper(this);
    private List<ObjectiveBinder> objectives;
    private boolean isTableNotEmpty = false;
    private int idEntry = 0;
    private ObjectivesAdapter adapter;
    private boolean packChosen = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_objectives);

        ButterKnife.bind(this);
        objectives = new ArrayList<>();
        userId = dbHelper.getActiveUserInDB().get(0).getUsername();

        Utils.backToDashboard(titleApp, this, ObjectivesActivity.this);

        dbHelper.isTableNotEmpty("Objectives");
        if (!isTableNotEmpty) {
            getObjectives();
            if (objectives.size() > 9) {
                objectives = objectives.subList(0, 9);
            }
            sortingList();
            addingIds();

            loadDataInRV(objectives);
        }

        showEntriesObjectives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleApp.setVisibility(View.GONE);
                addObjectiveBtn.setVisibility(View.GONE);
                showEntriesObjectives.setVisibility(View.GONE);
                objectivesScroller.setVisibility(View.VISIBLE);
                exitShowObjectivesBtn.setVisibility(View.VISIBLE);

                exitShowObjectivesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        objectivesScroller.setVisibility(View.GONE);
                        exitShowObjectivesBtn.setVisibility(View.GONE);
                        titleApp.setVisibility(View.VISIBLE);
                        addObjectiveBtn.setVisibility(View.VISIBLE);
                        showEntriesObjectives.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        addObjectiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (objectives.size() > 8) {
                    Toast.makeText(ObjectivesActivity.this, "Limite d'objectifs atteinte (9).", Toast.LENGTH_SHORT).show();
                } else {
                    addObjectiveBtn.setVisibility(View.GONE);
                    objectivesScroller.setVisibility(View.GONE);
                    newObjectiveCustomTV.setVisibility(View.VISIBLE);
                    containerCustomNewObjective.setVisibility(View.VISIBLE);

                    addCustomObjectiveBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            newObjectiveCustomTV.setVisibility(View.GONE);
                            containerCustomNewObjective.setVisibility(View.GONE);
                            addObjectiveBtn.setVisibility(View.GONE);
                            containerValidationBtns.setVisibility(View.VISIBLE);
                            newObjectiveContainer.setVisibility(View.VISIBLE);
                            validateEntryBtn.setOnClickListener(new View.OnClickListener() {
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

                                    dbHelper.writeObjectiveInDB(todayDate, deadlineObjective, typeObjective, descriptionObjective, userId);

                                    newObjectiveContainer.setVisibility(View.GONE);
                                    containerValidationBtns.setVisibility(View.GONE);
                                    addObjectiveBtn.setVisibility(View.VISIBLE);

                                    Toast.makeText(ObjectivesActivity.this, "Objectif ajouté avec succès!", Toast.LENGTH_SHORT).show();
                                    getObjectives();
                                }
                            });
                        }
                    });

                    addPredefObjectiveBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            newObjectiveCustomTV.setVisibility(View.GONE);
                            containerCustomNewObjective.setVisibility(View.GONE);
                            addObjectiveBtn.setVisibility(View.GONE);
                            titleApp.setVisibility(View.GONE);
                            showEntriesObjectives.setVisibility(View.GONE);
                            packsScroller.setVisibility(View.VISIBLE);

                            chooseOnePackAndLeave();
                        }
                    });

                    exitEntryBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            newObjectiveContainer.setVisibility(View.GONE);
                            containerValidationBtns.setVisibility(View.GONE);
                            addObjectiveBtn.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
    }

    private void chooseOnePackAndLeave() {
        firstPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.writeObjectiveInDB(DateUtils.calculateDateOfToday(), "7", "Alimentation", "Une courte diète pour booster votre corps!", userId);
                packChosen = true;
                packChosenEnd();
            }
        });
        secondPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.writeObjectiveInDB(DateUtils.calculateDateOfToday(), "7", "Sport", "Un petit entrainement pour vous remettre dans le bain!", userId);
                packChosen = true;
                packChosenEnd();
            }
        });
        thirdPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.writeObjectiveInDB(DateUtils.calculateDateOfToday(), "7", "Insulin", "Réduction des doses d'insuline faites, vous irez moins souvent à la pharmacie !:)", userId);
                packChosen = true;
                packChosenEnd();
            }
        });
        forthPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.writeObjectiveInDB(DateUtils.calculateDateOfToday(), "7", "Glycemy", "Équilibrez votre diabète en réduisant votre moyenne glycémique hebdomadaire !", userId);
                packChosen = true;
                packChosenEnd();
            }
        });
    }

    private void packChosenEnd() {
        if (packChosen) {
            packsScroller.setVisibility(View.GONE);
            titleApp.setVisibility(View.VISIBLE);
            showEntriesObjectives.setVisibility(View.VISIBLE);
            addObjectiveBtn.setVisibility(View.VISIBLE);
            packChosen = false;
        }
    }

    private void getObjectives() {
        objectives = dbHelper.getObjectives(userId);
        if (objectives.isEmpty()) {
            showEntriesObjectives.setVisibility(View.GONE);
        } else {
            showEntriesObjectives.setVisibility(View.VISIBLE);
        }
    }

    private void sortingList() {
        Collections.sort(objectives, (obj1, obj2) -> obj2.getDate().compareToIgnoreCase(obj1.getDate()));
    }

    private void addingIds() {
        for (ObjectiveBinder o : objectives) {
            o.setIdEntry(idEntry += 1);
        }
    }

    private void loadDataInRV(List<ObjectiveBinder> objectivesList) {
        adapter = new ObjectivesAdapter(this, objectivesList);
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
