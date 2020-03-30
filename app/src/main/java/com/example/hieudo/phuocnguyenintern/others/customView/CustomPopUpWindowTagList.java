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
import com.example.hieudo.phuocnguyenintern.others.adapters.PopUpTagListAdapter;
import com.example.hieudo.phuocnguyenintern.others.interfaces.PopUpItemListener;
import com.example.hieudo.phuocnguyenintern.others.models.localModels.SpinnerList;

import java.util.ArrayList;

public class CustomPopUpWindowTagList extends PopupWindow implements PopUpItemListener{

    private ArrayList<SpinnerList> spinnerListArrayList;
    
    private PopUpItemListener popUpItemListener;
    
    public CustomPopUpWindowTagList(Context context, int layoutResource, ArrayList<SpinnerList> spinnerListArrayList, PopUpItemListener popUpItemListener){
        super(context);
        this.spinnerListArrayList = spinnerListArrayList;
        this.popUpItemListener = popUpItemListener;
        onCreateView(context,layoutResource);
    }



    private void onCreateView(Context context, int layoutResource) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(layoutResource,null);
        RecyclerView recyclerView = view.findViewById(R.id.popupTagList_recyclerView);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        PopUpTagListAdapter popUpTagListAdapter = new PopUpTagListAdapter(spinnerListArrayList,popUpItemListener);
        popUpTagListAdapter.setPopUpItemListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(popUpTagListAdapter);
        setContentView(view);
        setFocusable(true);
    }
    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
    }


    @Override
    public void onItemClickListener(int position) {


    }

    @Override
    public void onTagListItemClickListener(int position) {
        dismiss();
        popUpItemListener.onTagListItemClickListener(position);
    }
}
