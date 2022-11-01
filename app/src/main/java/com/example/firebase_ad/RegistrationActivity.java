package com.example.firebase_ad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailTextView, passwordTextView;
    private Button Btn, toLogIn;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.passwd);
        Btn = findViewById(R.id.btnregister);
        toLogIn = findViewById(R.id.btnlogin);
        progressbar = findViewById(R.id.progressbar);

        Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                registerNewUser();
            }
        });

        toLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registerNewUser(){

        String email, password;
        String[] splitter = emailTextView.getText().toString().split("\\s+");
        email = splitter[0];
        password = passwordTextView.getText().toString();
        progressbar.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "Please enter email!", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Beep Boop You're In!", Toast.LENGTH_LONG).show();
                    String[] split = email.split("@");
                    String name = split[0].substring(0,1).toUpperCase() + split[0].substring(1).toLowerCase();
                    progressbar.setVisibility(View.GONE);
                    FirebaseUser user = mAuth.getCurrentUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                    user.updateProfile(profileUpdates);
                    emailTextView.setText("");
                    passwordTextView.setText("");
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "OOOOoooof Registration Failed!" + " Try again Later", Toast.LENGTH_LONG).show();

                    progressbar.setVisibility(View.GONE);
                }
            }
        });
    }
}