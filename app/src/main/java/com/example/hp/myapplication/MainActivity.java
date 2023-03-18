package com.example.hp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(MainActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Button signInButton = findViewById(R.id.btnSignIn);
        Button signUpButton = findViewById(R.id.btnSignUp);
        signInButton.setOnClickListener(l -> {
            startActivity(new Intent(this, SignIn.class));
            finish();
        });
        signUpButton.setOnClickListener(l ->
                startActivity(new Intent(this, SignUp.class))
        );
    }
}