package com.example.hieudo.phuocnguyenintern.others.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hieudo.phuocnguyenintern.R;
import com.example.hieudo.phuocnguyenintern.others.interfaces.RecyclerViewItemClickListener;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.tags.Tag;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.tags.TagItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.CustomViewHolder> {
    Context context;
    private List<TagItem> tagItemList;

    public void setRecyclerViewItemClickListener(RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
    }

    public TagAdapter(List<TagItem> tagItemList) {
        this.tagItemList = tagItemList;
    }

    private RecyclerViewItemClickListener recyclerViewItemClickListener;

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_tag,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tvTag.setText(tagItemList.get(position).getName());
        holder.tvCounter.setText(String.valueOf(tagItemList.get(position).getCount()));
    }

    @Override
    public int getItemCount() {
        return tagItemList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.itemTag_tvTag)
        TextView tvTag;
        @BindView(R.id.itemTag_tvCounter)
        TextView tvCounter;
        CustomViewHolder(@NonNull View itemView) {
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
