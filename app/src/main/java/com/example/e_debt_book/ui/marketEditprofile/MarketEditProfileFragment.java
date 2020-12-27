package com.example.e_debt_book.ui.marketEditprofile;

import androidx.fragment.app.Fragment;

import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.e_debt_book.MarketMain;
import com.example.e_debt_book.R;
import com.example.e_debt_book.model.Market;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MarketEditProfileFragment extends Fragment {

    EditText editInfoName , editInfoEmail  , editInfoPassword, editInfoIban , editInfoAdress;
    Button marketeditBackButton2, marketeditDoneButton2;

    DatabaseReference mRootRef,conditionRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_market_editprofile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Market market = (Market) getActivity().getIntent().getSerializableExtra("Market");

        mRootRef = FirebaseDatabase.getInstance().getReference();

        marketeditBackButton2 = getActivity().findViewById(R.id.marketeditBackButton2);
        marketeditDoneButton2 = getActivity().findViewById(R.id.marketeditDoneButton2);
        editInfoName = getActivity().findViewById(R.id.editInfoName);
        editInfoEmail = getActivity().findViewById(R.id.editInfoEmail);
        editInfoPassword = getActivity().findViewById(R.id.editInfoPassword);
        editInfoIban = getActivity().findViewById(R.id.editInfoIban);
        editInfoAdress = getActivity().findViewById(R.id.editInfoAdress);


        String key = market.getPhone();
        int status = market.getStatus();

        editInfoName.setText(market.getName());
        editInfoEmail.setText(market.getEmail());
        editInfoPassword.setText(market.getPassword());
        editInfoIban.setText(market.getIban());
        editInfoAdress.setText(market.getAdress());

        marketeditDoneButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name = editInfoName.getText().toString().trim();
                String email = editInfoEmail.getText().toString().trim();
                String password = editInfoPassword.getText().toString().trim();
                String iban = editInfoIban.getText().toString().trim();
                String adress = editInfoAdress.getText().toString().trim();

                // every thing down this is line, Alhamza is  bullshitt..

                conditionRef = mRootRef.child("Markets");
                Market market1 = new Market(name, password, key, email, iban, adress, status);
                conditionRef.child(key).setValue(market1);
            }
        });

        marketeditBackButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MarketMain.class);
                Bundle b = new Bundle();
                b.putSerializable("Market",market);
                i.putExtras(b);
                startActivity(i);
                getActivity().finish();
            }
        });

    }
}