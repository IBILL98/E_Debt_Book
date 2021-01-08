package com.example.e_debt_book.ui.customerProfile;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.example.e_debt_book.R;
import com.example.e_debt_book.model.Customer;

import com.google.firebase.database.DatabaseReference;


public class CustomerProfileFragment extends Fragment {

    private EditText editCostomerName, editCostomerLastName, editCostomerPassword, editCostomerPhone, editCostomerEmail;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_customer_editprofile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Customer customer = (Customer) getActivity().getIntent().getSerializableExtra("Customer");


        editCostomerEmail = getView().findViewById(R.id.editCostomerEmail);
        editCostomerPhone = getView().findViewById(R.id.editCostomerPhone);
        editCostomerLastName = getView().findViewById(R.id.editCostomerLastName);
        editCostomerName = getView().findViewById(R.id.editCostomerName);

        editCostomerEmail.setText(customer.getEmail());
        editCostomerPhone.setText(customer.getPhone());
        editCostomerLastName.setText(customer.getLastname());
        editCostomerName.setText(customer.getName());





    }
}