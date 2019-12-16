package com.studio.neopanda.diabetesmeganotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ObjectivesAdapter extends RecyclerView.Adapter<ObjectivesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Objective> mData;

    public ObjectivesAdapter(Context context, List<Objective> mData) {
        this.mContext = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ObjectivesAdapter.MyViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.objective_item, viewGroup, false);
        return new ObjectivesAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ObjectivesAdapter.MyViewHolder holder, int position) {
        holder.numberTV.setText("Objectif " + mData.get(position).idEntry);
        holder.typeTV.setText("Type d'objectif : " + mData.get(position).type);
        holder.durationTV.setText("Durée : " + mData.get(position).duration + "J");
        holder.descriptionTV.setText(mData.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView numberTV;
        TextView typeTV;
        TextView durationTV;
        TextView descriptionTV;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            numberTV = itemView.findViewById(R.id.objectives_item_number);
            typeTV = itemView.findViewById(R.id.objectives_item_type);
            durationTV = itemView.findViewById(R.id.objectives_item_duration);
            descriptionTV = itemView.findViewById(R.id.objectives_item_description);
        }
    }
}
