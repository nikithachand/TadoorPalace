package com.example.tadoorpalace.ViewHolder;

import android.content.ClipData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tadoorpalace.Interface.ItemClickListener;
import com.example.tadoorpalace.R;

public class DishViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener

{
    public TextView txtDishTitle, txtDishDescription, txtDishPrice;
    public ImageView imageView;
    public ItemClickListener listner;

    public DishViewHolder(@NonNull View itemView)
    {

        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.Dish_Img);
        txtDishTitle = (TextView) itemView.findViewById(R.id.Dish_title);
        txtDishDescription = (TextView) itemView.findViewById(R.id.Dish_Discription);
        txtDishPrice = (TextView) itemView.findViewById(R.id.Dish_Price);

    }
    public void setItemClickListner(ItemClickListener listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view)
    {
        listner.onClick(view, getAdapterPosition(),false);
    }
}
