package com.minhkhoa.taskmanagement.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.minhkhoa.taskmanagement.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    Button btnSignUp, btnLogin;
    EditText edtUsername, edtPassword;
    AnimationDrawable animationDrawable;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //set trans stt bar
        setStatusBarTranslucent(true);
        addControls();
        //events
        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        //set animation
        relativeLayout = findViewById(R.id.relative_layout);
        animationDrawable =(AnimationDrawable)relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(1000);
    }

    private void addControls() {
        btnSignUp = findViewById(R.id.buttonsignup);
        edtUsername = findViewById(R.id.edittextusername);
        edtPassword = findViewById(R.id.editteampassword);
        btnLogin = findViewById(R.id.buttonlogin);
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    protected void onStart() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
//        Log.d("AAA", String.valueOf(user));
        if(user != null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }
        super.onStart();
    }

    @Override
    protected void onResume() {
        animationDrawable.start();
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if(v == btnSignUp){
            signUpUser(edtUsername.getText().toString(),edtPassword.getText().toString());
        }
        if(v == btnLogin){
            Log.d("AAA","go in button login");
            logInUser(edtUsername.getText().toString(),edtPassword.getText().toString());
        }
    }

    private void signUpUser(String email,String password){
        //check empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, getString(R.string.enter_username), Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, getString(R.string.enter_password), Toast.LENGTH_SHORT).show();
        }
        //create user
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("AAA","Sign Up Successfully");
                }
            }
        });
    }

    private void logInUser(String email,String password){
        //check empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, getString(R.string.enter_username), Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, getString(R.string.enter_password), Toast.LENGTH_SHORT).show();
        }
        //login user
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, getString(R.string.login_successfully), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }
            }
        });
    }
}
