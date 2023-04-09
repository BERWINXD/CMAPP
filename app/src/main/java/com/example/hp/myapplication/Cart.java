package com.example.hp.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.hp.myapplication.Model.CartModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart extends AppCompatActivity {
    private RecyclerView recyclerView;
    private final List<CartModel> cart_modelList = new ArrayList<>();
    private static final String TAG = "Cart";
    private TextView emptyCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cart);
        recyclerView = findViewById(R.id.rec);
        emptyCart = findViewById(R.id.emptyCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        DatabaseReference cartItemsReference = FirebaseDatabase.getInstance().getReference("AddToCart").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));
        cartItemsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cart_modelList.clear();
                if (snapshot.exists()) {
                    emptyCart.setVisibility(View.INVISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        CartModel item = snapshot1.getValue(CartModel.class);
                        if (item != null)
                            item.setKey(snapshot1.getKey());
                        cart_modelList.add(item);
                        if (item != null)
                            Log.d(TAG, "onDataChange: " + item.getProductName() + " : " + item.getProductPrice() + " : " + item.getProductAmount());
                    }
                    recyclerView.setAdapter(new CartAdapter(cart_modelList, cartItemsReference, emptyCart));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}