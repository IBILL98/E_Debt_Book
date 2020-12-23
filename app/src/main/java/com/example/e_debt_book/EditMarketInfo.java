package com.example.e_debt_book;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.e_debt_book.model.Market;

public class EditMarketInfo extends AppCompatActivity {

    EditText editInfoName , editInfoEmail , editInfoPhone , editInfoPassword, editInfoIban , editInfoAdress;
    Button marketeditBackButton2, marketeditDoneButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_market_info);

        Market market = (Market) getIntent().getSerializableExtra("Market");


        marketeditBackButton2 = findViewById(R.id.marketeditBackButton2);
        marketeditDoneButton2 = findViewById(R.id.marketeditDoneButton2);
        editInfoName = findViewById(R.id.editInfoName);
        editInfoEmail = findViewById(R.id.editInfoEmail);
        editInfoPhone = findViewById(R.id.editInfoPhone);
        editInfoPassword = findViewById(R.id.editInfoPassword);
        editInfoIban = findViewById(R.id.editInfoIban);
        editInfoAdress = findViewById(R.id.editInfoAdress);






        marketeditBackButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editInfoName.setText(market.getName());
                editInfoEmail.setText(market.getEmail());
                editInfoPhone.setText(market.getPhone());
                editInfoPassword.setText(market.getPassword());
                editInfoIban.setText(market.getIban());
                editInfoAdress.setText(market.getAdress());

            }
        });
        marketeditDoneButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}