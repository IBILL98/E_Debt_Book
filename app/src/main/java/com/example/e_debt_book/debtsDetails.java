package com.example.e_debt_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_debt_book.model.Customer;
import com.example.e_debt_book.model.Debt;
import com.example.e_debt_book.model.Market;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;

public class debtsDetails extends AppCompatActivity {

    EditText amountDisplay, descriptionDisplay,dueDateDisplay;
    TextView customerInfoDisplay, dateOFLoanDisplay;
    Button editButton, deleteButton, printButton, changeDueDateButton;
    ListView listView;

    DatabaseReference reference;
    @SuppressLint({"SetTextI18n", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debts_details);
        customerInfoDisplay = (TextView) findViewById(R.id.customerInfoDisplay);
        amountDisplay = (EditText)findViewById(R.id.amountDisplay);
        dateOFLoanDisplay = (TextView) findViewById(R.id.dateOFLoanDisplay);
        dueDateDisplay = (EditText) findViewById(R.id.dueDateDisplay);
        descriptionDisplay = (EditText)findViewById(R.id.descriptionDisplay);
        changeDueDateButton = (Button) findViewById(R.id.changeDueDateButton);
        editButton = (Button)findViewById(R.id.editButton);
        deleteButton = (Button)findViewById(R.id.deleteButton);
        printButton = (Button)findViewById(R.id.printButton);
        listView = (ListView)findViewById(R.id.productsList);

        Debt debt = (Debt) getIntent().getSerializableExtra("Debt");
        Market market = (Market) getIntent().getSerializableExtra("Market") ;
        Customer customer = (Customer) getIntent().getSerializableExtra("Customer");

        reference = FirebaseDatabase.getInstance().getReference().child("Debts");

        customerInfoDisplay.setText(customer.getName()+" "+customer.getLastname()+", "+customer.getPhone());
        amountDisplay.setText(debt.getAmount());
        dateOFLoanDisplay.setText(debt.getDateOfLoan());
        dueDateDisplay.setText(debt.getDueDate());
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
                debt.setDueDate(dueDate);
                debt.setAmount(debtAmount);
                debt.setDescription(description);
                reference.child(debt.getDebtID()).setValue(debt);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(debt.getDebtID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(debtsDetails.this, "Debt removed successfully!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}