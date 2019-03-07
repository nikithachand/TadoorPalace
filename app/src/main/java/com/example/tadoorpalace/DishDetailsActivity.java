package com.example.tadoorpalace;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.tadoorpalace.Model.Products;
import com.example.tadoorpalace.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DishDetailsActivity extends AppCompatActivity {

    private Button AddCartbtn;
    private ImageView ddishimg;
    private ElegantNumberButton NumBtn;
    private TextView ddprice, dddescription, ddname;
    private String ProductID = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_details);

        AddCartbtn = (Button) findViewById(R.id.ShoppingCart);
        ddishimg = (ImageView) findViewById(R.id.DDetails_img);
        NumBtn = (ElegantNumberButton) findViewById(R.id.Num_btn);
        ddprice = (TextView) findViewById(R.id.DD_Price);
        dddescription = (TextView) findViewById(R.id.DDish_Name);
        ddname = (TextView) findViewById(R.id.D_Description);


        ProductID = getIntent().getStringExtra("pid");

        getProductDetails(ProductID);

        AddCartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddingtoCart();
            }
        });
    }

    private void AddingtoCart()
    {   String SaveCurrentDate, SaveCurrentTime ;

        Calendar CalforDate = Calendar.getInstance();
        SimpleDateFormat CurrentDate = new SimpleDateFormat("MMM dd, yyyy");
        SaveCurrentDate = CurrentDate.format(CalforDate.getTime());

        SimpleDateFormat CurrentTime = new SimpleDateFormat("HH:mm:ss a");
        SaveCurrentTime = CurrentDate.format(CalforDate.getTime());

        final DatabaseReference CartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", ProductID);
        cartMap.put("DishName", ddname.getText().toString());
        cartMap.put("Price", ddprice.getText().toString());
        cartMap.put("Date", SaveCurrentDate);
        cartMap.put("Time", SaveCurrentTime);
        cartMap.put("Quantity", NumBtn.getNumber());
        cartMap.put("Discount", "");

        CartListRef.child("User View").child(Prevalent.currentOnlineUser.getNumber())
                .child("Products").child(ProductID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            CartListRef.child("Admin View").child(Prevalent.currentOnlineUser.getNumber())
                                    .child("Products").child(ProductID)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if(task.isSuccessful());
                                            Toast.makeText(DishDetailsActivity.this, "Dish Added to Cart", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(DishDetailsActivity.this, HomeActivity.class);
                                            startActivity(intent);

                                        }
                                    });
                        }
                    }
                });


    }

    private void getProductDetails(final String ProductID)
    {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productsRef.child(ProductID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    Products products = dataSnapshot.getValue(Products.class);

                    ddname.setText(products.getPname());
                    ddprice.setText(products.getPrice());
                    dddescription.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(ddishimg);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
