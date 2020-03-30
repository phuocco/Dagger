package com.example.hieudo.phuocnguyenintern.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hieudo.phuocnguyenintern.R;
import com.example.hieudo.phuocnguyenintern.others.interfaces.IOnBackPressed;
import com.example.hieudo.phuocnguyenintern.others.models.localModels.User;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    protected Context mContext;
    FragmentManager fragmentManager;

    protected abstract int getLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        mContext = BaseActivity.this;
    }

    public void showActionBar(View view, String title) {
        TextView actionbarTitle =  view.findViewById(R.id.actionBarDetail_tvTitle);
        if(actionbarTitle != null)
            actionbarTitle.setText(title);
        ImageButton backArrow = view.findViewById(R.id.actionBarDetail_backArrow);
        backArrow.setOnClickListener(v -> onBackPressed());
        }

    public void showActionBarHome(View view, String title, String tag) {
        TextView actionbarTitle =  view.findViewById(R.id.actionBarHome_tvSite);
        TextView actionbarTag = view.findViewById(R.id.actionBarHome_tvTag);
        ImageView btnMenu = view.findViewById(R.id.actionBarHome_ivMenu);
        ImageView btnMenuSite = view.findViewById(R.id.actionBarHomeSite_ivMenu);
        ImageView btnAnswer = view.findViewById(R.id.actionBarHome_ivAnswer);
        if(actionbarTitle != null && actionbarTag != null)
        {
            actionbarTitle.setText(title);
            actionbarTag.setText(tag);
        }
        btnAnswer.setOnClickListener(v -> Toast.makeText(mContext, "Answer clicked", Toast.LENGTH_SHORT).show());
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if(drawerLayout != null){
            final View.OnClickListener onClickListener = v -> drawerLayout.openDrawer(Gravity.START);
            btnMenu.setOnClickListener(onClickListener);
            btnMenuSite.setOnClickListener(onClickListener);
        }
    }

    public void showActionBarSite(View view, String title) {
        TextView actionbarTitle =  view.findViewById(R.id.actionBarSite_tvTitle);
        ImageView btnMenu = view.findViewById(R.id.actionBarSite_ivMenu);
        if(actionbarTitle != null)
        {
            actionbarTitle.setText(title);
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if(drawerLayout != null){
            btnMenu.setOnClickListener(v ->
                    drawerLayout.openDrawer(Gravity.START)
            );
        }
    }

        public void isHome(View view){
        ImageButton backArrow = view.findViewById(R.id.actionBarDetail_backArrow);
        backArrow.setVisibility(View.GONE);
        }

    @Override
    public void onBackPressed() {
        Fragment fragment =  getSupportFragmentManager().findFragmentById(R.id.containerFragment);
        if(!(fragment instanceof IOnBackPressed) || !((IOnBackPressed)fragment).onBackArrowPressed()) {
            super.onBackPressed();
        }
    }

    protected void replaceFragment(Fragment fragment){
        fragmentManager =  getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerFragment,fragment,fragment.getClass().getSimpleName()).commit();
    }
    protected void addFragment(Fragment fragment){
        fragmentManager =  getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null).add(R.id.containerFragment,fragment,fragment.getClass().getSimpleName()).commit();
    }

    protected void setUser(User user){
        BaseApplication.instance.setUser(user);
    }

    protected User getUser (){
        return BaseApplication.instance.getUser();
    }
}

