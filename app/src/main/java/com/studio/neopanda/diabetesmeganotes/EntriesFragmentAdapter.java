package com.studio.neopanda.diabetesmeganotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EntriesFragmentAdapter extends RecyclerView.Adapter<EntriesFragmentAdapter.MyViewHolder> {

    private List<Glycemy> mData;
    private Context mContext;

    public EntriesFragmentAdapter(Context context, List<Glycemy> mData) {
        this.mContext = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.entry_diary_item, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.dateTV.setText("Date : " + mData.get(position).date);
        holder.levelGlycemyTV.setText("Glycémie : " + mData.get(position).glycemyLevel);
        holder.idDiary.setText("Analyse n° : " + mData.get(position).getIdEntry());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dateTV;
        TextView levelGlycemyTV;
        TextView idDiary;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTV = itemView.findViewById(R.id.date_diary_entry_TV);
            levelGlycemyTV = itemView.findViewById(R.id.level_diary_entry_TV);
            idDiary = itemView.findViewById(R.id.id_diary_entry_TV);
        }
    }
}
