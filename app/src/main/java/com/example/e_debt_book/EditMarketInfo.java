package com.example.e_debt_book;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.e_debt_book.model.Market;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EditMarketInfo extends AppCompatActivity {

    EditText editInfoName , editInfoEmail  , editInfoPassword, editInfoIban , editInfoAdress;
    Button marketeditBackButton2, marketeditDoneButton2;

    DatabaseReference mRootRef,conditionRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_market_info);

        Market market = (Market) getIntent().getSerializableExtra("Market");

        mRootRef = FirebaseDatabase.getInstance().getReference();

        marketeditBackButton2 = findViewById(R.id.marketeditBackButton2);
        marketeditDoneButton2 = findViewById(R.id.marketeditDoneButton2);
        editInfoName = findViewById(R.id.editInfoName);
        editInfoEmail = findViewById(R.id.editInfoEmail);
        editInfoPassword = findViewById(R.id.editInfoPassword);
        editInfoIban = findViewById(R.id.editInfoIban);
        editInfoAdress = findViewById(R.id.editInfoAdress);


        String key = market.getPhone();
        int status = market.getStatus();

        editInfoName.setText(market.getName());
        editInfoEmail.setText(market.getEmail());
        editInfoPassword.setText(market.getPassword());
        editInfoIban.setText(market.getIban());
        editInfoAdress.setText(market.getAdress());

        marketeditDoneButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name = editInfoName.getText().toString().trim();
                String email = editInfoEmail.getText().toString().trim();
                String password = editInfoPassword.getText().toString().trim();
                String iban = editInfoIban.getText().toString().trim();
                String adress = editInfoAdress.getText().toString().trim();

                // every thing down this is line, Alhamza is  bullshitt..

                conditionRef = mRootRef.child("Markets");
                Market market1 = new Market(name, password, key, email, iban, adress, status);
                conditionRef.child(key).setValue(market1);
            }
        });

        marketeditBackButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditMarketInfo.this, MarketMain.class);
                Bundle b = new Bundle();
                b.putSerializable("Market",market);
                i.putExtras(b);
                startActivity(i);
                finish();
            }
        });

    }
}