package com.example.tadoorpalace;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.tadoorpalace.Model.Products;
import com.example.tadoorpalace.ViewHolder.DishViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SearchActivity extends AppCompatActivity
{
    private Button btnSearch ;
    private EditText txtSearch;
    private RecyclerView searchList;
    private String searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        txtSearch = findViewById(R.id.SearchDish);
        btnSearch = findViewById(R.id.Searchbtn);
        searchList= findViewById(R.id.Search);
        searchList.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                searchInput = txtSearch.getText().toString();

                onStart();
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(reference.orderByChild("pname").startAt(searchInput),Products.class)
                .build();
        FirebaseRecyclerAdapter<Products, DishViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, DishViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull DishViewHolder holder, int position, @NonNull final Products model)
                    {
                        holder.txtDishTitle.setText(model.getPname());
                        holder.txtDishDescription.setText(model.getDescription());
                        holder.txtDishPrice.setText("Price = $ " + model.getPrice());
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent = new Intent(SearchActivity.this, DishDetailsActivity.class);
                                intent.putExtra("pid", model.getPid());
                                startActivity(intent);

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_layout, parent, false);
                        DishViewHolder holder = new DishViewHolder(view);
                        return holder;
                    }
                };

        searchList.setAdapter(adapter);
        adapter.startListening();

    }
}
