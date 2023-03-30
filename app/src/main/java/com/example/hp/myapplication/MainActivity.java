package com.example.hp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(MainActivity.this);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent in = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(in);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Button signInButton = findViewById(R.id.btnSignIn);
        Button signUpButton = findViewById(R.id.btnSignUp);
        signInButton.setOnClickListener(l -> {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        });
        signUpButton.setOnClickListener(l ->
                startActivity(new Intent(this, SignUpActivity.class))
        );
    }
}