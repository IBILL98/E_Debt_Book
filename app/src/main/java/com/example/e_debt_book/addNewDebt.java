package com.example.e_debt_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_debt_book.model.Customer;
import com.example.e_debt_book.model.Debt;
import com.example.e_debt_book.model.Item;
import com.example.e_debt_book.model.Market;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class addNewDebt extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText customerNameInput, customerEmailInput, customerPhoneInput;
    ImageButton customerSelectButton;
    TextView selectedCustomerPhone;
    EditText loanAmountInput;
    EditText descriptionInput;
    EditText dateOfLoanInput;
    EditText dueDateInput;
    ImageButton dateOfLoanSelect;
    ImageButton dueDateSelect;
    EditText itemNameInput;
    EditText itemPriceInput;
    Button addProductButton;
    ListView productsList;
    Button addDebtButton;
    Customer selectedCustomer;

    ArrayList<Item> itemList;
    String[] itemListString;
    int i=0;
    ArrayAdapter adapter;
    ArrayAdapter<String> mAdapter;
    Market market;
    ArrayList<Customer> customerList;
    ArrayList<String> displayCustomerList;

    DatabaseReference reference, customerReference;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_debt);

        customerNameInput = findViewById(R.id.customerNameInput);
        customerEmailInput = findViewById(R.id.customerEmailInput);
        customerPhoneInput = findViewById(R.id.customerPhoneInput);
        loanAmountInput = findViewById(R.id.loanAmountInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        dateOfLoanInput = findViewById(R.id.dateOfLoanInput);
        dueDateInput = findViewById(R.id.dueDateInput);
        dateOfLoanSelect = findViewById(R.id.dateOfLoanSelect);
        dueDateSelect = findViewById(R.id.dueDateSelect);
        itemNameInput = findViewById(R.id.itemNameInput);
        itemPriceInput = findViewById(R.id.itemPriceInput);
        addProductButton = findViewById(R.id.addProductButton);
        productsList = findViewById(R.id.productsList);
        customerSelectButton = findViewById(R.id.customerSelectButton);
        selectedCustomerPhone = findViewById(R.id.selectedCustomerPhone);
        addDebtButton = findViewById(R.id.addDebtButton);
        selectedCustomerPhone.setText("");

        reference = FirebaseDatabase.getInstance().getReference("Debts");
        customerReference = FirebaseDatabase.getInstance().getReference("Customers");

        itemList = new ArrayList<>();
        //itemListString = new String[100];
        market = (Market) getIntent().getSerializableExtra("Market");
        //customerList = market.getMyCustomers();
        //if (customerList!=null){
            for (int i=0;i<customerList.size();i++) {
                displayCustomerList.add(customerList.get(i).getName()+" "+customerList.get(i).getLastname()+", "+customerList.get(i).getPhone());
            }
        //}

        customerSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = selectedCustomerPhone.getText().toString();
                if (phoneNumber.length()==11) {
                    long phone1;
                    try {
                        phone1 = Integer.parseInt(phoneNumber);
                    } catch (NumberFormatException nfe) {
                        Toast.makeText(addNewDebt.this, "Incorrect phone number format!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    customerReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                selectedCustomer = ds.getValue(Customer.class);
                                if (selectedCustomer != null) {
                                    //find in database
                                    //display
                                } else {
                                    Toast.makeText(addNewDebt.this, "User not found!", Toast.LENGTH_LONG).show();
                                    return;
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }else {
                    Toast.makeText(addNewDebt.this, "Phone number must consist of 11 digits!", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

        //adapter = ArrayAdapter.createFromResource(this,displayCustomerList, android.R.layout.simple_spinner_dropdown_item );
        //Not sure if this code is gonna work
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.layout.simple_spinner_item, displayCustomerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //customerSelectSpinner.setAdapter(adapter);
        //customerSelectSpinner.setOnItemSelectedListener(this);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select A Date");
        MaterialDatePicker materialDatePicker = builder.build();

        dateOfLoanSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(),"Date_Picker");
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                dateOfLoanInput.setText(materialDatePicker.getHeaderText());
            }
        });
        dueDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(),"Date_Picker");
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                dueDateInput.setText(materialDatePicker.getHeaderText());
            }
        });

        mAdapter = new ArrayAdapter<String>(addNewDebt.this, android.R.layout.simple_list_item_1, android.R.id.text1, itemListString);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = itemNameInput.getText().toString();
                String itemPrice = itemPriceInput.getText().toString();
                int itemPrice1 = 0;
                try {
                    itemPrice1 = Integer.parseInt(itemPrice);
                } catch(NumberFormatException nfe) {
                    Toast.makeText(addNewDebt.this, "Please enter the price with only integer format!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Item item = new Item(itemName, itemPrice1);
                itemList.add(item);
                itemListString[i+1]=itemName+" "+itemPrice;
                i++;
                productsList.setAdapter(mAdapter);
            }
        });
        addDebtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customerPhone = selectedCustomer.getPhone();
                String marketPhone = market.getPhone();
                String amount = loanAmountInput.getText().toString();
                String description = descriptionInput.getText().toString();
                String dateOfLoan = dateOfLoanInput.getText().toString();
                String dueDate = dueDateInput.getText().toString();
                List<Item> finalItemList = itemList;
                if(TextUtils.isEmpty(amount)){
                    loanAmountInput.setError("Loan amount is Required.");
                    return;
                }
                if(TextUtils.isEmpty(dateOfLoan)){
                    dateOfLoanInput.setError("Date Of Loan is Required.");
                    return;
                }
                if(TextUtils.isEmpty(dueDate)){
                    dueDateInput.setError("Due date is Required.");
                    return;
                }

                String id = reference.push().getKey();
                Debt debt = new Debt ();
                debt.setCustomerPhone(customerPhone);
                debt.setMarketPhone(marketPhone);
                debt.setAmount(amount);
                debt.setDateOfLoan(dateOfLoan);
                debt.setDescription(description);
                debt.setItemList(finalItemList);
                debt.setDueDate(dueDate);
                debt.setDebtID(id);
                reference.child(id).setValue(debt);

                Intent intent = new Intent(addNewDebt.this,myCustomers.class);
                Bundle b = new Bundle();
                b.putSerializable("Market",market);
                intent.putExtras(b);
                startActivity(intent);
                finish();

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        selectedCustomer = customerList.get(position);
        selectedCustomerField.setText(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}