package com.example.e_debt_book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    ////Main screen Choice attributes
    private ConstraintLayout mainChoice;
    private Button mainMarketButton;
    private Button mainCostumertButton;


    ////Main screen costumer login attribute
    private ConstraintLayout costumerLogin;
    private Button costumerBackButton;
    private Button costumertSignUpButton;


    ////Main screen Market login attribute
    private ConstraintLayout marketLogin;
    private Button MarketBackButton;



    private TextView textView;
    private Button who;
    private String text;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRootRef;
    private DatabaseReference conditionRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ////Main screen Choice attributes
        mainChoice = findViewById(R.id.mainChoice);
        mainMarketButton = findViewById(R.id.mainMarketButton);
        mainCostumertButton = findViewById(R.id.mainCostumertButton);

        ////Main screen costumer login attribute
        costumerLogin = findViewById(R.id.costumerLogin);
        costumerBackButton = findViewById(R.id.costumerBackButton);
        costumertSignUpButton = findViewById(R.id.costumertSignUpButton);


        ////Main screen Market login attribute
        costumerLogin = findViewById(R.id.costumerLogin);
        marketLogin = findViewById(R.id.marketLogin);
        MarketBackButton = findViewById(R.id.MarketBackButton);



        mainCostumertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainChoice.setVisibility(View.GONE);
                costumerLogin.setVisibility(View.VISIBLE);
            }
        });


        mainMarketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainChoice.setVisibility(View.GONE);
                marketLogin.setVisibility(View.VISIBLE);
            }
        });


        MarketBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marketLogin.setVisibility(View.GONE);
                mainChoice.setVisibility(View.VISIBLE);
            }
        });

        costumerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                costumerLogin.setVisibility(View.GONE);
                mainChoice.setVisibility(View.VISIBLE);
            }
        });

        costumertSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CostumerRegister.class));
            }
        });
/*
        firebaseDatabase = FirebaseDatabase.getInstance();
        mRootRef = FirebaseDatabase.getInstance().getReference();
        conditionRef = mRootRef.child("Costumers").child("456165").child("first_name");
        who = (Button)findViewById(R.id.who);
        textView = (TextView)findViewById(R.id.textView);*/
    }
/*
    @Override
    protected void onStart() {
        super.onStart();

        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                text = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        who.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(text);
            }
        });
    }*/
}