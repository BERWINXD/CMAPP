package com.example.hp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hp.myapplication.validators.EmailValidator;
import com.example.hp.myapplication.validators.ValidatorResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    private Button signInButton;
    private EditText emailEditText, passwordEditText;
    private FirebaseAuth firebaseAuth;
    private ProgressBar signInProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        firebaseAuth = FirebaseAuth.getInstance();
        signInProgressBar = findViewById(R.id.progressBar2);

        emailEditText = findViewById(R.id.edtemail);
        passwordEditText = findViewById(R.id.edtPassword);
        signInButton = findViewById(R.id.btnSignIn);
    }

    @Override
    protected void onResume() {
        super.onResume();
        signInButton.setOnClickListener(v -> {
            signInProgressBar.setVisibility(View.VISIBLE);
            signInButton.setEnabled(false);

            String email = this.emailEditText.getText().toString();
            String password = this.passwordEditText.getText().toString();

            ValidatorResult emailValidatorResult = EmailValidator.isEmailValid(email);

            if (!emailValidatorResult.isValid) {
                Toast.makeText(SignInActivity.this, emailValidatorResult.reason, Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 7) {
                Toast.makeText(SignInActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseAuth
                    .signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        signInProgressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignInActivity.this, "Login Successful",
                                    Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(SignInActivity.this, HomeActivity.class);
                            startActivity(in);
                            finish();

                        } else {

                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });
            signInButton.setEnabled(true);
        });
    }
}
