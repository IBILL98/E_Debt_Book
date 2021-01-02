package com.example.e_debt_book.ui.add_customer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddCustomerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddCustomerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}