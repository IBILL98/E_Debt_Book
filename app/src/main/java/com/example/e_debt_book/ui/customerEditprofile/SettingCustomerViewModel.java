package com.example.e_debt_book.ui.customerEditprofile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingCustomerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SettingCustomerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}