package com.example.hieudo.phuocnguyenintern.others.customView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.hieudo.phuocnguyenintern.R;
import com.example.hieudo.phuocnguyenintern.others.models.localModels.User;

import com.example.hieudo.phuocnguyenintern.ui.auth.authScreen.AuthScreenActivity;
import com.example.hieudo.phuocnguyenintern.ui.home.homeScreen.HomeScreenActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomDialogFragment extends DialogFragment {

    //private EditText etEmail;
    @BindView(R.id.loginDialog_etEmail)
    EditText etEmail;
    @BindView(R.id.loginDialog_etPassword)
    EditText etPassword;
    @BindView(R.id.loginDialog_tvLogin)
    TextView tvLogin;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    private DatabaseReference Rootref;

    private int layout;
    private boolean isLogin;
    public CustomDialogFragment(int layout){
        this.layout = layout;
    }

    public CustomDialogFragment(int layout, boolean isLogin) {
        this.layout = layout;
        this.isLogin = isLogin;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(layout,container,false);
        ButterKnife.bind(this,view);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Rootref = firebaseDatabase.getReference();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etEmail.requestFocus();
        Objects.requireNonNull(getDialog().getWindow()).setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        );
        if (isLogin){
            getDialog().setTitle(getString(R.string.log_in_with_stack_exchange));
            tvLogin.setOnClickListener(v -> login(etEmail.getText().toString(),etPassword.getText().toString()));

        } else {
            getDialog().setTitle(getString(R.string.sign_up_using_stack_exchange));
            tvLogin.setOnClickListener(v -> signUp(etEmail.getText().toString(),etPassword.getText().toString()));
        }
    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(getContext(), "Login successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), HomeScreenActivity.class));

                    }
                });
    }

    private void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(getContext(), "Sign up successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), AuthScreenActivity.class));
                        FirebaseUser user = mAuth.getCurrentUser();
                        String uid = user.getUid();
                        User newUser = new User(uid,email,null,email,null);
                        Rootref.child("Users").child(uid).setValue(newUser);
                    } else {
                        Toast.makeText(getContext(), "Sign up fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
