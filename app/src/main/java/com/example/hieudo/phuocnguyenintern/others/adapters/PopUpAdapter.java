package com.example.hieudo.phuocnguyenintern.others.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hieudo.phuocnguyenintern.R;
import com.example.hieudo.phuocnguyenintern.others.interfaces.PopUpItemListener;
import com.example.hieudo.phuocnguyenintern.others.models.localModels.Header;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopUpAdapter  extends RecyclerView.Adapter<PopUpAdapter.CustomViewHolder> {

    private PopUpItemListener popUpItemListener;
    private ArrayList<Header> headerArrayList;

    public PopUpAdapter(ArrayList<Header> headerArrayList, PopUpItemListener popUpItemListener) {
        this.popUpItemListener = popUpItemListener;
        this.headerArrayList = headerArrayList;
    }

    public void setPopUpItemListener(PopUpItemListener popUpItemListener) {
        this.popUpItemListener = popUpItemListener;
    }

    @NonNull
    @Override
    public PopUpAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.popup_header_item,parent,false);
        return new CustomViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.headerItem.setText(headerArrayList.get(position).getTitle());
        holder.headerItem.setOnClickListener(v ->
                popUpItemListener.onItemClickListener(position));
    }

    @Override
    public int getItemCount() {
        return headerArrayList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.popupHeader_itemName)
        TextView headerItem;
        CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
