package com.example.e_debt_book;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.e_debt_book.model.Customer;
import com.example.e_debt_book.model.Debt;
import com.example.e_debt_book.model.Market;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;

public class debtsDetails extends AppCompatActivity {

    EditText customerInfoDisplay, amountDisplay, descriptionDisplay,dueDateDisplay;
    TextView dateOFLoanDisplay;
    Button editButton, deleteButton, printButton, changeDueDateButton;


    @SuppressLint({"SetTextI18n", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debts_details);
        customerInfoDisplay = (EditText)findViewById(R.id.customerInfoDisplay);
        amountDisplay = (EditText)findViewById(R.id.amountDisplay);
        dateOFLoanDisplay = (TextView) findViewById(R.id.dateOFLoanDisplay);
        dueDateDisplay = (EditText) findViewById(R.id.dueDateDisplay);
        descriptionDisplay = (EditText)findViewById(R.id.descriptionDisplay);
        changeDueDateButton = (Button) findViewById(R.id.changeDueDateButton);
        editButton = (Button)findViewById(R.id.editButton);
        deleteButton = (Button)findViewById(R.id.deleteButton);
        printButton = (Button)findViewById(R.id.printButton);
        Debt debt = (Debt) getIntent().getSerializableExtra("debt");
        Market market = (Market) getIntent().getSerializableExtra("market") ;
        Customer customer = (Customer) getIntent().getSerializableExtra("customer");

        customerInfoDisplay.setText(customer.getName()+" "+customer.getLastname()+", "+customer.getPhone());
        amountDisplay.setText(debt.getAmount());
        dateOFLoanDisplay.setText(debt.getDateOfLoan().toString());
        dueDateDisplay.setText(debt.getDueDate().toString());
        descriptionDisplay.setText(debt.getDescription());

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select A Date");
        MaterialDatePicker materialDatePicker = builder.build();

        changeDueDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(),"Date_Picker");

            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                dueDateDisplay.setText(materialDatePicker.getHeaderText());
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String debtAmount = amountDisplay.getText().toString();
                String dueDate = dueDateDisplay.getText().toString();
                String description = descriptionDisplay.getText().toString();
            }
        });
    }
    /*public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        dueDateDisplay.setText(currentDateString);
    }*/
}