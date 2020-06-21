package com.example.orderapp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderapp.Interface.ItemClickListener;
import com.example.orderapp.R;

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtItemName;
    public ImageView imageView;

    private ItemClickListener itemClickListener;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        txtItemName = (TextView)itemView.findViewById(R.id.item_name);
        imageView = (ImageView)itemView.findViewById(R.id.item_image);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
