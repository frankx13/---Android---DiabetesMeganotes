package com.studio.neopanda.diabetesmeganotes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.studio.neopanda.diabetesmeganotes.models.InsulinInjection;
import com.studio.neopanda.diabetesmeganotes.R;

import java.util.List;

public class EntriesUnitsFragmentAdapter extends RecyclerView.Adapter<EntriesUnitsFragmentAdapter.MyViewHolder> {

    private List<InsulinInjection> mData;
    private Context mContext;

    public EntriesUnitsFragmentAdapter(Context context, List<InsulinInjection> mData) {
        this.mContext = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public EntriesUnitsFragmentAdapter.MyViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.insulin_diary_item, viewGroup, false);
        return new EntriesUnitsFragmentAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EntriesUnitsFragmentAdapter.MyViewHolder holder, int position) {
        holder.dateTV.setText("Date : " + mData.get(position).date);
        holder.insulinUnitsTV.setText("Unités : " + mData.get(position).numberUnit);
        holder.idDiary.setText("Analyse n° : " + mData.get(position).idEntry);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dateTV;
        TextView insulinUnitsTV;
        TextView idDiary;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTV = itemView.findViewById(R.id.date_insulin_entry_TV);
            insulinUnitsTV = itemView.findViewById(R.id.units_insulin_entry_TV);
            idDiary = itemView.findViewById(R.id.id_insulin_entry_TV);
        }
    }
}
