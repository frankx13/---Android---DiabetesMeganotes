package com.studio.neopanda.diabetesmeganotes.fragments;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.studio.neopanda.diabetesmeganotes.R;
import com.studio.neopanda.diabetesmeganotes.adapters.EntriesFragmentAdapter;
import com.studio.neopanda.diabetesmeganotes.database.DatabaseHelper;
import com.studio.neopanda.diabetesmeganotes.models.Glycemy;
import com.studio.neopanda.diabetesmeganotes.models.GlycemyBinder;
import com.studio.neopanda.diabetesmeganotes.utils.AverageGlycemyUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class EntriesDiaryFragment extends Fragment {

    private LinearLayout containerDiary;
    private RecyclerView recyclerView;
    private TextView averageGlycemyLevel;

    private List<Glycemy> glycemies;
    private List<GlycemyBinder> glycemyBinder;
    private DatabaseHelper dbHelper = null;
    private int idEntry = 0;
    private List<Double> glycemyLevels;
    private String userId = "";

    public EntriesDiaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entries_diary, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dbHelper = new DatabaseHelper(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();

        recyclerView = getActivity().findViewById(R.id.recyclerview_diary_entries);
        averageGlycemyLevel = getActivity().findViewById(R.id.average_level_glycemy_TV);
        containerDiary = getActivity().findViewById(R.id.container_diary_journal);
        glycemies = new ArrayList<>();
        glycemyLevels = new ArrayList<>();
        glycemyBinder = new ArrayList<>();

        userId = dbHelper.getActiveUserInDB().get(0).getUsername();

        glycemies = dbHelper.getGlycemies();

        if (glycemies.size() > 0) {
            sortingList();
            addingIds();
            loadingTextViewAverage();
            antiUIBreakthrough();
            filteringWithUsername();
            onLoadRecyclerView();
        } else {
            Toast.makeText(getActivity(), "Aucune entrée trouvée, essayez d'en ajouter !", Toast.LENGTH_SHORT).show();
        }
    }

    private void filteringWithUsername() {
        for (Glycemy g : glycemies) {
            glycemyBinder.add(new GlycemyBinder(g.glycemyLevel, g.date));
        }

        if (!glycemyBinder.isEmpty()) {
            for (Iterator<GlycemyBinder> glyBinderIterator = glycemyBinder.iterator(); glyBinderIterator.hasNext(); ) {
                // Get next item.
                GlycemyBinder glyBinder = glyBinderIterator.next();
                // If current item title is not from the current user, remove that entry from list.
                if (!userId.equalsIgnoreCase(glyBinder.getDataID())) {
                    glyBinderIterator.remove();
                } else {
                    // Output current glycemy.
                    Log.e("ITERATORBUG", "filteringWithUsername: " + glyBinder);
                }
            }
        }

        Log.e("ENDITARATIONLOG", "EEEEND ! : " + glycemyBinder);
    }

    private void antiUIBreakthrough() {
        containerDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private double calculateAverageGlycemyLevel() {
        for (Glycemy g : glycemies) {
            glycemyLevels.add(Double.parseDouble(g.glycemyLevel));
        }
        return AverageGlycemyUtils.calculateAverageGlycemyAllResults(glycemyLevels);
    }

    private void loadingTextViewAverage() {
        double average = calculateAverageGlycemyLevel();
        if (average >= 0.80 && average <= 1.5) {
            averageGlycemyLevel.setTextColor(getResources().getColor(android.R.color.holo_green_light));
            averageGlycemyLevel.setCompoundDrawablesRelativeWithIntrinsicBounds
                    (getResources().getDrawable(R.drawable.ic_warning_red_24dp),
                            null, null, null);
        } else if (average < 0.80) {
            averageGlycemyLevel.setTextColor(getResources().getColor(android.R.color.holo_red_light));
        } else if (average > 1.5 && average <= 1.8) {
            averageGlycemyLevel.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
        } else if (average > 1.8 && average <= 2.20) {
            averageGlycemyLevel.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
        } else {
            averageGlycemyLevel.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            averageGlycemyLevel.setCompoundDrawablesRelativeWithIntrinsicBounds
                    (getResources().getDrawable(R.drawable.ic_warning_red_24dp),
                            null, null, null);
        }
        averageGlycemyLevel.setText("≈" + average);
    }

    private void sortingList() {
        Collections.sort(glycemies, (obj1, obj2) -> obj2.getDate().compareToIgnoreCase(obj1.getDate()));
    }

    private void addingIds() {
        for (Glycemy entry : glycemies) {
            entry.setIdEntry(idEntry += 1);
        }
    }

    private void onLoadRecyclerView() {
        Toast.makeText(getActivity(), "Aucune entrée présente, essayez d'en ajouter!", Toast.LENGTH_SHORT).show();
        EntriesFragmentAdapter adapter = new EntriesFragmentAdapter(getContext(), glycemies);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
    }


}
