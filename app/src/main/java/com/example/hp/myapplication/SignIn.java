package com.example.hp.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hp.myapplication.Common.Common;
import com.example.hp.myapplication.Model.User;
import com.example.hp.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
    EditText edtem1, edtPass1;
    Button btnSignIn;

    private DatabaseReference table_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtem1 = findViewById(R.id.edtemail);
        edtPass1 = findViewById(R.id.edtPassword);

        btnSignIn = findViewById(R.id.btnSignIn);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        table_user = database.getReferenceFromUrl("https://food-hut-777-default-rtdb.firebaseio.com/");
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnSignIn.setOnClickListener(v -> {
            final ProgressDialog progressDialog = new ProgressDialog(SignIn.this);
            progressDialog.setMessage("Please wait!");
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
        });

        // Remove the below code after fixing firebase users login above
        Intent home = new Intent(SignIn.this, Home.class);
        startActivity(home);
        finish();

    }
}
