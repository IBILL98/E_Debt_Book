package com.example.e_debt_book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.e_debt_book.model.Customer;
import com.example.e_debt_book.model.Market;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editCustomer extends AppCompatActivity {

    EditText editCostomerName, editCostomerLastName, editCostomerPassword, editCostomerPhone, editCostomerEmail;
    Button editCostomerBackButton, editCostomerDoneButton;
    ConstraintLayout editCostomerConstraint;
    DatabaseReference mRootRef,conditionRef;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);


        Customer customer = (Customer) getIntent().getSerializableExtra("Customer");

        mRootRef = FirebaseDatabase.getInstance().getReference();

        editCostomerBackButton = findViewById(R.id.editCostomerBackButton);
        editCostomerDoneButton = findViewById(R.id.editCostomerDoneButton);
        editCostomerEmail = findViewById(R.id.editCostomerEmail);
        editCostomerPhone = findViewById(R.id.editCostomerPhone);
        editCostomerPassword = findViewById(R.id.editCostomerPassword);
        editCostomerLastName = findViewById(R.id.editCostomerLastName);
        editCostomerName = findViewById(R.id.editCostomerName);


        String key = customer.getPhone();
        int status = customer.getStatus();

        editCostomerEmail.setText(customer.getEmail());
        editCostomerPhone.setText(customer.getPhone());
        editCostomerLastName.setText(customer.getLastname());
        editCostomerName.setText(customer.getName());
        editCostomerPassword.setText(customer.getStatus());


        editCostomerDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name = editCostomerName.getText().toString().trim();
                String email = editCostomerEmail.getText().toString().trim();
                String password = editCostomerPassword.getText().toString().trim();
                String LastName = editCostomerLastName.getText().toString().trim();
                String phone = editCostomerPhone.getText().toString().trim();


               // you deal with the key and status
                conditionRef = mRootRef.child("Customers");
                Customer customer1 = new Customer(name, phone, LastName,email );
                // conditionRef.child(key).setValue(customer1);
            }
        });




    }
}