package com.example.hp.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity {
    EditText email, password;
    Button signInButton;

    FirebaseAuth mAuth;

    ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference table_user = database.getReferenceFromUrl("https://food-hut-777-default-rtdb.firebaseio.com/");

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent in = new Intent(SignIn.this,Home.class);
            startActivity(in);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth=FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar2);
        email = findViewById(R.id.edtemail);
        password = findViewById(R.id.edtPassword);
        signInButton = findViewById(R.id.btnSignIn);
    }



    @Override
    protected void onResume() {
        super.onResume();
        signInButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            String email1,password1,password2;
            email1=String.valueOf(email.getText());
            password1 = String.valueOf(password.getText());

            if(TextUtils.isEmpty(email1)){
                Toast.makeText(SignIn.this,"Enter Email",Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(password1)){
                Toast.makeText(SignIn.this,"Enter Password",Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.signInWithEmailAndPassword(email1, password1)
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(SignIn.this, "Login Successful",
                                        Toast.LENGTH_SHORT).show();
                                Intent in = new Intent(SignIn.this,Home.class);
                                startActivity(in);
                                finish();

                            } else {

                                Toast.makeText(SignIn.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
//            final ProgressDialog progressDialog = new ProgressDialog(SignIn.this);
//            progressDialog.setMessage("Please wait!");
//            progressDialog.show();

//            table_user.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.child(edtem1.getText().toString()).exists()) {
//                        progressDialog.dismiss();
//                        User user = dataSnapshot.child(edtem1.getText().toString()).getValue(User.class);
//                        assert user != null;
//                        user.setPhone(edtem1.getText().toString());
//                        if (user.getPassword().equals(edtPass1.getText().toString())) {
//                            Intent home = new Intent(SignIn.this, Home.class);
//                            Common.currentUser = user;
//                            startActivity(home);
//                            finish();
//                        } else {
//                            Toast.makeText(SignIn.this, "Phone no. or Password is incorrect..", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        progressDialog.dismiss();
//                        Toast.makeText(SignIn.this, "Please Sign Up First..!", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//
//            });
            Intent home = new Intent(SignIn.this, Home.class);
            startActivity(home);
            finish();
        });
    }
}
