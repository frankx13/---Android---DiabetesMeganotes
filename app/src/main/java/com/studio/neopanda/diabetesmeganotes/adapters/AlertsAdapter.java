package com.studio.neopanda.diabetesmeganotes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.studio.neopanda.diabetesmeganotes.R;
import com.studio.neopanda.diabetesmeganotes.database.DatabaseHelper;
import com.studio.neopanda.diabetesmeganotes.models.Alert;

import java.util.List;

public class AlertsAdapter extends RecyclerView.Adapter<AlertsAdapter.MyViewHolder> {

    private List<Alert> mData;
    private Context mContext;
    private DatabaseHelper dbHelper;
    private String isActive;

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
        dbHelper = new DatabaseHelper(mContext);
        isActive = mData.get(position).getIsActive();

        holder.sdateTv.setText(mContext.getResources().getString(R.string.start_the, mData.get(position).getStartMoment()));
        holder.edateTv.setText(mContext.getResources().getString(R.string.finish_the, mData.get(position).getEndMoment()));
        holder.type.setText(mContext.getResources().getString(R.string.type_alarm, mData.get(position).getType()));
        holder.name.setText(mContext.getResources().getString(R.string.name_alarm, mData.get(position).getName()));
        holder.description.setText(mContext.getResources().getString(R.string.memo_alarm, mData.get(position).getDescription()));
        holder.number.setText(mContext.getResources().getString(R.string.number_alarm, mData.get(position).getIdEntry()));

        if (isActive.equalsIgnoreCase("active")) {
            holder.toggleButton.setChecked(true);
        }

        holder.toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                onClickUpdateEntry(position, "active");
            } else {
                onClickUpdateEntry(position, "inactive");
            }

            if (isActive.equals("active")) {
                createAlarm();
            } else {
                disableAlarm();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void createAlarm() {

    }

    private void disableAlarm() {

    }

    private void onClickUpdateEntry(int position, String isAlertActive) {
        int id = mData.get(position).idEntry;
        dbHelper.updateAlertStatus(isAlertActive, id);
        if (isAlertActive.equals("active")) {
            Toast.makeText(mContext, "L'alerte n° " + mData.get(position).idEntry + " a été activée!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "L'alerte n° " + mData.get(position).idEntry + " a été désactivée!", Toast.LENGTH_SHORT).show();
        }
        mData.get(position).isActive = isAlertActive;
        notifyItemChanged(position);
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
