package com.example.e_debt_book.ui.customerSettings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.e_debt_book.R;

public class CustomerSettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container ,Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_setting_customer, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();



    }

}