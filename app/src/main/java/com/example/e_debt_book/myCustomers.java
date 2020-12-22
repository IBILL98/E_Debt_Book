package com.example.e_debt_book;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class myCustomers extends AppCompatActivity {
    //sign up attributes
    EditText userInfoDisplay, totalDebtsDisplay, noOfCustomersDisplay;
    ListView listOfDebts;

    //Firebase attributes
    FirebaseAuth kAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mRootRef,conditionRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_customers);

        kAuth = FirebaseAuth.getInstance();

        if(kAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        mRootRef = FirebaseDatabase.getInstance().getReference();

    }
}