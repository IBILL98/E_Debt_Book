package com.example.e_debt_book.ui.customerEditprofile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CustomerEditProfileViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CustomerEditProfileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Customer Edit Profile fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}