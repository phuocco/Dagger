package com.example.hieudo.phuocnguyenintern.ui.auth;


import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;

import com.example.hieudo.phuocnguyenintern.R;
import com.example.hieudo.phuocnguyenintern.ui.base.BaseFragment;
import com.example.hieudo.phuocnguyenintern.ui.home.homeScreen.HomeScreenActivity;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends BaseFragment {

    @Override
    protected void initView(View view) {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(getContext(), HomeScreenActivity.class);
            startActivity(intent);
            Objects.requireNonNull(getActivity()).finish();
        }, 1500);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_splash;
    }

}
