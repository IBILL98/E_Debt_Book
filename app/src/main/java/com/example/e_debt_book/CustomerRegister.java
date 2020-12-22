package com.example.e_debt_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.e_debt_book.model.Customer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CustomerRegister extends AppCompatActivity {

    //Sign up attributes
    EditText customerRegisterName, customerRegisterLastName, customerRegisterPassword, customerRegisterPhone, customerRegisterEmail;
    Button customerRegisterBackButton, customerRegisterSignUpButtom;
    ProgressBar customerRegisterProgressBar;
    ConstraintLayout customerRegister;


    //Firebase attributes
    FirebaseAuth fAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mRootRef,conditionRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);


        //Sign up attributes
        customerRegisterName = findViewById(R.id.customerRegisterName);
        customerRegisterLastName = findViewById(R.id.customerRegisterLastName);
        customerRegisterPassword = findViewById(R.id.customerRegisterPassword);
        customerRegisterPhone = findViewById(R.id.customerRegisterPhone);
        customerRegisterEmail = findViewById(R.id.customerRegisterEmail);
        customerRegisterBackButton = findViewById(R.id.customerRegisterBackButton);
        customerRegisterSignUpButtom = findViewById(R.id.customerRegisterSignUpButtom);
        customerRegister = findViewById(R.id.customerRegister);

        fAuth = FirebaseAuth.getInstance();

        customerRegisterProgressBar = findViewById(R.id.customerRegisterProgressBar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        mRootRef = FirebaseDatabase.getInstance().getReference();

        customerRegisterBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        customerRegisterSignUpButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = customerRegisterEmail.getText().toString().trim();
                String password = customerRegisterPassword.getText().toString().trim();
                String phone = customerRegisterPhone.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    customerRegisterEmail.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    customerRegisterPassword.setError("Password is Required.");
                    return;
                }
                if(password.length() < 7){
                    customerRegisterPassword.setError("Password must be at least 8 characters");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    customerRegisterPhone.setError("Please Entere the Phone Number");
                    return;
                }

                customerRegisterProgressBar.setVisibility(View.VISIBLE);


                ////Creating the Customer
                conditionRef = mRootRef.child("Customers");
                Customer cos = new Customer(customerRegisterName.getText().toString(),
                                            customerRegisterLastName.getText().toString(),
                                            customerRegisterEmail.getText().toString(),0);
                //set the pone number as Null cause its already the key of the customer in the database
                cos.setPhone(null);

                ///First checking if the Phone number is used
                conditionRef.child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.getValue() != null) {
                            //phone exists, show the user that in toast
                            Toast.makeText(CustomerRegister.this, "This Phone Number is already used", Toast.LENGTH_SHORT).show();
                            System.out.println("**************************************");
                        } else {
                            //phone is available, start the registeration operation
                            //Create the login informations
                            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(CustomerRegister.this, "User Created ..", Toast.LENGTH_SHORT).show();

                                        //Adding the customer to the database
                                        conditionRef.child(customerRegisterPhone.getText().toString()).setValue(cos);
                                        cos.setPhone(customerRegisterPhone.getText().toString());

                                        Intent i = new Intent(CustomerRegister.this,NumberVerification.class);
                                        Bundle b = new Bundle();
                                        b.putSerializable("Customer",cos);
                                        i.putExtras(b);
                                        startActivity(i);
                                        finish();

                                    }else{
                                        Toast.makeText(CustomerRegister.this, "Error !! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            System.out.println("///////////////////////////////////////");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });



            }

        });
    }

}