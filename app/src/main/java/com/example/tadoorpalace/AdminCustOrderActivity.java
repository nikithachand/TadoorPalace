package com.example.tadoorpalace;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tadoorpalace.Model.Cart;
import com.example.tadoorpalace.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminCustOrderActivity extends AppCompatActivity {

    private RecyclerView dishList;
    RecyclerView.LayoutManager LayoutManager;
    private DatabaseReference CartListRef;

    private String CustID = " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cust_order);

        CustID = getIntent().getStringExtra("uid");

        dishList = findViewById(R.id.Dish_list);
        dishList.setHasFixedSize(true);
        LayoutManager = new LinearLayoutManager(this);
        dishList.setLayoutManager(LayoutManager);



        CartListRef = FirebaseDatabase.getInstance().getReference()
                .child("Cart List").child("Admin View").child(CustID).child("Products");

    }

    @Override
    protected void onStart()
    {

        super.onStart();

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(CartListRef, Cart.class)
                .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model)
            {
                holder.txtDishQuantity.setText( "Quantity: "+ model.getQuantity());
                holder.txtDishPrice.setText("$ "+ model.getPrice());
                holder.txtDishName.setText(model.getDishName());
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_dishes, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };

        dishList.setAdapter(adapter);
        adapter.startListening();

    }
}
