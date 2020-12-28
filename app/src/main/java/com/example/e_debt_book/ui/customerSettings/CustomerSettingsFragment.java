package com.example.e_debt_book.ui.customerSettings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_debt_book.R;

public class CustomerSettingsFragment extends Fragment {

    private CustomersSettingsViewModel customersSettingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        customersSettingsViewModel =
                new ViewModelProvider(this).get(CustomersSettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_setting_customer, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        customersSettingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}