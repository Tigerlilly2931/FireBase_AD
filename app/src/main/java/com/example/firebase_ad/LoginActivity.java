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

public class LoginActivity extends AppCompatActivity {

    private EditText emailTextView, passwordTextView;
    private Button Btn;
    private ProgressBar progressbar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        // initialising all views through id defined above
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.password);
        Btn = findViewById(R.id.login);
        progressbar = findViewById(R.id.progressBar);

        // Set on Click Listener on Sign-in button
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginUserAccount();
            }
        });

    }

    private void loginUserAccount(){
        progressbar.setVisibility(View.VISIBLE);
        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "Please enter email!", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();

                    progressbar.setVisibility(View.GONE);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Login failed!", Toast.LENGTH_LONG).show();
                    progressbar.setVisibility(View.GONE);
                }
            }
        });
    }
}