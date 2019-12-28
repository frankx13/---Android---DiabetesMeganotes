package com.studio.neopanda.diabetesmeganotes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.studio.neopanda.diabetesmeganotes.R;
import com.studio.neopanda.diabetesmeganotes.models.Objective;
import com.studio.neopanda.diabetesmeganotes.utils.DateUtils;

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
        holder.numberTV.setText("Objectif n°" + mData.get(position).idEntry);
        holder.typeTV.setText("Type : " + mData.get(position).type);
        holder.durationTV.setText("Durée : " + mData.get(position).duration + "J");
        holder.descriptionTV.setText("Description : " + mData.get(position).getDescription());

        //TODO startedDateTV' text need to be implemented inside the object instead
        holder.startedDateTV.setText("Début : " + DateUtils.calculateDateOfToday());
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
        TextView startedDateTV;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            numberTV = itemView.findViewById(R.id.objectives_item_number);
            typeTV = itemView.findViewById(R.id.objectives_item_type);
            durationTV = itemView.findViewById(R.id.objectives_item_duration);
            descriptionTV = itemView.findViewById(R.id.objectives_item_description);
            startedDateTV = itemView.findViewById(R.id.objectives_item_start_date);
        }
    }
}
