package com.example.e_debt_book.ui.add_customer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;


import com.example.e_debt_book.MarketMain1;
import com.example.e_debt_book.R;
import com.example.e_debt_book.model.Customer;
import com.example.e_debt_book.model.Market;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddCustomerFragment extends Fragment {
    //Sign up attributes
    EditText customerRegisterNamefromMarket, customerRegisterLastNamefromMarket, customerRegisterPhonefromMarket, customerRegisterEmailfromMarket;
    Button customerRegisterBackButtonfromMarket, customerRegisterSignUpButtomfromMarket;
    ProgressBar customerRegisterProgressBarfromMarket;// not functional
    ConstraintLayout customerRegisterfromMarket;


    //Firebase attributes
    FirebaseAuth fAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mRootRef,conditionRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_customer, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        //Sign up attributes
        customerRegisterNamefromMarket = getActivity().findViewById(R.id.customerRegisterNamefromMarket);
        customerRegisterLastNamefromMarket = getActivity().findViewById(R.id.customerRegisterLastNamefromMarket);
        customerRegisterPhonefromMarket = getActivity().findViewById(R.id.customerRegisterPhonefromMarket);
        customerRegisterEmailfromMarket = getActivity().findViewById(R.id.customerRegisterEmailfromMarket);
        customerRegisterBackButtonfromMarket = getActivity().findViewById(R.id.customerRegisterBackButtonfromMarket);
        customerRegisterSignUpButtomfromMarket = getActivity().findViewById(R.id.customerRegisterSignUpButtomfromMarket);
        customerRegisterfromMarket = getActivity().findViewById(R.id.customerRegisterfromMarket);

        fAuth = FirebaseAuth.getInstance();

        Market market = (Market) getActivity().getIntent().getSerializableExtra("Market");


        //ask bilal
        mRootRef = FirebaseDatabase.getInstance().getReference();
        // not functional
        customerRegisterProgressBarfromMarket = getActivity().findViewById(R.id.customerRegisterProgressBarfromMarket);

        customerRegisterBackButtonfromMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MarketMain1.class);
                Bundle b = new Bundle();
                b.putSerializable("Market",market);
                i.putExtras(b);
                startActivity(i);
                getActivity().finish();
            }
        });

        customerRegisterSignUpButtomfromMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = customerRegisterEmailfromMarket.getText().toString().trim();
                String phone = customerRegisterPhonefromMarket.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    customerRegisterEmailfromMarket.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    customerRegisterPhonefromMarket.setError("Please Entere the Phone Number");
                    return;
                }
                customerRegisterProgressBarfromMarket.setVisibility(View.VISIBLE);
                ////Creating the Customer
                conditionRef = mRootRef.child("Unregisterd_Customers");
                Customer cos = new Customer(customerRegisterNamefromMarket.getText().toString(),
                        customerRegisterLastNamefromMarket.getText().toString(),
                        customerRegisterEmailfromMarket.getText().toString(),0);
                //set the phone number as Null cause its already the key of the customer in the database
                //so we dont wanna add it in the database and make it key and cutomer attribute at the same time
                cos.setPhone(null);
                //check : first, if there's an unregistered customer with the same number,
                //second, if there's a registered customer with the same number, if not, then create an unregistered customer.
                conditionRef.child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.getValue() != null) {
                            //phone exists as an unregistered customer! , show the user that in a toast
                            Toast.makeText(getActivity(), "This Phone Number is already used", Toast.LENGTH_SHORT).show();
                            customerRegisterProgressBarfromMarket.setVisibility(View.GONE);
                            System.out.println("phone exists!");
                        } else {
                            //check if the phone's already used as a customer!
                            DatabaseReference databaseReference =mRootRef.child("Customers");
                            databaseReference.child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.getValue()!=null){
                                        //phone exists, show the user that in toast
                                        Toast.makeText(getActivity(), "This Phone Number is already used", Toast.LENGTH_SHORT).show();
                                        System.out.println("phone exists!");
                                        customerRegisterProgressBarfromMarket.setVisibility(View.GONE);

                                    }
                                    else{
                                        //phone is available, start the registeration operation
                                        //Adding the customer to the database
                                        conditionRef.child(customerRegisterPhonefromMarket.getText().toString()).setValue(cos);
                                        Intent i = new Intent(getActivity(),MarketMain1.class);
                                        Bundle b = new Bundle();
                                        b.putSerializable("Market",market);
                                        i.putExtras(b);
                                        startActivity(i);
                                        getActivity().finish();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {}
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
        });

    }
}