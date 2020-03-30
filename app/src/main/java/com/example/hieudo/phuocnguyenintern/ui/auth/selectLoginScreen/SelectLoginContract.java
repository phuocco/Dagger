package com.example.hieudo.phuocnguyenintern.ui.auth.selectLoginScreen;

import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public interface SelectLoginContract {
    interface View {
        void showProgressBar();
    }

    interface Presenter {
        void toSplashScreen(LinearLayout authFrag_buttons, TextView authFrag_tvStart, SharedPreferences sharedPreferences);
    }
}
