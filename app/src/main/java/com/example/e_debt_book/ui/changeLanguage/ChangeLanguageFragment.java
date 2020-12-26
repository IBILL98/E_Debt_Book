package com.example.e_debt_book.ui.changeLanguage;

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

public class ChangeLanguageFragment extends Fragment {

    private ChangeLanguageViewModel changeLanguageViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        changeLanguageViewModel =
                new ViewModelProvider(this).get(ChangeLanguageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_change_language, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        changeLanguageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}