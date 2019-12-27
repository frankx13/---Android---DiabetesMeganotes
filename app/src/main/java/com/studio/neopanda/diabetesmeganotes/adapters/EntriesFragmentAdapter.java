package com.studio.neopanda.diabetesmeganotes.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.studio.neopanda.diabetesmeganotes.R;
import com.studio.neopanda.diabetesmeganotes.database.DatabaseHelper;
import com.studio.neopanda.diabetesmeganotes.models.GlycemyBinder;
import com.studio.neopanda.diabetesmeganotes.utils.UIUtils;

import java.util.List;

public class EntriesFragmentAdapter extends RecyclerView.Adapter<EntriesFragmentAdapter.MyViewHolder> {

    private List<GlycemyBinder> mData;
    private Context mContext;
    private Activity activity;
    private DatabaseHelper dbHelper;
    private int id;
    private String newGlycemyUpdated;

    public EntriesFragmentAdapter(Context context, List<GlycemyBinder> mData, Activity activity) {
        this.mContext = context;
        this.mData = mData;
        this.activity = activity;
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
        dbHelper = new DatabaseHelper(mContext);
        holder.dateTV.setText("Date : " + mData.get(position).date);
        holder.levelGlycemyTV.setText("Glycémie : " + mData.get(position).glycemy);
        holder.idDiary.setText("Analyse n° : " + mData.get(position).id);

        holder.modifyEntryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.containerUpdateEntry.setVisibility(View.VISIBLE);
                holder.updateEntryBtn.setVisibility(View.VISIBLE);
                holder.containerActiondEntry.setVisibility(View.GONE);
                holder.oldUnits.setText(mData.get(position).getGlycemy());

                holder.updateEntryBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newGlycemyUpdated = holder.newUnits.getEditableText().toString();
                        if (newGlycemyUpdated.equals("")) {
                            Toast.makeText(mContext, "Vous devez quitter la page si vous ne souhaitez pas modifier cette valeur, ou bien en indiquer une nouvelle pour effectuer la modification", Toast.LENGTH_LONG).show();
                        } else {
                            onClickUpdateEntry(position);
                        }

                        holder.containerUpdateEntry.setVisibility(View.GONE);
                        holder.updateEntryBtn.setVisibility(View.GONE);
                        holder.containerActiondEntry.setVisibility(View.VISIBLE);
                    }
                });
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

    private void onClickUpdateEntry(int position) {
        id = mData.get(position).id;
        dbHelper.updateGlycemyEntry(newGlycemyUpdated, id);
        Toast.makeText(mContext, "L'entrée n° " + mData.get(position).id + " a été modifiée!", Toast.LENGTH_SHORT).show();
        mData.get(position).glycemy = newGlycemyUpdated;
        notifyItemChanged(position);
        UIUtils.hideKeyboard(activity);
    }

    private void onClickDeleteEntry(String[] itemsToDelete, String idDeleted, int position) {
        dbHelper.deleteGlycemyInDB(itemsToDelete);
        Toast.makeText(mContext, "L'entrée n° " + idDeleted + " a été supprimée!", Toast.LENGTH_SHORT).show();
        mData.remove(position);
        notifyItemRemoved(position);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dateTV;
        TextView levelGlycemyTV;
        TextView idDiary;
        LinearLayout containerUpdateEntry;
        LinearLayout containerActiondEntry;
        TextView oldUnits;
        EditText newUnits;
        Button modifyEntryBtn;
        Button deleteEntryBtn;
        Button updateEntryBtn;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTV = itemView.findViewById(R.id.date_diary_entry_TV);
            levelGlycemyTV = itemView.findViewById(R.id.level_diary_entry_TV);
            idDiary = itemView.findViewById(R.id.id_diary_entry_TV);
            modifyEntryBtn = itemView.findViewById(R.id.modify_diary_entry_btn);
            deleteEntryBtn = itemView.findViewById(R.id.delete_diary_entry_btn);
            containerUpdateEntry = itemView.findViewById(R.id.container_update_entry_glycemy);
            oldUnits = itemView.findViewById(R.id.glycemy_entry_old_TV);
            newUnits = itemView.findViewById(R.id.glycemy_entry_new_TV);
            updateEntryBtn = itemView.findViewById(R.id.validate_update_entry_btn);
            containerActiondEntry = itemView.findViewById(R.id.container_actions_diary);
        }
    }
}
