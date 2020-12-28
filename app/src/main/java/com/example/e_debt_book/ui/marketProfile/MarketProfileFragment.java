package com.example.e_debt_book.ui.marketProfile;

import androidx.fragment.app.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;


import com.example.e_debt_book.R;
import com.example.e_debt_book.model.Market;



public class MarketProfileFragment extends Fragment {

    EditText editInfoName , editInfoEmail  , editInfoPassword, editInfoIban , editInfoAdress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_market_profile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Market market = (Market) getActivity().getIntent().getSerializableExtra("Market");


        editInfoName = getActivity().findViewById(R.id.editInfoName);
        editInfoEmail = getActivity().findViewById(R.id.editInfoEmail);
        editInfoPassword = getActivity().findViewById(R.id.editInfoPassword);
        editInfoIban = getActivity().findViewById(R.id.editInfoIban);
        editInfoAdress = getActivity().findViewById(R.id.editInfoAdress);


        editInfoName.setText(market.getName());
        editInfoEmail.setText(market.getEmail());
        editInfoPassword.setText(market.getPassword());
        editInfoIban.setText(market.getIban());
        editInfoAdress.setText(market.getAdress());


    }

}