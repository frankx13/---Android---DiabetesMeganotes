package com.studio.neopanda.diabetesmeganotes.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.studio.neopanda.diabetesmeganotes.R;
import com.studio.neopanda.diabetesmeganotes.database.DatabaseHelper;
import com.studio.neopanda.diabetesmeganotes.models.Alert;
import com.studio.neopanda.diabetesmeganotes.utils.UIUtils;

import java.util.List;

public class AlertsAdapter extends RecyclerView.Adapter<AlertsAdapter.MyViewHolder> {

    private List<Alert> mData;
    private Context mContext;
    private DatabaseHelper dbHelper;
    private int id;
    private String isActive;
    private Activity activity;

    public AlertsAdapter(List<Alert> mData, Context mContext, Activity activity) {
        this.mData = mData;
        this.mContext = mContext;
        this.activity = activity;
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
        String acti = mData.get(position).getIsActive();

        holder.sdateTv.setText("Commence le : " + mData.get(position).getStartMoment());
        holder.edateTv.setText("Finit le : " + mData.get(position).getEndMoment());
        holder.type.setText("Type : " + mData.get(position).getType());
        holder.name.setText("Nom : " + mData.get(position).getName());
        holder.description.setText("Mémo : " + mData.get(position).getDescription());
        holder.number.setText("Alarme n°" + mData.get(position).getIdEntry());

        if (acti.equalsIgnoreCase("active")){
            holder.toggleButton.setChecked(true);
        }

        holder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isActive = "active";
                    onClickUpdateEntry(position);
                } else {
                    isActive = "inactive";
                    onClickUpdateEntry(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void onClickUpdateEntry(int position) {
        id = mData.get(position).idEntry;
        dbHelper.updateAlertStatus(isActive, id);
        if (isActive.equals("active")){
            Toast.makeText(mContext, "L'alerte n° " + mData.get(position).idEntry + " a été activée!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "L'alerte n° " + mData.get(position).idEntry + " a été désactivée!", Toast.LENGTH_SHORT).show();
        }
        mData.get(position).isActive = isActive;
        notifyItemChanged(position);
        UIUtils.hideKeyboard(activity);
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
