package com.example.e_debt_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_debt_book.model.Customer;
import com.example.e_debt_book.model.Debt;
import com.example.e_debt_book.model.Item;
import com.example.e_debt_book.model.Market;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
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

    DatePickerDialog.OnDateSetListener setListener;
    DatePickerDialog.OnDateSetListener setListener2;


    FirebaseDatabase database;
    DatabaseReference reference, customerReference,mReference;

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

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Debts");

        Market market = (Market) getIntent().getSerializableExtra("Market");
        customerReference = FirebaseDatabase.getInstance().getReference().child("Customers");

        customerSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = selectedCustomerPhone.getText().toString();
                if (phoneNumber.length()==10) {
                    customerReference.child(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() != null) {
                                selectedCustomer = snapshot.getValue(Customer.class);
                                assert selectedCustomer != null;
                                selectedCustomer.setPhone(snapshot.getKey());
                                customerNameInput.setText(selectedCustomer.getName()+" "+selectedCustomer.getLastname());
                                customerEmailInput.setText(selectedCustomer.getEmail());
                                customerPhoneInput.setText(selectedCustomer.getPhone());
                            } else {
                                Toast.makeText(addNewDebt.this, "User not found!", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else {
                    Toast.makeText(addNewDebt.this, "Phone number must consist of 10 digits!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        dateOfLoanSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(addNewDebt.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth + "/" + month + "/"+ year;
                dateOfLoanInput.setText(date);
            }
        };

        final int year2 = calendar.get(Calendar.YEAR);
        final int month2 = calendar.get(Calendar.MONTH);
        final int day2 = calendar.get(Calendar.DAY_OF_MONTH);
        dueDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(addNewDebt.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener2, year2, month2, day2);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth + "/" + month + "/"+ year;
                dueDateInput.setText(date);
            }
        };


        ArrayList<String> displayProductsList = new ArrayList<String>();
        ArrayList<Item> itemList = new ArrayList<>();
        final ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(addNewDebt.this, android.R.layout.simple_list_item_1, displayProductsList);

        productsList.setAdapter(mAdapter);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = new Item();
                String itemName = itemNameInput.getText().toString();
                String itemPrice = itemPriceInput.getText().toString();
                int itemPrice1 = 0;
                try {
                    itemPrice1 = Integer.parseInt(itemPrice);
                } catch(NumberFormatException nfe) {
                    Toast.makeText(addNewDebt.this, "Please enter the price with only integer format!", Toast.LENGTH_SHORT).show();
                    return;
                }
                item.setName(itemName);
                item.setPrice(itemPrice1);
                Toast.makeText(addNewDebt.this, itemName + " " + itemPrice + " is added!",Toast.LENGTH_LONG).show();
                itemList.add(item);
                displayProductsList.add(itemName+", Price: "+itemPrice);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) productsList.getLayoutParams();
                float old_height = productsList.getHeight();
                lp.height = (int) (old_height + 140);
                productsList.setLayoutParams(lp);
                mAdapter.notifyDataSetChanged();
                itemNameInput.setText("");
                itemPriceInput.setText("");
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
                debt.setDueDate(dueDate);
                debt.setDebtID(id);
                assert id != null;
                reference.child(id).setValue(debt);

                for (int i=0; i<itemList.size(); i++){
                    Item item = itemList.get(i);
                    String itemkey = "item" + i;
                    DatabaseReference itemsref = reference.child(id).child("items").child(itemkey);
                    itemsref.setValue(item);

                }

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

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}