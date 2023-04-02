package com.example.hp.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShowCart_Adapter extends RecyclerView.Adapter<ShowCart_Adapter.Viewholder> {
    private Context context;
    private List<ShowCart_Model> showCart_modelList;

    public ShowCart_Adapter(Context context, List<ShowCart_Model> showCart_models)
    {
        this.context=context;
        this.showCart_modelList = showCart_models;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(context).inflate(R.layout.cart_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        ShowCart_Model showCart_model = showCart_modelList.get(position);
        holder.product_name.setText(showCart_model.getProduct_Name());
        holder.price.setText(showCart_model.getPrice());

    }

    @Override
    public int getItemCount() {
        return showCart_modelList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{
        private TextView product_name;
        private TextView price;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.cart_item_name);
            price= itemView.findViewById(R.id.cart_item_price);
        }
    }

}
