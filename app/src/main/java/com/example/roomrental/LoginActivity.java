package com.example.roomrental;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roomrental.Create;
import com.example.roomrental.Forget;
import com.example.roomrental.R;
import com.example.roomrental.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText email, pwd, edtPhone;
    Button signbtn;
    TextView crtacnt, frgtpwd;

    //Firebase

    private FirebaseAuth auth;
    private Object Users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//            finish();
//        }
        setContentView(R.layout.activity_login);


        email = findViewById(R.id.username);
        pwd = findViewById(R.id.password);
        signbtn = findViewById(R.id.button);
        crtacnt = findViewById(R.id.textView2);
        frgtpwd = findViewById(R.id.textView3);

        auth = FirebaseAuth.getInstance();


        signbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                validate(email.getText().toString(), pwd.getText().toString());
            }
        });
        frgtpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }
        });

        crtacnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    private void validate(String userName, String userPassword) {
        auth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    //Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    checkEmailVerification();
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void checkEmailVerification() {
    FirebaseUser firebaseUser = auth.getInstance().getCurrentUser();
    Boolean emailflag = firebaseUser.isEmailVerified();

    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
}
}
