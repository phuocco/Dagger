package com.example.hieudo.phuocnguyenintern;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.hieudo.phuocnguyenintern.ui.auth.authScreen.AuthScreenActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.maintext)
    void onClick(View view){
        startActivity(new Intent(MainActivity.this, AuthScreenActivity.class));
    }
}
