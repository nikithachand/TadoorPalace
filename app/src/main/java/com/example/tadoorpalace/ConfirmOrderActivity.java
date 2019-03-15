package com.example.tadoorpalace;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tadoorpalace.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmOrderActivity extends AppCompatActivity
{

    private EditText ETName, ETPhoneNum, ETAddress;
    private Button ConfirmBtn;

    private String totalAmt = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        totalAmt = getIntent().getStringExtra("Total Cost");


        ConfirmBtn = (Button) findViewById(R.id.confirm_btn);
        ETName = (EditText) findViewById(R.id.DeliveryName);
        ETPhoneNum = (EditText) findViewById(R.id.DeliveryPhN);
        ETAddress = (EditText) findViewById(R.id.DeliveryAddress);

        ConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Check();
            }
        });

    }

    private void Check()
    {
        if(TextUtils.isEmpty(ETName.getText().toString()))
        {
            Toast.makeText(this, "Please Enter Your Full Name", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(ETPhoneNum.getText().toString()))
        {
            Toast.makeText(this, "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(ETAddress.getText().toString()))
        {
            Toast.makeText(this, "Please Enter Your Address", Toast.LENGTH_SHORT).show();
        }else
        {
            ConfirmOrder();
        }
    }

    private void ConfirmOrder()
    {
        final String SaveCurrentDate, SaveCurrentTime ;

        Calendar CalforDate = Calendar.getInstance();
        SimpleDateFormat CurrentDate = new SimpleDateFormat("MMM dd, yyyy");
        SaveCurrentDate = CurrentDate.format(CalforDate.getTime());

        SimpleDateFormat CurrentTime = new SimpleDateFormat("HH:mm:ss a");
        SaveCurrentTime = CurrentDate.format(CalforDate.getTime());

        final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());

        HashMap<String, Object> orderMap = new HashMap<>();
        orderMap.put("TotalAmount", totalAmt);
        orderMap.put("Name", ETName.getText().toString());
        orderMap.put("Phone", ETPhoneNum.getText().toString());
        orderMap.put("Address", ETAddress.getText().toString());
        orderMap.put("Date", SaveCurrentDate);
        orderMap.put("Time", SaveCurrentTime);
        orderMap.put("State", "Delivery Not Processed");

        ordersRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())
                {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Cart List")
                            .child("User View")
                            .child(Prevalent.currentOnlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(ConfirmOrderActivity.this, "Order Placed", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(ConfirmOrderActivity.this, HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();

                                    }
                                }
                            });
                }
            }
        });


    }
}
