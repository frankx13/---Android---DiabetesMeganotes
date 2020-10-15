package com.studio.neopanda.diabetesmeganotes.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.studio.neopanda.diabetesmeganotes.R;
import com.studio.neopanda.diabetesmeganotes.activities.DashboardActivity;
import com.studio.neopanda.diabetesmeganotes.database.DatabaseHelper;
import com.studio.neopanda.diabetesmeganotes.models.User;

import java.util.List;

public class UserConnectionAdapter extends RecyclerView.Adapter<UserConnectionAdapter.MyViewHolder> {

    private Context mContext;
    private List<User> mData;
    private Activity activity;
    private DatabaseHelper dbHelper;

    public UserConnectionAdapter(Context mContext, Activity activity, List<User> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.activity = activity;
    }

    @NonNull
    @Override
    public UserConnectionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.existing_user_selection_item, parent, false);
        return new UserConnectionAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserConnectionAdapter.MyViewHolder holder, int position) {
        Glide.with(mContext)
                .load(Integer.parseInt(mData.get(position).getUserImg()))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.userImg);

        holder.username.setText(mData.get(position).getUsername());
        holder.deleteUserBtn.setOnClickListener(v -> {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(mContext.getResources().getString(R.string.dialog_delete_existing_user_confirmation, mData.get(position).getUsername()))
                    .setPositiveButton(R.string.yes, (dialog, id) -> {
                        String[] itemsToDelete = {mData.get(position).getUsername()};
                        onClickDeleteView(itemsToDelete, mData.get(position).getUsername(), position);
                    })
                    .setNegativeButton(R.string.no, (dialog, id) -> dialog.dismiss());
            // Create the AlertDialog object and return it
            builder.create().show();
        });
        holder.userImg.setOnClickListener(v -> {
            dbHelper = new DatabaseHelper(mContext);
            dbHelper.writeActiveUserInDB(mData.get(position).getUsername(), mData.get(position).getUserImg());
            loadDashboard();
        });
    }

    private void loadDashboard() {
        Intent intent = new Intent(mContext, DashboardActivity.class);
        mContext.startActivity(intent);
        activity.overridePendingTransition(R.anim.go_up_anim, R.anim.go_down_anim);
        activity.finish();
    }

    private void onClickDeleteView(String[] itemsToDelete, String usernameDeleted, int position) {
        dbHelper = new DatabaseHelper(mContext);
        dbHelper.deleteUserInDB(itemsToDelete);
        Toast.makeText(mContext, "The user " + usernameDeleted + " has been deleted!", Toast.LENGTH_SHORT).show();
        mData.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView userImg;
        ImageView deleteUserBtn;
        TextView username;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            userImg = itemView.findViewById(R.id.img_existing_user);
            deleteUserBtn = itemView.findViewById(R.id.delete_user_btn);
            username = itemView.findViewById(R.id.username_existing_user);
        }
    }
}