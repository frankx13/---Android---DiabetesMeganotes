package com.studio.neopanda.diabetesmeganotes.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.studio.neopanda.diabetesmeganotes.R;
import com.studio.neopanda.diabetesmeganotes.adapters.EntriesUnitsFragmentAdapter;
import com.studio.neopanda.diabetesmeganotes.database.DatabaseHelper;
import com.studio.neopanda.diabetesmeganotes.models.InsulinBinder;
import com.studio.neopanda.diabetesmeganotes.utils.AverageGlycemyUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class EntriesInsulinFragment extends Fragment {

    //UI
    private LinearLayout containerInsulin;
    private RecyclerView recyclerViewInsulin;
    private TextView averageInsulinLevel;

    //DATA
    private DatabaseHelper dbHelper = null;
    private List<InsulinBinder> insulinInjections;
    private int idEntry = 0;
    private List<Integer> insulinUnits;
    private String userId = "";

    public EntriesInsulinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entries_insulin, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dbHelper = new DatabaseHelper(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();

        initViews();
        initVars();
        getCurrentUser();
        fetchInjectionsInDB();
        continueProcess();
    }

    private void getCurrentUser() {
        userId = dbHelper.getActiveUserInDB().get(0).getUsername();
    }

    private void initVars() {
        insulinUnits = new ArrayList<>();
        insulinInjections = new ArrayList<>();
    }

    private void initViews() {
        recyclerViewInsulin = Objects.requireNonNull(getActivity()).findViewById(R.id.recyclerview_insulin_entries);
        averageInsulinLevel = getActivity().findViewById(R.id.average_level_insulin_TV);
        containerInsulin = getActivity().findViewById(R.id.container_diary_journal_insulin);
    }

    private void continueProcess() {
        sortingList();
        addingIds();
        loadingTextViewAverage();
        antiUIBreakthrough();
        onLoadRecyclerView();
    }

    private void fetchInjectionsInDB() {
        insulinInjections = dbHelper.getBindedInsulinData(userId);
    }

    private void antiUIBreakthrough() {
        containerInsulin.setOnClickListener(v -> {

        });
    }

    private void sortingList() {
        Collections.sort(insulinInjections, (obj1, obj2) -> obj2.getDate().compareToIgnoreCase(obj1.getDate()));
    }

    private void addingIds() {
        for (InsulinBinder injection : insulinInjections) {
            injection.setIdEntry(idEntry += 1);
        }
    }

    private int calculateAverageUnitsByInjection() {
        for (InsulinBinder injection : insulinInjections) {
            insulinUnits.add(Integer.valueOf(injection.numberUnit));
        }

        return AverageGlycemyUtils.calculateAverageInsulinUnits(insulinUnits);
    }

    private void loadingTextViewAverage() {
        int average = calculateAverageUnitsByInjection();

        averageInsulinLevel.setText("≈ " + average + "unités par injection");
    }


    private void onLoadRecyclerView() {
        EntriesUnitsFragmentAdapter adapter = new EntriesUnitsFragmentAdapter(getContext(), insulinInjections, getActivity());
        recyclerViewInsulin.setAdapter(adapter);
        recyclerViewInsulin.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.notifyDataSetChanged();
    }
}
