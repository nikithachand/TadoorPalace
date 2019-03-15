package com.example.tadoorpalace;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tadoorpalace.Model.Users;
import com.example.tadoorpalace.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;


public class LoginActivity extends AppCompatActivity
{

    private EditText LoginNumber, LoginPassword;
    private Button LoginButton;
    private TextView AdminLink, NotAdmin;
    private ProgressDialog loadingBar;


    private String parentDbName = "Users";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        LoginButton = (Button) findViewById(R.id.signin_btn);
        LoginNumber = (EditText) findViewById(R.id.number_input);
        LoginPassword = (EditText) findViewById(R.id.password_input);
        AdminLink = (TextView) findViewById(R.id.admin_panel);
        NotAdmin = (TextView) findViewById(R.id.not_admin_panel);
        loadingBar = new ProgressDialog(this);



        Paper.init(this);


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                LoginUser();

            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginButton.setText("Admin Login");
                LoginNumber.setHint("Restaurant Code");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdmin.setVisibility(View.VISIBLE);
                parentDbName = "BestMealsAdmin";

            }
        });

        NotAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginButton.setText("Login");
                LoginNumber.setHint("Phone Number");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdmin.setVisibility(View.INVISIBLE);
                parentDbName = "Users";


            }
        });


    }



    private void LoginUser()
    {

        String number = LoginNumber.getText().toString();
        String password = LoginPassword.getText().toString();

        if (TextUtils.isEmpty(number))
        {

            Toast.makeText(this, "Phone Number Required", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Password Required", Toast.LENGTH_SHORT).show();
        }

        else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Verifying Credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();


            AllowAccessToAccount(number, password);
        }

    }

    private void AllowAccessToAccount(final String number, final String password)
    {
        if (LoginButton.isClickable())
        {
            Paper.book().write(Prevalent.UserPhoneKey, number);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }


        final DatabaseReference RootRef;

        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child(parentDbName).child(number).exists())
                {
                    Users usersData = dataSnapshot.child(parentDbName).child(number).getValue(Users.class);

                    if (usersData.getPhone().equals(number)) {
                        if (usersData.getPassword().equals(password)) {
                            if (parentDbName.equals("BestMealsAdmin"))
                            {
                                Toast.makeText(LoginActivity.this, "Logged In! ", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                startActivity(intent);
                                LoginPassword.setText("");

                            }

                            else if (parentDbName.equals("Users"))
                            {
                                Toast.makeText(LoginActivity.this, "Logged In!", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);
                                LoginPassword.setText("");
                            }

                        }

                        else {

                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                            LoginNumber.setText("");
                            LoginPassword.setText("");


                        }

                    }

                }

                else
                {
                    Toast.makeText(LoginActivity.this, "Account does not exist", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }

        });

    }

}
