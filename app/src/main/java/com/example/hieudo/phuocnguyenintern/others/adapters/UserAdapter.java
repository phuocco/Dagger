package com.example.hieudo.phuocnguyenintern.others.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hieudo.phuocnguyenintern.R;
import com.example.hieudo.phuocnguyenintern.others.interfaces.RecyclerViewItemClickListener;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.users.UserItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.CustomViewHolder> {
    Context context;
    private List<UserItem> userItemsList;
    RecyclerViewItemClickListener recyclerViewItemClickListener;

    public UserAdapter(List<UserItem> userItemsList) {
        this.userItemsList = userItemsList;
    }

    public void setRecyclerViewItemClickListener(RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_user,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        UserItem user = userItemsList.get(position);
        holder.tvUsername.setText(user.getDisplayName());
        holder.tvReputation.setText(String.valueOf(user.getReputation()));
        Picasso.get().load(user.getProfileImage()).into(holder.ivAvatar);
        holder.tvGold.setText(String.valueOf(user.getBadgeCounts().getGold()));
        holder.tvSilver.setText(String.valueOf(user.getBadgeCounts().getSilver()));
        holder.tvBronze.setText(String.valueOf(user.getBadgeCounts().getBronze()));

    }

    @Override
    public int getItemCount() {
        return userItemsList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.itemUser_ivAvatar)  ImageView ivAvatar;
        @BindView(R.id.itemUser_tvUsername) TextView tvUsername;
        @BindView(R.id.itemUser_tvReputation) TextView tvReputation;
        @BindView(R.id.itemUser_tvGold) TextView tvGold;
        @BindView(R.id.itemUser_tvSilver) TextView tvSilver;
        @BindView(R.id.itemUser_tvBronze) TextView tvBronze;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(recyclerViewItemClickListener != null){
                recyclerViewItemClickListener.onItemClickListener(v,getLayoutPosition());
            }
        }
    }
}
