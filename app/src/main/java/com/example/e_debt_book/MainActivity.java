package com.example.e_debt_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ////Main screen Choice attributes
    private ConstraintLayout mainChoice;
    private Button mainMarketButton;
    private Button mainCostumertButton;


    ////Main screen costumer login attribute
    private ConstraintLayout costumerLogin;



    ////Main screen Market login attribute
    private ConstraintLayout marketLogin;



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


        ////Main screen Market login attribute
        costumerLogin = findViewById(R.id.costumerLogin);
        marketLogin = findViewById(R.id.marketLogin);

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