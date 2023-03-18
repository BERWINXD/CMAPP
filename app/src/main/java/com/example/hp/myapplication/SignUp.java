package com.example.hp.myapplication;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {
    MaterialEditText email, password, confirmPassword;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.edtemail);
        confirmPassword = findViewById(R.id.edtCPassword);
        password = findViewById(R.id.edtPassword);
        signUpButton = findViewById(R.id.btnSignUp);

        signUpButton.setOnClickListener(v -> {
        });
    }
}
