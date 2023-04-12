package com.example.hp.myapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hp.myapplication.CustomComponents.ElegantNumberButton;
import com.example.hp.myapplication.Model.CartModel;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholder> {
    private final List<CartModel> cart_modelList;
    private final DatabaseReference cartItemsReference;

    public CartAdapter(List<CartModel> cart_models, DatabaseReference cartItemsReference) {
        this.cart_modelList = cart_models;
        this.cartItemsReference = cartItemsReference;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        CartModel cart_model = cart_modelList.get(position);
        holder.getProductName().setText(cart_model.getProductName());
        holder.getPrice().setText(String.valueOf(cart_model.getProductPrice() * cart_model.getProductAmount()));
        holder.getAmount().setNumber(String.valueOf(cart_model.getProductAmount()));
        holder.getAmount().setOnValueChangeListener((view, oldValue, newValue) -> {
            Log.d("", "onBindViewHolder: Value Changed");
            holder.getPrice().setText(String.valueOf(newValue * cart_model.getProductPrice()));
            if (newValue != 0)
                cartItemsReference.child(cart_modelList.get(position).getKey()).child("productAmount").setValue(newValue);
            else
                cartItemsReference.child(cart_modelList.get(position).getKey()).removeValue();
        });
    }

    @Override
    public int getItemCount() {
        return cart_modelList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        private final TextView productName;
        private final TextView price;
        private ElegantNumberButton amount;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.cart_item_name);
            price = itemView.findViewById(R.id.cart_item_price);
            amount = itemView.findViewById(R.id.cart_item_amount);
        }

        public TextView getProductName() {
            return productName;
        }

        public TextView getPrice() {
            return price;
        }

        public ElegantNumberButton getAmount() {
            return amount;
        }
    }
}
