package com.studio.neopanda.diabetesmeganotes.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.studio.neopanda.diabetesmeganotes.database.DatabaseHelper;
import com.studio.neopanda.diabetesmeganotes.models.InsulinBinder;
import com.studio.neopanda.diabetesmeganotes.models.InsulinInjection;
import com.studio.neopanda.diabetesmeganotes.R;

import java.util.List;

public class EntriesUnitsFragmentAdapter extends RecyclerView.Adapter<EntriesUnitsFragmentAdapter.MyViewHolder> {

    private List<InsulinBinder> mData;
    private Context mContext;
    private DatabaseHelper dbHelper;

    public EntriesUnitsFragmentAdapter(Context context, List<InsulinBinder> mData) {
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
        holder.idDiary.setText("Analyse n° : " + mData.get(position).id);
        holder.modifyEntryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.deleteEntryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use the Builder class for convenient dialog construction
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage(mContext.getResources().getString(R.string.dialog_delete_insulin_entry))
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String[] itemsToDelete = {String.valueOf(mData.get(position).id)};
                                onClickDeleteEntry(itemsToDelete, String.valueOf(mData.get(position).id), position);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                // Create the AlertDialog object and return it
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void onClickDeleteEntry(String[] itemsToDelete, String idDeleted, int position){
        dbHelper = new DatabaseHelper(mContext);
        dbHelper.deleteInjectionInDB(itemsToDelete);
        Toast.makeText(mContext, "The entry n° " + idDeleted + " has been deleted!", Toast.LENGTH_SHORT).show();
        mData.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dateTV;
        TextView insulinUnitsTV;
        TextView idDiary;
        Button modifyEntryBtn;
        Button deleteEntryBtn;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTV = itemView.findViewById(R.id.date_insulin_entry_TV);
            insulinUnitsTV = itemView.findViewById(R.id.units_insulin_entry_TV);
            idDiary = itemView.findViewById(R.id.id_insulin_entry_TV);
            modifyEntryBtn = itemView.findViewById(R.id.modify_insulin_entry_btn);
            deleteEntryBtn = itemView.findViewById(R.id.delete_insulin_entry_btn);
        }
    }
}
