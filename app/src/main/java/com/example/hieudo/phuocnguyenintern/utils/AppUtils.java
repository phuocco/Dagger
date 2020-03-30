package com.example.hieudo.phuocnguyenintern.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.example.hieudo.phuocnguyenintern.R;
import com.example.hieudo.phuocnguyenintern.others.customView.CustomDialogFragment;
import com.example.hieudo.phuocnguyenintern.others.customView.CustomPopUpWindow;
import com.example.hieudo.phuocnguyenintern.others.customView.CustomPopUpWindowTagList;
import com.example.hieudo.phuocnguyenintern.others.interfaces.PopUpItemListener;
import com.example.hieudo.phuocnguyenintern.others.models.localModels.Header;
import com.example.hieudo.phuocnguyenintern.others.models.localModels.SpinnerList;

import java.util.ArrayList;

public class AppUtils {
    public static void showPopUp(Context context, View view, int layoutResource, ArrayList<Header> headerArrayList, PopUpItemListener popUpItemListener){
        CustomPopUpWindow customPopUpWindow = new CustomPopUpWindow(context,layoutResource,headerArrayList, popUpItemListener);
        customPopUpWindow.showAsDropDown(view,140,-1);
    }
    public static void showPopUpTagList(Context context, View view, int layoutResource, ArrayList<SpinnerList> spinnerListArrayList, PopUpItemListener popUpItemListener){
        CustomPopUpWindowTagList customPopUpWindowTagList = new CustomPopUpWindowTagList(context,layoutResource, spinnerListArrayList, popUpItemListener);
        customPopUpWindowTagList.showAsDropDown(view,-230,10,Gravity.END);
    }
    public static void showPopUpTagListSite(Context context, View view, int layoutResource, ArrayList<SpinnerList> spinnerListArrayList, PopUpItemListener popUpItemListener){
        CustomPopUpWindowTagList customPopUpWindowTagList = new CustomPopUpWindowTagList(context,layoutResource, spinnerListArrayList, popUpItemListener);
        customPopUpWindowTagList.showAsDropDown(view,-230,10,Gravity.END);
    }

    public static void showDialog(int layout, FragmentManager fragmentManager, boolean isLogin){
        assert fragmentManager != null;
        CustomDialogFragment customDialogFragment;
        if(isLogin){
            customDialogFragment = new CustomDialogFragment(layout,true);
        } else {
            customDialogFragment = new CustomDialogFragment(layout,false);
        }
        customDialogFragment.show(fragmentManager,"Dialog");

    }
    public static String showTime(Context context,int time){
        String text;
        if(time < 60){
            text = context.getResources().getString(R.string.sec_ago,time);
        } else if (time >60 && time < 3600){
            text = context.getResources().getString(R.string.min_ago,time/60);
        } else if (time<7200 && time >3600) {
            text = context.getResources().getString(R.string.hour_ago,time/3600);
        } else if(time<86400 && time >7200){
            text = context.getResources().getString(R.string.hours_ago,time/3600);
        } else {
            text = context.getResources().getString(R.string.day_ago,time/86400);
        }
        return text;
    }
    public static String showVote(Context context,int score){
        String text;
        if(score > 9999){
            text = context.getResources().getString(R.string.votes_over_10000,score/1000);
        } else {
            text = String.valueOf(score);
        }
        return text;
    }
}
