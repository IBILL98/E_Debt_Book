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
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_debt_book.model.Market;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MarketRegister extends AppCompatActivity {


    //Sign up attributes
    EditText marketRegisterName,marketRegisterEmail,marketRegisterPhone,marketRegisterPassword,marketRegisterIban,marketRegisterAdress;
    Button marketRegisterBackButton,marketRegisterSignUpButtom;
    ProgressBar marketRegisterProgressBar;
    ConstraintLayout market_register;

    //Firebase attributes
    FirebaseAuth fAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mRootRef,conditionRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_register);

        marketRegisterName = findViewById(R.id.marketRegisterName);
        marketRegisterEmail = findViewById(R.id.marketRegisterEmail);
        marketRegisterPhone = findViewById(R.id.marketRegisterPhone);
        marketRegisterPassword = findViewById(R.id.marketRegisterPassword);
        marketRegisterIban = findViewById(R.id.marketRegisterIban);
        marketRegisterAdress = findViewById(R.id.marketRegisterAdress);
        marketRegisterBackButton = findViewById(R.id.marketRegisterBackButton);
        marketRegisterSignUpButtom = findViewById(R.id.marketRegisterSignUpButtom);
        marketRegisterProgressBar = findViewById(R.id.marketRegisterProgressBar);
        market_register = findViewById(R.id.market_register);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        mRootRef = FirebaseDatabase.getInstance().getReference();


        marketRegisterBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MarketRegister.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        marketRegisterSignUpButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = marketRegisterName.getText().toString().trim();
                String email = marketRegisterEmail.getText().toString().trim();
                String phone = marketRegisterPhone.getText().toString().trim();
                String password = marketRegisterPassword.getText().toString().trim();
                String iban = marketRegisterIban.getText().toString().trim();
                String adress = marketRegisterAdress.getText().toString().trim();


                System.out.println(phone);
                /////Check if the Attributes are Empty
                if(TextUtils.isEmpty(email)){
                    marketRegisterEmail.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(name)){
                    marketRegisterName.setError("Name is Required.");
                    return;
                }
                if(TextUtils.isEmpty(iban)){
                    marketRegisterIban.setError("Iban is Required.");
                    return;
                }
                if(TextUtils.isEmpty(adress)){
                    marketRegisterAdress.setError("Address is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    marketRegisterPassword.setError("Password is Required.");
                    return;
                }
                if(password.length() < 7){
                    marketRegisterPassword.setError("Password must be at least 8 characters");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    marketRegisterPhone.setError("Please Enter the Phone Number");
                    return;
                }

                marketRegisterProgressBar.setVisibility(View.VISIBLE);

                //Creating the Market
                conditionRef = mRootRef.child("Markets");
                Market market = new Market(name,password,phone,email,iban,adress,0);

                ///First checking if the phone is used
                conditionRef.child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.getValue() != null) {
                            //phone  exists, show the user that in toast
                            Toast.makeText(MarketRegister.this, "This Phone Number is already used", Toast.LENGTH_SHORT).show();
                            System.out.println("**************************************");
                        } else {
                            //email is available, start the registration
                            //Create the login information
                            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(MarketRegister.this, "User Created ..", Toast.LENGTH_SHORT).show();

                                        //Adding the market to the database
                                        conditionRef.child(phone).setValue(market);

                                        Intent i = new Intent(MarketRegister.this,NumberVerification.class);
                                        Bundle b = new Bundle();
                                        b.putSerializable("Market",market);
                                        i.putExtras(b);
                                        startActivity(i);
                                        finish();

                                    }else{
                                        Toast.makeText(MarketRegister.this, "Error !! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
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