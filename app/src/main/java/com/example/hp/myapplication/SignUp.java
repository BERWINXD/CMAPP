package com.example.hp.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hp.myapplication.Model.User;
import com.example.hp.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;

public class SignUp extends AppCompatActivity {
    MaterialEditText edtem, edtPass, edtCPass;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtem = findViewById(R.id.edtemail);
        edtCPass = findViewById(R.id.edtCPassword);
        edtPass = findViewById(R.id.edtPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("https://food-hut-777-default-rtdb.firebaseio.com");

        btnSignUp.setOnClickListener(v -> {

            final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
            progressDialog.setMessage("Please wait..!");
            progressDialog.show();

            table_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.child(Objects.requireNonNull(edtem.getText()).toString()).exists()) {
                        progressDialog.dismiss();
                        Toast.makeText(SignUp.this, "User already exists!", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        User user = new User(edtem.getText().toString(), Objects.requireNonNull(edtPass.getText()).toString());
                        table_user.child(edtem.getText().toString()).setValue(user);
                        Toast.makeText(SignUp.this, "SignUp successfully! ", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        });
    }
}
