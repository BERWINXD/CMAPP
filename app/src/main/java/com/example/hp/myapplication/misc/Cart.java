package com.example.hp.myapplication.misc;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hp.myapplication.Common.Common;
import com.example.hp.myapplication.Database.Database;
import com.example.hp.myapplication.Model.Order;
import com.example.hp.myapplication.Model.Request;
import com.example.hp.myapplication.R;
import com.example.hp.myapplication.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    Button btnPlace;


    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = findViewById(R.id.total);
        btnPlace = findViewById(R.id.btnPlaceOrder);

        btnPlace.setOnClickListener(v -> showAlertDialog());
        loadListFood();
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Cart.this);
        builder.setTitle("One More Step!");

        final EditText edtAddress = new EditText(Cart.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtAddress.setLayoutParams(lp);
        builder.setView(edtAddress);
        builder.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        builder.setPositiveButton("YES", (dialog, which) -> {
            Request request = new Request(
                    Common.currentUser.getName(),
                    edtAddress.getText().toString(),
                    txtTotalPrice.getText().toString(),
                    cart
            );
            requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);
            try (Database database = new Database(getBaseContext())) {
                database.cleanCart();
                Toast.makeText(Cart.this, "Your Order has been Confirmed!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(Cart.this, "Operation Failed", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
        builder.show();
    }

    private void loadListFood() {
        try (Database database = new Database(getApplicationContext())) {
            cart = database.getCarts();
            adapter = new CartAdapter(cart, this);
            recyclerView.setAdapter(adapter);

            //total price
            int total = 0;
            for (Order order : cart)
                total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
            Locale locale = new Locale("en", "US");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

            txtTotalPrice.setText(fmt.format(total));

        }
    }
}
