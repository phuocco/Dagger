package com.example.hieudo.phuocnguyenintern.ui.auth.loginScreen;

import androidx.fragment.app.Fragment;

import com.facebook.AccessToken;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;

public interface LoginScreenContract {
    interface View {
        void loginSuccess();
        void loginError(String error);
        void showDialog();
        void replaceFragment();
    }
    interface Presenter {
        void handleSignInResult(GoogleSignInResult result);

        void firebaseAuthGoogle(AuthCredential credential);

        void updateUI(FirebaseUser user, String social);

        void loginFacebook(LoginButton btnFacebook, Fragment fragment);
        void signInWithFacebook(AccessToken token);


    }
}
