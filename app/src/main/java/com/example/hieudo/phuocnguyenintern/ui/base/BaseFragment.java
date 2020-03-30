package com.example.hieudo.phuocnguyenintern.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hieudo.phuocnguyenintern.others.interfaces.IOnBackPressed;
import com.example.hieudo.phuocnguyenintern.others.models.localModels.User;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment implements IOnBackPressed {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view =  inflater.inflate(getLayout(),container,false);
        ButterKnife.bind(this,view);
        initView(view);
        return view;
    }
    protected abstract void initView(View view);
    protected abstract int getLayout();

    protected void replaceFragment(Fragment fragment){
        if (getActivity() instanceof  BaseActivity){
            ((BaseActivity) getActivity()).replaceFragment(fragment);
        }
    }
    protected void addFragment(Fragment fragment){
        if(getActivity() instanceof BaseActivity){
            ((BaseActivity) getActivity()).addFragment(fragment);
        }
    }
    protected void setUser(User user){
        if (getActivity() instanceof BaseActivity){
            ((BaseActivity)  getActivity()).setUser(user);
        }
    }

    protected User getUser (){
        if (getActivity() instanceof BaseActivity){
            return ((BaseActivity)getActivity()).getUser();
        }
        return ((BaseActivity)getActivity()).getUser();
    }


    protected void showActionBar(View view, String title){
        if(getActivity() instanceof BaseActivity){
            ((BaseActivity) getActivity()).showActionBar(view,title);
        }
    }
    protected void showActionBarHome(View view, String title, String tag){
        if(getActivity() instanceof BaseActivity){
            ((BaseActivity) getActivity()).showActionBarHome(view,title,tag);
        }
    }
    protected void showActionBarSite(View view, String title){
        if(getActivity() instanceof BaseActivity){
            ((BaseActivity) getActivity()).showActionBarSite(view,title);
        }
    }
    protected void isHome(View view){
        if (getActivity() instanceof BaseActivity){
            ((BaseActivity) getActivity()).isHome(view);
        }
    }
    @Override
    public boolean onBackArrowPressed() {
        return false;
    }
}
