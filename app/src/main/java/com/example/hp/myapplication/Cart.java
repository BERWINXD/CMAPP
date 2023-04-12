package com.example.hp.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private TextView emptyCart;

    private CardView orderCardView;

    private Button orderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cart);
        recyclerView = findViewById(R.id.rec);
        emptyCart = findViewById(R.id.emptyCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        orderCardView = findViewById(R.id.order_card_view);
        orderButton = findViewById(R.id.btnPlaceOrder);

        orderButton.setOnClickListener(listener -> {
            // Code here to handle ordering
        });

        DatabaseReference cartItemsReference = FirebaseDatabase.getInstance().getReference("AddToCart").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));
        cartItemsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cart_modelList.clear();
                if (snapshot.exists()) {
                    emptyCart.setVisibility(View.INVISIBLE);
                    float totalCost = 0f;
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        CartModel item = snapshot1.getValue(CartModel.class);
                        if (item != null) {
                            totalCost += item.getProductPrice() * item.getProductAmount();
                            item.setKey(snapshot1.getKey());
                        }
                        cart_modelList.add(item);
                        orderCardView.setVisibility(View.VISIBLE);
                    }
                    ((TextView) findViewById(R.id.total)).setText(String.valueOf(totalCost));
                } else {
                    emptyCart.setVisibility(View.VISIBLE);
                    orderCardView.setVisibility(View.INVISIBLE);
                }
                recyclerView.setAdapter(new CartAdapter(cart_modelList, cartItemsReference));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}