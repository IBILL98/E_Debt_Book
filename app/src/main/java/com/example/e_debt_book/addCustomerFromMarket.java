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

public class addCustomerFromMarket extends AppCompatActivity {
    //Sign up attributes
    EditText customerRegisterNamefromMarket, customerRegisterLastNamefromMarket, customerRegisterPhonefromMarket, customerRegisterEmailfromMarket;
    Button customerRegisterBackButtonfromMarket, customerRegisterSignUpButtomfromMarket;
    ProgressBar customerRegisterProgressBarfromMarket;// not functional
    ConstraintLayout customerRegisterfromMarket;


    //Firebase attributes
    FirebaseAuth fAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mRootRef,conditionRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer_from_market);

        //Sign up attributes
        customerRegisterNamefromMarket = findViewById(R.id.customerRegisterNamefromMarket);
        customerRegisterLastNamefromMarket = findViewById(R.id.customerRegisterLastNamefromMarket);
        customerRegisterPhonefromMarket = findViewById(R.id.customerRegisterPhonefromMarket);
        customerRegisterEmailfromMarket = findViewById(R.id.customerRegisterEmailfromMarket);
        customerRegisterBackButtonfromMarket = findViewById(R.id.customerRegisterBackButtonfromMarket);
        customerRegisterSignUpButtomfromMarket = findViewById(R.id.customerRegisterSignUpButtomfromMarket);
        customerRegisterfromMarket = findViewById(R.id.customerRegisterfromMarket);

        fAuth = FirebaseAuth.getInstance();


        //ask bilal
        mRootRef = FirebaseDatabase.getInstance().getReference();
        // not functional
        customerRegisterProgressBarfromMarket = findViewById(R.id.customerRegisterProgressBarfromMarket);

        customerRegisterBackButtonfromMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        customerRegisterSignUpButtomfromMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = customerRegisterEmailfromMarket.getText().toString().trim();
                String phone = customerRegisterPhonefromMarket.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    customerRegisterEmailfromMarket.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    customerRegisterPhonefromMarket.setError("Please Entere the Phone Number");
                    return;
                }
                customerRegisterProgressBarfromMarket.setVisibility(View.VISIBLE);
                ////Creating the Customer
                conditionRef = mRootRef.child("Customers");
                Customer cos = new Customer(customerRegisterNamefromMarket.getText().toString(),
                        customerRegisterLastNamefromMarket.getText().toString(),
                        customerRegisterEmailfromMarket.getText().toString(),0);
                //set the phone number as Null cause its already the key of the customer in the database
                //so we dont wanna add it in the database and make it key and cutomer attribute at the same time
                cos.setPhone(null);
                ///First checking if the Phone number is used
                conditionRef.child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.getValue() != null) {
                            //phone exists, show the user that in toast
                            Toast.makeText(addCustomerFromMarket.this, "This Phone Number is already used", Toast.LENGTH_SHORT).show();
                            System.out.println("**************************************");
                        } else {
                            //phone is available, start the registeration operation
                            //Create the login informations

                            fAuth.createUserWithEmailAndPassword(email,null).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(addCustomerFromMarket.this, "User Created ..", Toast.LENGTH_SHORT).show();

                                        //Adding the customer to the database
                                        conditionRef.child(customerRegisterPhonefromMarket.getText().toString()).setValue(cos);
                                        cos.setPhone(customerRegisterPhonefromMarket.getText().toString());

                                        Intent i = new Intent(addCustomerFromMarket.this,NumberVerification.class);
                                        Bundle b = new Bundle();
                                        b.putSerializable("Customer",cos);
                                        i.putExtras(b);
                                        startActivity(i);
                                        finish();

                                    }else{
                                        Toast.makeText(addCustomerFromMarket.this, "Error !! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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