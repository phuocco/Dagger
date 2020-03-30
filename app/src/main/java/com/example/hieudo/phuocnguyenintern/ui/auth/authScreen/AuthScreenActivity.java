package com.example.hieudo.phuocnguyenintern.ui.auth.authScreen;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.hieudo.phuocnguyenintern.R;
import com.example.hieudo.phuocnguyenintern.others.constants.Constants;
import com.example.hieudo.phuocnguyenintern.ui.auth.selectLoginScreen.SelectLoginMVPFragment;
import com.example.hieudo.phuocnguyenintern.ui.base.BaseActivity;
import com.example.hieudo.phuocnguyenintern.ui.home.homeScreen.HomeScreenActivity;
import com.facebook.CallbackManager;

public class AuthScreenActivity extends BaseActivity {

    CallbackManager callbackManager;
    SharedPreferences sharedPreferences;
    @Override
    protected int getLayout() {
        return R.layout.activity_auth_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new SelectLoginMVPFragment());
        callbackManager = CallbackManager.Factory.create();
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
        boolean isLogged = sharedPreferences.getBoolean(Constants.IS_LOGGED, false);
        if (isLogged) {
            Intent intent = new Intent(AuthScreenActivity.this, HomeScreenActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                fragment.onActivityResult(requestCode, resultCode, data);
                Log.d("Activity", "ON RESULT CALLED");
            }
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
    }
}
