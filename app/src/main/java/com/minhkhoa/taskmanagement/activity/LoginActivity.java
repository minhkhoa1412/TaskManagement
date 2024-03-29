package com.minhkhoa.taskmanagement.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.minhkhoa.taskmanagement.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    Button  btnLogin;
    TextView txtSignUp;
    EditText edtUsername, edtPassword;
    AnimationDrawable animationDrawable;
    RelativeLayout relativeLayout;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //set trans stt bar
        setStatusBarTranslucent(true);
        addControls();
        dialog = new ProgressDialog(this);
        //events
        txtSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        //set animation
        relativeLayout = findViewById(R.id.relative_layout);
        animationDrawable =(AnimationDrawable)relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(1000);
    }

    private void addControls() {
        edtUsername = findViewById(R.id.edittextusername);
        edtPassword = findViewById(R.id.editteampassword);
        btnLogin = findViewById(R.id.buttonlogin);
        txtSignUp = findViewById(R.id.textviewsignup);
    }

    private void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    protected void onStart() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
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
        if(v == txtSignUp){
            signUpUser(edtUsername.getText().toString(),edtPassword.getText().toString());
        }
        if(v == btnLogin){
            logInUser(edtUsername.getText().toString().trim(),edtPassword.getText().toString().trim());
        }
    }

    private void signUpUser(final String email, String password){
        //check empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, getString(R.string.enter_username), Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, getString(R.string.enter_password), Toast.LENGTH_SHORT).show();
            return;
        }
        dialog.show(this,getString(R.string.please),getString(R.string.regis),true,false);
        //create user
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    dialog.dismiss();
                    Intent intent = new Intent(LoginActivity.this,UpdateInfoActivity.class);
                    Bundle bundle =  new Bundle();
                    bundle.putString("user_email", email);
                    intent.putExtra("bundleuser",bundle);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    private void logInUser(String email,String password){
        //check empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, getString(R.string.enter_username), Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, getString(R.string.enter_password), Toast.LENGTH_SHORT).show();
            return;
        }
        dialog.show(this,getString(R.string.please),getString(R.string.logging),true,false);
        //login user
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, getString(R.string.login_successfully), Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.login_fail), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
