package com.example.e_debt_book;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MarketRegister extends AppCompatActivity {


    //Sign up attributes
    EditText marketRegisterName,marketRegisterEmail,marketRegisterPhone,marketRegisterPassword,marketRegisterIban,marketRegisterAdress;
    Button marketRegisterBackButton,marketRegisterSignUpButtom;
    ProgressBar marketRegisterProgressBar;


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

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        mRootRef = FirebaseDatabase.getInstance().getReference();


        marketRegisterBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                /////Check if the Atrtributes are Empty
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
                    marketRegisterAdress.setError("Adress is Required.");
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
                    marketRegisterPhone.setError("Please Entere the Phone Number");
                    return;
                }

                marketRegisterProgressBar.setVisibility(View.VISIBLE);







            }
        });


    }
}