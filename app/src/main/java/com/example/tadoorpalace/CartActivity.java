package com.example.tadoorpalace;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tadoorpalace.Model.Cart;
import com.example.tadoorpalace.Prevalent.Prevalent;
import com.example.tadoorpalace.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CartActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private Button txtNextBtn;
    private TextView txtCost, OrderMsg;


    private int TotalCost = 0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.Scartlist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtNextBtn = (Button) findViewById(R.id.Nextbtn);
        txtCost = (TextView) findViewById(R.id.Cart_Total);
        OrderMsg = (TextView) findViewById(R.id.orderPlaced);


        txtNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                txtCost.setText("Total Cost = $" + String.valueOf(TotalCost));

                Intent intent = new Intent(CartActivity.this, ConfirmOrderActivity.class);
                intent.putExtra("Total Cost", String.valueOf(TotalCost));
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    protected void onStart()
    {
        super.onStart();

        CheckOrderState();

        final DatabaseReference CartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(CartListRef.child("User View")
                .child(Prevalent.currentOnlineUser.getNumber()).child("Products"), Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder>adapter
                = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model)
            {
                holder.txtDishQuantity.setText( "Quantity: "+ model.getQuantity());
                holder.txtDishPrice.setText("$ "+ model.getPrice());
                holder.txtDishName.setText(model.getDishName());

                int OneDishPrice = ((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.getQuantity());
                TotalCost = TotalCost + OneDishPrice;


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence option[] = new CharSequence[]
                                {
                                    "Edit",
                                     "Delete",
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart Options: ");


                        builder.setItems(option, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i)
                            {
                                if( i == 0)
                                {
                                    Intent intent = new Intent(CartActivity.this, DishDetailsActivity.class);
                                    intent.putExtra("pid", model.getPid());

                                    startActivity(intent);
                                }
                                if (i == 1)
                                {
                                    CartListRef.child("User View")
                                            .child(Prevalent.currentOnlineUser.getNumber())
                                            .child("Products")
                                            .child(model.getPid())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful())
                                                    {
                                                        Toast.makeText(CartActivity.this, "Dish Removed", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(CartActivity.this, HomeActivity.class);
                                                        startActivity(intent);
                                                    }
                                                }
                                            });
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_dishes, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }

        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void CheckOrderState()
    {
        final DatabaseReference orderRef;
        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getNumber());

        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    String shippingState = dataSnapshot.child("State").getValue().toString();
                    String uName = dataSnapshot.child("Name").getValue().toString();

                    if(shippingState.equals("Delivery in Process"))
                    {
                        txtCost.setText(uName + "Your Food is on its Way");
                        recyclerView.setVisibility(View.GONE);

                        OrderMsg.setVisibility(View.VISIBLE);
                        txtNextBtn.setVisibility(View.GONE);
                    }
                    else if (shippingState.equals("Delivery Pending"))
                    {
                        txtCost.setText(uName + "Your Food is on its Way");
                        recyclerView.setVisibility(View.GONE);

                        OrderMsg.setVisibility(View.VISIBLE);
                        txtNextBtn.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}