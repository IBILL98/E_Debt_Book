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
import androidx.navigation.fragment.NavHostFragment;

import com.example.e_debt_book.R;
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

public class AddDebtFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // declaring listeners for the date selector
    DatePickerDialog.OnDateSetListener setListener;
    private ImageButton customerSelectButton;
    private TextView selectedCustomerPhone;
    //declating the necessary attributes
    private EditText customerNameInput, customerEmailInput, customerPhoneInput;
    private EditText descriptionInput;
    private EditText dateOfLoanInput;
    private EditText dueDateInput;
    private ImageButton dateOfLoanSelect;
    private ImageButton dueDateSelect;
    private EditText itemNameInput;
    private EditText itemPriceInput;
    private Button addProductButton;
    private ListView productsList;
    private Button addDebtButton;
    private Customer selectedCustomer;
    private TextView loanAmountInput;
    DatePickerDialog.OnDateSetListener setListener2;
    //firebase attributes

    FirebaseDatabase database;
    DatabaseReference reference, customerReference,unregisteredRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_debt, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        //
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
        // setting the reference node at the database
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Debts");

        Market market = (Market) getActivity().getIntent().getSerializableExtra("Market");
        customerReference = FirebaseDatabase.getInstance().getReference().child("Customers");
        unregisteredRef = FirebaseDatabase.getInstance().getReference().child("Unregisterd_Customers");
        // since the Market must know the phone number of the customer, the data of the customer is only called via
        // entering the phone number of the customer.
        // so the personal information of the customer is uneditable by the Market.
        customerNameInput.setClickable(false);
        customerNameInput.setFocusable(false);
        customerNameInput.setFocusableInTouchMode(false);
        customerEmailInput.setClickable(false);
        customerEmailInput.setFocusable(false);
        customerEmailInput.setFocusableInTouchMode(false);
        customerPhoneInput.setClickable(false);
        customerPhoneInput.setFocusable(false);
        customerPhoneInput.setFocusableInTouchMode(false);
        //Market entering the information of the customer...
        customerSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = selectedCustomerPhone.getText().toString();
                //checking if the number is valid, means it's not an arbitrary number
                if (phoneNumber.length()==10) {
                    customerReference.child(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //checking if the number belongs to a customer or not
                            //if yes, then call the info's of the customer with this number.
                            if (snapshot.getValue() != null) {
                                selectedCustomer = snapshot.getValue(Customer.class);
                                assert selectedCustomer != null;
                                selectedCustomer.setPhone(snapshot.getKey());
                                customerNameInput.setText(selectedCustomer.getName() + " " + selectedCustomer.getLastname());
                                customerEmailInput.setText(selectedCustomer.getEmail());
                                customerPhoneInput.setText(selectedCustomer.getPhone());
                            } else {
                                //if the person with this number still hasn't registered... save his info, and the debt, and he'll
                                //create an account later, and the debt will show in his account.
                                unregisteredRef.child(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.getValue() != null) {
                                            selectedCustomer = snapshot.getValue(Customer.class);
                                            assert selectedCustomer != null;
                                            selectedCustomer.setPhone(snapshot.getKey());
                                            customerNameInput.setText(selectedCustomer.getName() + " " + selectedCustomer.getLastname());
                                            customerEmailInput.setText(selectedCustomer.getEmail());
                                            customerPhoneInput.setText(selectedCustomer.getPhone());
                                        } else {
                                            Toast.makeText(getActivity(), "User not found!", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    //if canceled
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else {
                    //if the number isn't a valid phone number.
                    Toast.makeText(getActivity(), "Phone number must consist of 10 digits!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // selecting the date of the loan.
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
        // selecting the due date
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
                String date = dayOfMonth + "/" + month + "/" + year;
                dueDateInput.setText(date);
            }
        };

        ArrayList<String> displayProductsList = new ArrayList<String>();
        ArrayList<Item> itemList = new ArrayList<>();
        final ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, displayProductsList);
        productsList.setAdapter(mAdapter);
        //adding a product's name and price to the loan's details


        // the amount is an addittion of the prices of the products...
        // sooo...
        loanAmountInput.setText("0");
        loanAmountInput.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   Toast.makeText(getActivity(), " Please Enter the items of the debt and the prices", Toast.LENGTH_SHORT).show();
                                               }
                                           }
        );
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = new Item();
                String itemName = itemNameInput.getText().toString();
                String itemPrice = itemPriceInput.getText().toString();
                //updating the value in the field of the total amount of debt = old value + the new item's price
                loanAmountInput.setText(String.valueOf(Integer.valueOf(itemPrice) + Integer.parseInt(String.valueOf(loanAmountInput.getText()))));
                item.setName(itemName);
                item.setPrice(itemPrice);
                Toast.makeText(getActivity(), itemName + " " + itemPrice + " is added!", Toast.LENGTH_LONG).show();
                itemList.add(item);
                // desplaying the new item in the list
                displayProductsList.add(itemName + ", Price: " + itemPrice);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) productsList.getLayoutParams();
                int old_height = productsList.getHeight();
                lp.height = (old_height + 135);
                productsList.setLayoutParams(lp);
                mAdapter.notifyDataSetChanged();
                itemNameInput.setText("");
                itemPriceInput.setText("");
            }
        });
        //adding the debt in the database and saving
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
                //the customer Phone is important

                if (TextUtils.isEmpty(customerPhone)) {
                    loanAmountInput.setError("customer Phone is Required.");
                    return;
                }
                //the amount is important
                if (TextUtils.isEmpty(amount)) {
                    loanAmountInput.setError("Loan amount is Required.");
                    return;
                }
                //the date Of Loan is important
                if (TextUtils.isEmpty(dateOfLoan)) {
                    dateOfLoanInput.setError("Date Of Loan is Required.");
                    return;
                }
                //the due date is important
                if (TextUtils.isEmpty(dueDate)) {
                    dueDateInput.setError("Due date is Required.");
                    return;
                }
                //the amount needs to be as much as the total value of the listed item's value
                float total = 0;
                for (Item i : itemList) total = total + Float.parseFloat(i.getPrice());
                if (total != Float.parseFloat(amount)) return;

                String id = reference.push().getKey();
                Debt debt = new Debt();
                debt.setCustomerPhone(customerPhone);
                debt.setMarketPhone(marketPhone);
                debt.setAmount(amount);
                debt.setDateOfLoan(dateOfLoan);
                debt.setDescription(description);
                debt.setDueDate(dueDate);
                debt.setDebtID(id);
                assert id != null;
                debt.setItemList(itemList);
                reference.child(id).setValue(debt);

                Intent intent =  getActivity().getIntent();
                Bundle b = new Bundle();
                b.putSerializable("Market",market);
                intent.putExtras(b);
                NavHostFragment.findNavController(AddDebtFragment.this).navigate(R.id.action_add_debt_to_nav_market_home);
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
