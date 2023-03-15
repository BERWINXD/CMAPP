package com.example.hp.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.hp.myapplication.R;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    private Button signInButton, SignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(MainActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        signInButton = findViewById(R.id.btnSignIn);
        signInButton.setOnClickListener(l -> {
            Intent secondActivityIntent = new Intent(this, SignIn.class);
            startActivity(secondActivityIntent);
        });
    }
}