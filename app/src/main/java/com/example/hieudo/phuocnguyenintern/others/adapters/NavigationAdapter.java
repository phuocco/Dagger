package com.example.hieudo.phuocnguyenintern.others.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hieudo.phuocnguyenintern.R;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.Navigation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.CustomViewHolder> {
    private Context context;
    private ArrayList<Navigation> navigationArrayList = new ArrayList<>();

    public NavigationAdapter(ArrayList<Navigation> navigationArrayList) {
        this.navigationArrayList = navigationArrayList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_navigation,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Navigation navigation = navigationArrayList.get(position);
        holder.navTitle.setText(navigation.getTitle());
    }

    @Override
    public int getItemCount() {
        return navigationArrayList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.nav_tvFeed)
        TextView navTitle;
        CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
