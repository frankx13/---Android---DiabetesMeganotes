package com.studio.neopanda.diabetesmeganotes.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.studio.neopanda.diabetesmeganotes.R;
import com.studio.neopanda.diabetesmeganotes.models.User;

import java.util.List;

public class UserConnectionAdapter extends RecyclerView.Adapter<UserConnectionAdapter.MyViewHolder> {

    private Context mContext;
    private List<User> mData;

    public UserConnectionAdapter(Context mContext, List<User> mData) {
        this.mContext = mContext;
        this.mData = mData;
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
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView userImg;
        TextView username;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            userImg = itemView.findViewById(R.id.img_existing_user);
            username = itemView.findViewById(R.id.username_existing_user);
        }
    }
}