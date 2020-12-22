package com.example.e_debt_book;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MarketRegister extends AppCompatActivity {

    EditText costumerRegisterName,costumerRegisterLastName,costumerRegisterPassword,costumerRegisterPhone,costumerRegisterEmail;
    Button costumerRegisterBackButton,costumerRegisterSignUpButtom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_register);

        costumerRegisterName = findViewById(R.id.costumerRegisterName);
        costumerRegisterLastName = findViewById(R.id.costumerRegisterLastName);
        costumerRegisterPassword = findViewById(R.id.costumerRegisterPassword);
        costumerRegisterPhone = findViewById(R.id.costumerRegisterPhone);
        costumerRegisterEmail = findViewById(R.id.costumerRegisterEmail);
        costumerRegisterBackButton = findViewById(R.id.costumerRegisterBackButton);
        costumerRegisterSignUpButtom = findViewById(R.id.costumerRegisterSignUpButtom);




    }
}