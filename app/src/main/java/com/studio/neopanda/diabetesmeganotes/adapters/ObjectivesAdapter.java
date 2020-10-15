package com.studio.neopanda.diabetesmeganotes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.studio.neopanda.diabetesmeganotes.R;
import com.studio.neopanda.diabetesmeganotes.database.DatabaseHelper;
import com.studio.neopanda.diabetesmeganotes.models.ObjectiveBinder;
import com.studio.neopanda.diabetesmeganotes.utils.DateUtils;

import java.util.List;

public class ObjectivesAdapter extends RecyclerView.Adapter<ObjectivesAdapter.MyViewHolder> {

    private Context mContext;
    private List<ObjectiveBinder> mData;
    private DatabaseHelper dbHelper;

    public ObjectivesAdapter(Context context, List<ObjectiveBinder> mData) {
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
        dbHelper = new DatabaseHelper(mContext);

        holder.numberTV.setText(mContext.getResources().getString(R.string.objective_number, mData.get(position).idEntry));
        holder.typeTV.setText(mContext.getResources().getString(R.string.type, mData.get(position).type));
        holder.durationTV.setText(mContext.getResources().getString(R.string.duration, mData.get(position).duration + "J"));
        holder.descriptionTV.setText(mContext.getResources().getString(R.string.description, mData.get(position).getDescription()));

        //TODO startedDateTV' text need to be implemented inside the object instead
        holder.startedDateTV.setText(mContext.getResources().getString(R.string.start, DateUtils.calculateDateOfToday()));

        holder.deleteEntryBtn.setOnClickListener(v -> {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(mContext.getResources().getString(R.string.dialog_delete_insulin_entry))
                    .setPositiveButton(R.string.yes, (dialog, id) -> {
                        String[] itemsToDelete = {String.valueOf(mData.get(position).idEntry)};
                        onClickDeleteEntry(itemsToDelete, String.valueOf(mData.get(position).idEntry), position);
                    })
                    .setNegativeButton(R.string.no, (dialog, id) -> dialog.dismiss());
            // Create the AlertDialog object and return it
            builder.create().show();
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void onClickDeleteEntry(String[] itemsToDelete, String idDeleted, int position) {
        dbHelper.deleteObjectiveInDB(itemsToDelete);
        mData.remove(position);
        notifyItemRemoved(position);
        Toast.makeText(mContext, "L'entrée n° " + idDeleted + " a été supprimée!", Toast.LENGTH_SHORT).show();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView numberTV;
        TextView typeTV;
        TextView durationTV;
        TextView descriptionTV;
        TextView startedDateTV;
        Button deleteEntryBtn;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            numberTV = itemView.findViewById(R.id.objectives_item_number);
            typeTV = itemView.findViewById(R.id.objectives_item_type);
            durationTV = itemView.findViewById(R.id.objectives_item_duration);
            descriptionTV = itemView.findViewById(R.id.objectives_item_description);
            startedDateTV = itemView.findViewById(R.id.objectives_item_start_date);
            deleteEntryBtn = itemView.findViewById(R.id.delete_objective_entry_btn);
        }
    }
}
