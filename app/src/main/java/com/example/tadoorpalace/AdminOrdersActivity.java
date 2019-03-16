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

import com.example.tadoorpalace.Model.AdminOrders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminOrdersActivity extends AppCompatActivity {

    private RecyclerView OrdersList;
    private DatabaseReference orderRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);

        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        OrdersList = findViewById(R.id.Order_list);
        OrdersList.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrders> options =
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                .setQuery(orderRef, AdminOrders.class)
                .build();

        FirebaseRecyclerAdapter<AdminOrders,AdminOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, final int position, @NonNull final AdminOrders model)
                    {
                            holder.CustName.setText("Customer Name: " + model.getName());
                            holder.CustPNum.setText("Phone Number: " + model.getPhone());
                            holder.CustTotalCost.setText("Total Cost: $" + model.getTotalAmount());
                            holder.CustAddress.setText("Address: " + model.getAddress());
                            holder.CustDateTime.setText("Order Date and Time: " + model.getDate() + " " + model.getTime());

                            holder.ViewOrdersBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v)
                                {
                                    String uID = getRef(position).getKey();

                                    Intent intent = new Intent(AdminOrdersActivity.this, AdminCustOrderActivity.class);
                                    intent.putExtra("uid", uID);
                                    startActivity(intent);
                                }
                            });

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v)
                                {
                                    CharSequence options[] = new CharSequence[]
                                            {
                                                    "Yes",
                                                    "No"
                                            };
                                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminOrdersActivity.this);
                                    builder.setTitle("The Order has left the Premises ?");

                                    builder.setItems(options, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int i)
                                        {
                                            if (i == 0)
                                            {
                                                String uID = getRef(position).getKey();

                                                RemoveOrder(uID);
                                            }
                                            else
                                            {
                                                finish();
                                            }
                                        }
                                    });
                                    
                                    builder.show();
                                }
                            });
                    }

                    @NonNull
                    @Override
                    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                        return new AdminOrdersViewHolder(view);
                    }
                };
        OrdersList.setAdapter(adapter);
        adapter.startListening();
    }



    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder
    {
        public TextView CustName, CustPNum, CustTotalCost, CustDateTime, CustAddress;
        public Button ViewOrdersBtn;

        public AdminOrdersViewHolder(@NonNull View itemView)
        {
            super(itemView);

            CustName = itemView.findViewById(R.id.oUserName);
            CustPNum = itemView.findViewById(R.id.oPhoneNum);
            CustTotalCost = itemView.findViewById(R.id.oTotalCost);
            CustDateTime = itemView.findViewById(R.id.Order_Date);
            CustAddress = itemView.findViewById(R.id.Cust_Address);
            ViewOrdersBtn = itemView.findViewById(R.id.view_order);


        }
    }

    private void RemoveOrder(String uID)
    {
        orderRef.child(uID).removeValue();
    }

}
