package com.example.hp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {
    MaterialEditText email, password, confirmPassword;
    Button signUpButton;

    FirebaseAuth mAuth;

    ProgressBar progressBar;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent in = new Intent(SignUp.this,Home.class);
            startActivity(in);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth=FirebaseAuth.getInstance();
        email = findViewById(R.id.edtemail);
        confirmPassword = findViewById(R.id.edtCPassword);
        password = findViewById(R.id.edtPassword);
        signUpButton = findViewById(R.id.btnSignUp);
        progressBar = findViewById(R.id.progressBar);

        signUpButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            String email1,password1,password2;
            email1=String.valueOf(email.getText());
            password1 = String.valueOf(password.getText());
            password2 = String.valueOf(confirmPassword.getText());
            if(TextUtils.isEmpty(email1)){
                Toast.makeText(SignUp.this,"Enter Email",Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(password1)){
                Toast.makeText(SignUp.this,"Enter Password",Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(password2)){
                Toast.makeText(SignUp.this,"Enter Email",Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.createUserWithEmailAndPassword(email1, password1)
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(SignUp.this, "Account Created",
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(SignUp.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

        });
    }
}
