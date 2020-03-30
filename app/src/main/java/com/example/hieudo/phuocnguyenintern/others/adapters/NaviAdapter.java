package com.example.hieudo.phuocnguyenintern.others.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hieudo.phuocnguyenintern.R;
import com.example.hieudo.phuocnguyenintern.others.interfaces.NavItemClickListener;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.NavigationSite.SiteItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NaviAdapter extends RecyclerView.Adapter<NaviAdapter.CustomViewHolder> {
    public NaviAdapter(List<SiteItem> itemList) {
        this.itemList = itemList;
    }

    public void setNavItemClickListener(NavItemClickListener navItemClickListener) {
        this.navItemClickListener = navItemClickListener;
    }

    private NavItemClickListener navItemClickListener;
    private List<SiteItem> itemList;
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_navigation,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tvFeed.setText(itemList.get(position).getName());
        if (itemList.get(position).getIconUrl() != null){
            Picasso.get().load(itemList.get(position).getIconUrl()).into(holder.ivFeed);
        }

    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.nav_tvFeed)
        TextView tvFeed;
        @BindView(R.id.nav_ivFeed)
        ImageView ivFeed;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(navItemClickListener != null){
                navItemClickListener.onNavItemClickListener(getLayoutPosition());
            }
        }
    }
}
