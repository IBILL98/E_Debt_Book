package com.example.e_debt_book.ui.addDebt;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddDebtViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddDebtViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}