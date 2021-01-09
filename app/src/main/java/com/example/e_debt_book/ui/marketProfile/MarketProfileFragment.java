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

    private EditText editInfoName , editInfoEmail  , editInfoPassword, editInfoIban , editInfoAdress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_market_profile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Market market = (Market) getActivity().getIntent().getSerializableExtra("Market");
////Just getting the Market INfrometion from the previous activity and show it in the profile


        editInfoName = getActivity().findViewById(R.id.editInfoName);
        editInfoEmail = getActivity().findViewById(R.id.editInfoEmail);
        editInfoPassword = getActivity().findViewById(R.id.editInfoPassword);
        editInfoIban = getActivity().findViewById(R.id.editInfoIban);
        editInfoAdress = getActivity().findViewById(R.id.editInfoAdress);


        editInfoName.setText(market.getName());
        editInfoEmail.setText(market.getEmail());
        editInfoIban.setText(market.getIban());
        editInfoAdress.setText(market.getAdress());



        ///make them uneditable as well
        editInfoName.setClickable(false);
        editInfoName.setFocusable(false);
        editInfoName.setFocusableInTouchMode(false);

        editInfoEmail.setClickable(false);
        editInfoEmail.setFocusable(false);
        editInfoEmail.setFocusableInTouchMode(false);

        editInfoPassword.setClickable(false);
        editInfoPassword.setFocusable(false);
        editInfoPassword.setFocusableInTouchMode(false);

        editInfoIban.setClickable(false);
        editInfoIban.setFocusable(false);
        editInfoIban.setFocusableInTouchMode(false);

        editInfoAdress.setClickable(false);
        editInfoAdress.setFocusable(false);
        editInfoAdress.setFocusableInTouchMode(false);


    }

}