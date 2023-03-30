package com.example.hp.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hp.myapplication.validators.EmailValidator;
import com.example.hp.myapplication.validators.ValidatorResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUpActivity extends AppCompatActivity {
    private MaterialEditText emailEditText, passwordEditText, confirmPasswordEditText;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.emailSignUp);
        passwordEditText = findViewById(R.id.passwordSignUp);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordSignUp);
        progressBar = findViewById(R.id.progressBarSignUp);
        Button signUpButton = findViewById(R.id.btnSignUp);

        signUpButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);

            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            ValidatorResult emailValidatorResult = EmailValidator.isEmailValid(email);

            if (!emailValidatorResult.isValid) {
                Toast.makeText(SignUpActivity.this, emailValidatorResult.reason, Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() < 7) {
                Toast.makeText(SignUpActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }
            if (confirmPassword.length() < 7) {
                Toast.makeText(SignUpActivity.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                return;
            }
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Account Created",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
