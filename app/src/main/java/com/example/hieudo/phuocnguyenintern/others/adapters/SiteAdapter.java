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
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.NavigationSite.SiteItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.CustomViewHolder> {
    private Context context;

    public SiteAdapter(List<SiteItem> siteItemList) {
        this.siteItemList = siteItemList;
    }

    private List<SiteItem> siteItemList;

    public void setRecyclerViewItemClickListener(RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
    }


    private RecyclerViewItemClickListener recyclerViewItemClickListener;

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_site,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Picasso.get().load(siteItemList.get(position).getIconUrl()).into(holder.ivAvatar);
        holder.tvName.setText(siteItemList.get(position).getName());
        holder.tvAudience.setText(siteItemList.get(position).getAudience());
    }

    @Override
    public int getItemCount() {
        return siteItemList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.itemSite_ivAvatar)
        ImageView ivAvatar;
        @BindView(R.id.itemSite_tvName)
        TextView tvName;
        @BindView(R.id.itemSite_tvAudience)
        TextView tvAudience;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            if(recyclerViewItemClickListener != null){
                recyclerViewItemClickListener.onItemClickListener(v,getLayoutPosition());
            }
        }
    }
}
