package com.example.tadoorpalace.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.tadoorpalace.Interface.ItemClickListener;
import com.example.tadoorpalace.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtDishName, txtDishPrice, txtDishQuantity;
    private ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView)
    {
        super(itemView);

        txtDishName = itemView.findViewById(R.id.CartDname);
        txtDishPrice = itemView.findViewById(R.id.CartDPrice);
        txtDishQuantity = itemView.findViewById(R.id.CartDquantity);
    }


    @Override
    public void onClick(View view)
    {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
