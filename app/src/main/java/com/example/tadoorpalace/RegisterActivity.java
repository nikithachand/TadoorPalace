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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText InputName, InputNumber, InputPassword;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        CreateAccountButton = (Button) findViewById(R.id.create_act_btn);
        InputName = (EditText) findViewById(R.id.register_username_input);
        InputNumber = (EditText) findViewById(R.id.register_phone_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        loadingBar = new ProgressDialog(this);



        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateAccount();

            }
        });


    }

    private void CreateAccount()
    {
        String name = InputName.getText().toString();
        String number = InputNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(name))
        {

            Toast.makeText(this, "Username Required", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(number))
        {

            Toast.makeText(this, "Phone Number Required", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(password))
        {

            Toast.makeText(this, "Password Required", Toast.LENGTH_SHORT).show();
        }


        else
        {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Checking Credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            validatePhone(name,number,password);

        }

    }

    private void validatePhone(final String name, final String number, final String password)
    {

        final DatabaseReference RootRef;

        RootRef = FirebaseDatabase.getInstance() .getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                if(!(dataSnapshot.child("Users").child(number).exists())) // Checks to see if the Email already exist in the database

                {

                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("name", name);
                    userdataMap.put("number", number);
                    userdataMap.put("password", password);

                    RootRef.child("Users").child(number).updateChildren(userdataMap)

                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)

                                {

                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this, "Account Created!", Toast.LENGTH_SHORT).show();

                                        loadingBar.dismiss();


                                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                        startActivity(intent);

                                    }

                                    else

                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this, "An error has occurred, pLease try again. ", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });

                }

                else

                {
                    Toast.makeText(RegisterActivity.this, "An account with " + number + " Already Exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                    //Throws an error message if the Email already exists in the Database


                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);

                    //Brings the user back to the Main Screen

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)

            {

            }
        });

    }
}

