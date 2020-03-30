package com.example.hieudo.phuocnguyenintern.ui.auth.signUpScreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hieudo.phuocnguyenintern.R;
import com.example.hieudo.phuocnguyenintern.ui.base.BaseFragment;
import com.example.hieudo.phuocnguyenintern.utils.AppUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpMVPFragment extends BaseFragment {

    public SignUpMVPFragment() {
        // Required empty public constructor
    }
    @Override
    protected int getLayout() {
        return R.layout.fragment_sign_up_mvp;
    }

    @BindView(R.id.signUpFrag_tvPolicy)
    TextView tvPolicy;
    @Override
    protected void initView(View view) {
        showActionBar(view,getString(R.string.sign_up));
    }
    @OnClick({R.id.signupFrag_buttonStack, R.id.signUpFrag_tvPolicy})
    void onClick(View view){
        switch (view.getId()){
            case R.id.signUpFrag_tvPolicy:
                tvPolicy.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case R.id.signupFrag_buttonStack:
                FragmentManager fragmentManager = getFragmentManager();
                AppUtils.showDialog(R.layout.dialog_login,fragmentManager, false);
                break;
        }
    }
}
