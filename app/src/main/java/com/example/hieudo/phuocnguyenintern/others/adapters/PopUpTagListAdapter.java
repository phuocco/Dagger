package com.example.hieudo.phuocnguyenintern.others.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hieudo.phuocnguyenintern.R;
import com.example.hieudo.phuocnguyenintern.others.interfaces.PopUpItemListener;
import com.example.hieudo.phuocnguyenintern.others.models.localModels.SpinnerList;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopUpTagListAdapter  extends RecyclerView.Adapter<PopUpTagListAdapter.CustomViewHolder> {

    private ArrayList<SpinnerList> spinnerListArrayList;
    public void setPopUpItemListener(PopUpItemListener popUpItemListener) {
        this.popUpItemListener = popUpItemListener;
    }

    private PopUpItemListener popUpItemListener;

    public PopUpTagListAdapter(ArrayList<SpinnerList> spinnerListArrayList, PopUpItemListener popUpItemListener) {
        this.spinnerListArrayList = spinnerListArrayList;
        this.popUpItemListener = popUpItemListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.popup_tag_list_item,parent,false);
        return new CustomViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tagListItem.setText(spinnerListArrayList.get(position).getTitle());
        holder.tagListItem.setOnClickListener(v -> {
            popUpItemListener.onTagListItemClickListener(position);
        });
    }

    @Override
    public int getItemCount() {
        return spinnerListArrayList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.popupTagList_itemName)
        TextView tagListItem;
        CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
