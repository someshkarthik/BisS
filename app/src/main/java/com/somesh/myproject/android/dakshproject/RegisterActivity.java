package com.somesh.myproject.android.dakshproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText empid_et,username_et,password_et,emailId_et,phoneno_et;
    FloatingActionButton register_fab;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        empid_et = findViewById(R.id.Register_empid_et);
        username_et = findViewById(R.id.Register_username_et);
        password_et = findViewById(R.id.Register_password_et);
        emailId_et =findViewById(R.id.Register_emailid_et);
        phoneno_et = findViewById(R.id.Register_phoneno_et);
        register_fab = findViewById(R.id.Register_done_fab);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        register_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }
    private void register() {
        final String username = username_et.getText().toString();
        final String email = emailId_et.getText().toString();
        final String phoneno = phoneno_et.getText().toString();
        final String empid = empid_et.getText().toString();
        String password = password_et.getText().toString();
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(phoneno) &&!TextUtils.isEmpty(empid)) {
            progressDialog.setMessage("Registering");
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Toast.makeText(RegisterActivity.this, "inside createuser", Toast.LENGTH_SHORT).show();

                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "inside succesfull", Toast.LENGTH_SHORT).show();
                        String user_id = firebaseAuth.getCurrentUser().getUid();
                        DatabaseReference child_ref = databaseReference.child(user_id);
                        child_ref.child("username").setValue(username);
                        child_ref.child("empid").setValue(empid);
                        child_ref.child("email_et").setValue(email);
                        child_ref.child("phoneno").setValue(phoneno);
                        progressDialog.dismiss();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }


                }
            });
        }
        else
            Toast.makeText(this, "Please complete the form", Toast.LENGTH_SHORT).show();
    }
}
