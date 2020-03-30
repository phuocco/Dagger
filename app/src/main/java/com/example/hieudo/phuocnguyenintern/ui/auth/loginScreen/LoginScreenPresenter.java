package com.example.hieudo.phuocnguyenintern.ui.auth.loginScreen;

import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;

import com.example.hieudo.phuocnguyenintern.others.constants.Constants;
import com.example.hieudo.phuocnguyenintern.others.models.localModels.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;

import java.util.Arrays;

public class LoginScreenPresenter implements LoginScreenContract.Presenter {

    private LoginScreenContract.View view;
    private CallbackManager callbackManager;
    private SharedPreferences sharedPreferences;
    private DatabaseReference Rootref;
    private String profile_name;
    private String avatar;
    private String uid, email;
    private FirebaseAuth mAuth;

    public void setView(LoginScreenContract.View view) {
        this.view = view;
    }

    @Override
    public void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            if (acct != null) {
                AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
                firebaseAuthGoogle(credential);
            }
        } else {
            view.loginError("Login Google failed");
        }
    }

    @Override
    public void firebaseAuthGoogle(AuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        uid = user.getUid();
                        profile_name = user.getDisplayName();
                        email = user.getEmail();
                        avatar = user.getPhotoUrl().toString();
                        User newUser = new User(uid, profile_name, null, email, null);
                        Rootref.child("Users").child(uid).setValue(newUser);
                        updateUI(user, "google");
                    } else {
                        view.loginError("Authentication failed");
                    }
                });
    }

    @Override
    public void updateUI(FirebaseUser user, String social) {
        if (user != null) {
            view.replaceFragment();
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(Constants.NAME, profile_name);
            edit.putString(Constants.AVATAR, avatar);
            edit.putBoolean(Constants.IS_LOGGED, true);
            edit.putString(Constants.SOCIAL, social);
            edit.apply();
        } else {
            view.loginError("Authentication failed");
        }
    }

    @Override
    public void loginFacebook(LoginButton btnFacebook, Fragment fragment) {
        btnFacebook.setReadPermissions(Arrays.asList(
                "public_profile", "email"));
        btnFacebook.setFragment(fragment);
        btnFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                signInWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                view.loginError("Login cancel");

            }

            @Override
            public void onError(FacebookException error) {
                view.loginError("Login error: " + error);

            }
        });
    }

    @Override
    public void signInWithFacebook(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        uid = user.getUid();
                        profile_name = user.getDisplayName();
                        email = user.getEmail();
                        view.loginError(email);
                        avatar = user.getPhotoUrl().toString();
                        User newUser = new User(uid, profile_name, null, email, null);
                        Rootref.child("Users").child(uid).setValue(newUser);
                        updateUI(user, "facebook");
                    } else {
                        view.loginError("Authentication failed");
                    }
                });
    }

}
