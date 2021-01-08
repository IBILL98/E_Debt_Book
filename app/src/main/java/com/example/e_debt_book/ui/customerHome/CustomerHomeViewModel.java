package com.example.e_debt_book.ui.customerHome;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CustomerHomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CustomerHomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Customer home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}