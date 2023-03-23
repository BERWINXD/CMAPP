package com.example.hp.myapplication.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hp.myapplication.Interface.ItemClickListener;
import com.example.hp.myapplication.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtMenuName;
    public ImageView imageView;
    public final CardView cv1;
    public final CardView cv2;
    public final CardView cv3;

    private ItemClickListener itemClickListener;

    public MenuViewHolder(final View itemView) {
        super(itemView);
        cv1 = itemView.findViewById(R.id.item1);
        cv2 = itemView.findViewById(R.id.item2);
        cv3 = itemView.findViewById(R.id.item3);
        cv1.setOnClickListener(v -> {
            txtMenuName = itemView.findViewById(R.id.menu_name);
            imageView = itemView.findViewById(R.id.menu_image);
        });
        cv2.setOnClickListener(v -> {
            txtMenuName = itemView.findViewById(R.id.menu_name1);
            imageView = itemView.findViewById(R.id.menu_image1);
        });
        cv3.setOnClickListener(v -> {
            txtMenuName = itemView.findViewById(R.id.menu_name2);
            imageView = itemView.findViewById(R.id.menu_image2);
        });

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAbsoluteAdapterPosition(), false);
    }
}
