package com.example.tadoorpalace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminCategoryActivity extends AppCompatActivity {

    private Button Soups;
    private Button Appetizers;
    private Button MainCourse;
    private Button Sides;
    private Button Beverages;
    private Button Dessert;

    private Button logOutbtn, CheckOrderbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        logOutbtn = (Button) findViewById(R.id.LogOut_btn);
        CheckOrderbtn = (Button) findViewById(R.id.newOrders_btn);
        Soups = (Button) findViewById(R.id.soup);
        Appetizers = (Button) findViewById(R.id.appetizers);
        MainCourse = (Button) findViewById(R.id.main_course);
        Sides = (Button) findViewById(R.id.sides);
        Beverages = (Button) findViewById(R.id.beverages);
        Dessert = (Button) findViewById(R.id.desserts);

        Soups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AddProductActivity.class);
                intent.putExtra("category","Soups");
                startActivity(intent);
            }
        });

        Appetizers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AddProductActivity.class);
                intent.putExtra("category","Appetizers");
                startActivity(intent);
            }
        });

        MainCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AddProductActivity.class);
                intent.putExtra("category","MainCourse");
                startActivity(intent);
            }
        });

        Sides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AddProductActivity.class);
                intent.putExtra("category","Sides");
                startActivity(intent);
            }
        });

        Beverages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AddProductActivity.class);
                intent.putExtra("category","Beverages");
                startActivity(intent);
            }
        });

        Dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AddProductActivity.class);
                intent.putExtra("category","Dessert");
                startActivity(intent);
            }
        });

        logOutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        CheckOrderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminOrdersActivity.class);
                startActivity(intent);


            }
        });


    }
}
