package com.example.e_debt_book.ui.debtInfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DebtInfoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DebtInfoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}