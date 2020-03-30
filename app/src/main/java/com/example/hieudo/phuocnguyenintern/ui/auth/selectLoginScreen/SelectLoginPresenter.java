package com.example.hieudo.phuocnguyenintern.ui.auth.selectLoginScreen;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hieudo.phuocnguyenintern.others.constants.Constants;

public class SelectLoginPresenter implements SelectLoginContract.Presenter{

    private SelectLoginContract.View view;

    public void setView(SelectLoginContract.View view) {
        this.view = view;
    }


    @Override
    public void toSplashScreen(LinearLayout authFrag_buttons, TextView authFrag_tvStart, SharedPreferences sharedPreferences) {
        authFrag_buttons.setVisibility(View.GONE);
        authFrag_tvStart.setVisibility(View.GONE);
        SharedPreferences.Editor edit=sharedPreferences.edit();
        edit.putBoolean(Constants.IS_LOGGED,true);
        edit.apply();
        view.showProgressBar();
    }
}
