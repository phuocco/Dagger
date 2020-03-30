package com.example.hieudo.phuocnguyenintern.others.customView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hieudo.phuocnguyenintern.R;
import com.example.hieudo.phuocnguyenintern.others.adapters.PopUpAdapter;
import com.example.hieudo.phuocnguyenintern.others.interfaces.PopUpItemListener;
import com.example.hieudo.phuocnguyenintern.others.models.localModels.Header;

import java.util.ArrayList;

public class CustomPopUpWindow extends PopupWindow implements PopUpItemListener {

    private ArrayList<Header> headerArrayList;

    private PopUpItemListener popUpItemListener;

    public CustomPopUpWindow(Context context, int layoutResource, ArrayList<Header> headerArrayList, PopUpItemListener popUpItemListener){
        super(context);
        this.headerArrayList = headerArrayList;
        this.popUpItemListener = popUpItemListener;
        onCreateView(context,layoutResource);
    }

    private void onCreateView(Context context, int layoutResource) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(layoutResource, null);
        RecyclerView recyclerView = view.findViewById(R.id.popupHeader_recyclerView);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        PopUpAdapter popUpAdapter = new PopUpAdapter(headerArrayList, popUpItemListener);
        popUpAdapter.setPopUpItemListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(popUpAdapter);

        setContentView(view);
        setFocusable(true);
    }


    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
    }

    @Override
    public void onItemClickListener(int position) {
        dismiss();
        popUpItemListener.onItemClickListener(position);
    }

    @Override
    public void onTagListItemClickListener(int position) {

    }
}
