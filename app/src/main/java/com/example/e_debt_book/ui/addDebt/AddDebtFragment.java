package com.example.e_debt_book.ui.addDebt;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;


import com.example.e_debt_book.R;

import com.example.e_debt_book.model.Customer;
import com.example.e_debt_book.model.Debt;
import com.example.e_debt_book.model.Item;
import com.example.e_debt_book.model.Market;
import com.example.e_debt_book.myCustomers;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddDebtFragment extends Fragment implements AdapterView.OnItemSelectedListener{

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_debt, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();



        customerNameInput = getActivity().findViewById(R.id.customerNameInput);
        customerEmailInput = getActivity().findViewById(R.id.customerEmailInput);
        customerPhoneInput = getActivity().findViewById(R.id.customerPhoneInput);
        loanAmountInput = getActivity().findViewById(R.id.loanAmountInput);
        descriptionInput = getActivity().findViewById(R.id.descriptionInput);
        dateOfLoanInput = getActivity().findViewById(R.id.dateOfLoanInput);
        dueDateInput = getActivity().findViewById(R.id.dueDateInput);
        dateOfLoanSelect = getActivity().findViewById(R.id.dateOfLoanSelect);
        dueDateSelect = getActivity().findViewById(R.id.dueDateSelect);
        itemNameInput = getActivity().findViewById(R.id.itemNameInput);
        itemPriceInput = getActivity().findViewById(R.id.itemPriceInput);
        addProductButton = getActivity().findViewById(R.id.addProductButton);
        productsList = getActivity().findViewById(R.id.productsList);
        customerSelectButton = getActivity().findViewById(R.id.customerSelectButton);
        selectedCustomerPhone = getActivity().findViewById(R.id.selectedCustomerPhone);
        addDebtButton = getActivity().findViewById(R.id.addDebtButton);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Debts");

        Market market = (Market) getActivity().getIntent().getSerializableExtra("Market");
        customerReference = FirebaseDatabase.getInstance().getReference().child("Customers");


        customerNameInput.setClickable(false);
        customerNameInput.setFocusable(false);
        customerNameInput.setFocusableInTouchMode(false);
        customerEmailInput.setClickable(false);
        customerEmailInput.setFocusable(false);
        customerEmailInput.setFocusableInTouchMode(false);
        customerPhoneInput.setClickable(false);
        customerPhoneInput.setFocusable(false);
        customerPhoneInput.setFocusableInTouchMode(false);


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
                                Toast.makeText(getActivity(), "User not found!", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else {
                    Toast.makeText(getActivity(), "Phone number must consist of 10 digits!", Toast.LENGTH_SHORT).show();
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener2, year2, month2, day2);
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
        final ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, displayProductsList);
        productsList.setAdapter(mAdapter);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = new Item();
                String itemName = itemNameInput.getText().toString();
                String itemPrice = itemPriceInput.getText().toString();

                item.setName(itemName);
                item.setPrice(itemPrice);
                Toast.makeText(getActivity(), itemName + " " + itemPrice + " is added!",Toast.LENGTH_LONG).show();
                itemList.add(item);
                displayProductsList.add(itemName+", Price: "+itemPrice);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) productsList.getLayoutParams();
                int old_height = productsList.getHeight();
                lp.height =  (old_height + 135);
                productsList.setLayoutParams(lp);
                System.out.println("////////////////////////////////////////////////////");
                System.out.println(productsList.getHeight());
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
                if(TextUtils.isEmpty(customerPhone)){
                    loanAmountInput.setError("customer Phone is Required.");
                    return;
                }if(TextUtils.isEmpty(amount)){
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
                    DatabaseReference itemsref = reference.child(id).child("itemList").child(itemkey);
                    itemsref.setValue(item);

                }

                Intent intent =  getActivity().getIntent();
                Bundle b = new Bundle();
                b.putSerializable("Market",market);
                intent.putExtras(b);

            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
