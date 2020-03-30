package com.example.hieudo.phuocnguyenintern.ui.auth.loginScreen;

import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hieudo.phuocnguyenintern.R;
import com.example.hieudo.phuocnguyenintern.others.constants.Constants;
import com.example.hieudo.phuocnguyenintern.ui.auth.SplashFragment;
import com.example.hieudo.phuocnguyenintern.ui.base.BaseFragment;
import com.example.hieudo.phuocnguyenintern.utils.AppUtils;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginScreenFragment extends BaseFragment implements GoogleApiClient.OnConnectionFailedListener, LoginScreenContract.View{
    @BindView(R.id.loginFrag_btnFacebook)
    LoginButton btnFacebook;
    @BindView(R.id.loginFrag_button_google)
    SignInButton signInButton;
    @BindView(R.id.loginFrag_tvTest)
    TextView tvTest;
    private GoogleApiClient googleApiClient;
    private int RC_SIGN_IN = 0;
    private CallbackManager callbackManager;
    private SharedPreferences sharedPreferences;
    private DatabaseReference Rootref;
    private String profile_name;
    private String avatar;
    private String uid, email;
    private FirebaseAuth mAuth;
    private LoginScreenPresenter presenter;


    @Override
    protected int getLayout() {
        return R.layout.fragment_login_screen;
    }
    @Override
    protected void initView(View view) {
        showActionBar(view,getString(R.string.login));
        init();
        initPresenter();

    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Rootref = firebaseDatabase.getReference();
        callbackManager = CallbackManager.Factory.create();
        sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);

    }

    private void initPresenter() {
        presenter = new LoginScreenPresenter();
        presenter.setView(this);
    }

    @OnClick({R.id.loginFrag_llStack, R.id.loginFrag_btnFacebook, R.id.loginFrag_button_google})
    void onClick(View view){
        switch (view.getId()){
            case R.id.loginFrag_llStack:
                showDialog();
                break;
            case R.id.loginFrag_btnFacebook:
                clickLoginFacebook();
                break;
            case R.id.loginFrag_button_google:
                clickSignInGoogle();
                break;
        }
    }

    private void clickLoginFacebook() {
        presenter.loginFacebook(btnFacebook, this);
    }

    private void clickSignInGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (!result.isSuccess())
                Toast.makeText(getContext(), "false", Toast.LENGTH_SHORT).show();
           presenter.handleSignInResult(result);
        }
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(getContext(), "Login success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        AppUtils.showDialog(R.layout.dialog_login,fragmentManager,true);
    }

    @Override
    public void replaceFragment() {
        replaceFragment(new SplashFragment());
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(googleApiClient != null && googleApiClient.isConnected()){
            googleApiClient.stopAutoManage(getActivity());
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
