package com.example.hieudo.phuocnguyenintern.ui.auth.selectLoginScreen;

import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hieudo.phuocnguyenintern.R;
import com.example.hieudo.phuocnguyenintern.others.constants.Constants;
import com.example.hieudo.phuocnguyenintern.ui.auth.loginScreen.LoginScreenFragment;
import com.example.hieudo.phuocnguyenintern.ui.auth.signUpScreen.SignUpMVPFragment;
import com.example.hieudo.phuocnguyenintern.ui.base.BaseFragment;
import com.example.hieudo.phuocnguyenintern.ui.auth.SplashFragment;

import butterknife.BindView;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectLoginMVPFragment extends BaseFragment implements SelectLoginContract.View {
    @BindView(R.id.authFrag_tvStart)
    TextView authFrag_tvStart;
    @BindView(R.id.authFrag_buttons)
    LinearLayout authFrag_buttons;
    @BindView(R.id.authFrag_progressBar)
    ProgressBar authFrag_progressBar;
    private SharedPreferences sharedPreferences;

    private SelectLoginPresenter presenter;

    public SelectLoginMVPFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_select_login2;
    }

    @Override
    protected void initView(View view) {
        sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
        initPresenter();
    }

    private void initPresenter() {
        presenter = new SelectLoginPresenter();
        presenter.setView(this);
    }

    @OnClick({R.id.authFrag_buttonLogin, R.id.authFrag_buttonSignUp, R.id.authFrag_tvStart})
    void onClick(View view){
        switch (view.getId()){
            case R.id.authFrag_buttonLogin:
                addFragment(new LoginScreenFragment());
                break;
            case R.id.authFrag_buttonSignUp:
                addFragment(new SignUpMVPFragment());
                break;
            case R.id.authFrag_tvStart:
                show();
        }
    }
    
    private void show() {
        new Handler().postDelayed(() -> replaceFragment(new SplashFragment()), 1500);
        presenter.toSplashScreen(authFrag_buttons, authFrag_tvStart, sharedPreferences);
    }

    @Override
    public void showProgressBar() {
        authFrag_progressBar.setVisibility(View.VISIBLE);
    }
}
