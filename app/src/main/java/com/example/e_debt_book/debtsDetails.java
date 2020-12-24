package com.example.e_debt_book;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.e_debt_book.model.Debt;

public class debtsDetails extends AppCompatActivity {

    EditText customerInfoDisplay, amountDisplay, dateOFLoanDisplay, dueDateDisplay, descriptionDisplay;
    Button editButton, deleteButton, printButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debts_details);
        customerInfoDisplay = (EditText)findViewById(R.id.customerInfoDisplay);
        amountDisplay = (EditText)findViewById(R.id.amountDisplay);
        dateOFLoanDisplay = (EditText)findViewById(R.id.dateOFLoanDisplay);
        dueDateDisplay = (EditText)findViewById(R.id.dueDateDisplay);
        descriptionDisplay = (EditText)findViewById(R.id.descriptionDisplay);
        editButton = (Button)findViewById(R.id.editButton);
        deleteButton = (Button)findViewById(R.id.deleteButton);
        printButton = (Button)findViewById(R.id.printButton);
        Debt debt = getIntent().getExtras()
    }
}