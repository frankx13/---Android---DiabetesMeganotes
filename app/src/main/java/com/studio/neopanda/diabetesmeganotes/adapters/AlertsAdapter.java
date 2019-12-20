package com.studio.neopanda.diabetesmeganotes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.studio.neopanda.diabetesmeganotes.R;
import com.studio.neopanda.diabetesmeganotes.models.Alert;

import java.util.List;

public class AlertsAdapter extends RecyclerView.Adapter<AlertsAdapter.MyViewHolder> {

    private List<Alert> mData;
    private Context mContext;

    public AlertsAdapter(List<Alert> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AlertsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.alert_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertsAdapter.MyViewHolder holder, int position) {
        holder.sdateTv.setText("Commence le : " + mData.get(position).getStartMoment());
        holder.edateTv.setText("Finit le : " + mData.get(position).getEndMoment());
        holder.type.setText("Type : " + mData.get(position).getType());
        holder.name.setText("Nom : " + mData.get(position).getName());
        holder.description.setText("Mémo : " + mData.get(position).getDescription());
        holder.number.setText("Alarme n°" + mData.get(position).getIdEntry());
        if (mData.get(position).isActive){
            holder.toggleButton.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView sdateTv;
        TextView edateTv;
        TextView type;
        TextView name;
        TextView description;
        TextView number;
        ToggleButton toggleButton;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            sdateTv = itemView.findViewById(R.id.sdate_alert_entry_TV);
            edateTv = itemView.findViewById(R.id.edate_alert_entry_TV);
            type = itemView.findViewById(R.id.type_alert_entry_TV);
            name = itemView.findViewById(R.id.name_alert_entry_TV);
            description = itemView.findViewById(R.id.description_alert_entry_TV);
            number = itemView.findViewById(R.id.number_alert_entry_TV);
            toggleButton = itemView.findViewById(R.id.toggle_alert);
        }
    }
}
