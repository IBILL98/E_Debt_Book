package com.example.e_debt_book;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.e_debt_book.model.Customer;
import com.example.e_debt_book.model.Market;

import java.util.ArrayList;

public class addNewDebt extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner customerSelectSpinner;
    TextView selectedCustomerField;
    ArrayAdapter adapter;
    Market market;
    ArrayList<Customer> customerList;
    ArrayList<String> displayCustomerList;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_debt);
        customerSelectSpinner = findViewById(R.id.customerSelectSpinner);
        selectedCustomerField = findViewById(R.id.selectedCustomerField);
        selectedCustomerField.setText("Select customer");
        market = (Market) getIntent().getSerializableExtra("market");
        customerList = market.getMyCustomers();
        for (int i=0;i<customerList.size();i++) {
            displayCustomerList.add(customerList.get(i).getName()+" "+customerList.get(i).getLastname()+", "+customerList.get(i).getPhone());
        }
        //adapter = ArrayAdapter.createFromResource(this,displayCustomerList, android.R.layout.simple_spinner_dropdown_item );
        //Not sure if this code is gonna work
        adapter = new ArrayAdapter<String>(this, R.layout.debts_infos_resource, android.R.layout.simple_spinner_item, displayCustomerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerSelectSpinner.setAdapter(adapter);
        customerSelectSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        selectedCustomerField.setText(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}