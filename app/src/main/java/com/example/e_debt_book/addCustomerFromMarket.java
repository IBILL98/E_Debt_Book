package com.example.e_debt_book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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


    }
}