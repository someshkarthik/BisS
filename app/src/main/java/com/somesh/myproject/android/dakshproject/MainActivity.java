package com.somesh.myproject.android.dakshproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText email_et, password_et;
    Button login,register;
    ProgressDialog progressDialog;
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED)
        {
        data.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
    }}

    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email_et =findViewById(R.id.Login_emailid_et);
        password_et =findViewById(R.id.Login_password_et);
        login=findViewById(R.id.Login_login_bt);
        register=findViewById(R.id.Login_register_bt);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Intent intent = new Intent(MainActivity.this, BillingActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }
            }
        };
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });

    }

    private void login() {
        String emailid = email_et.getText().toString();
        String password = password_et.getText().toString();
        if(!TextUtils.isEmpty(emailid) && !TextUtils.isEmpty(password))
        {
            progressDialog.setMessage("Logging in");
            progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(emailid,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                progressDialog.dismiss();
                else
                    Toast.makeText(MainActivity.this, "error signing in", Toast.LENGTH_SHORT).show();
            }
        });
    }}
}
