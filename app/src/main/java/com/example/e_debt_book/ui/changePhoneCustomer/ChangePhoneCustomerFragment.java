package com.example.e_debt_book.ui.changePhoneCustomer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.e_debt_book.MainActivity;
import com.example.e_debt_book.MarketMain;
import com.example.e_debt_book.NumberVerification;
import com.example.e_debt_book.R;
import com.example.e_debt_book.addCustomerFromMarket;
import com.example.e_debt_book.debtsDetails;
import com.example.e_debt_book.model.Customer;
import com.example.e_debt_book.model.Market;
import com.example.e_debt_book.ui.changePassword.ChangePasswordFragment;
import com.example.e_debt_book.ui.customerSettings.CustomerSettingsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePhoneCustomerFragment extends Fragment {
    Button EditMarketPhoneDoneButton,EditMarketPhoneBackButton;
    EditText EditMarketPhoneOldPhone,EditMarketPhoneNewPhone;
    DatabaseReference mRootRef, conditionRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_phone_customer, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mRootRef = FirebaseDatabase.getInstance().getReference();
        conditionRef = mRootRef.child("Customers");

        EditMarketPhoneDoneButton = getActivity().findViewById(R.id.EditMarketPhoneDoneButton);
        EditMarketPhoneBackButton = getActivity().findViewById(R.id.EditMarketPhoneBackButton);
        EditMarketPhoneOldPhone = getActivity().findViewById(R.id.EditMarketPhoneOldPhone);
        EditMarketPhoneNewPhone = getActivity().findViewById(R.id.EditMarketPhoneNewPhone);
        Customer customer = (Customer) getActivity().getIntent().getSerializableExtra("Customer");

        EditMarketPhoneDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPhone = EditMarketPhoneOldPhone.getText().toString();
                String newPhone = EditMarketPhoneNewPhone.getText().toString();
                if (currentPhone.length() != 10) {
                    EditMarketPhoneOldPhone.setError("Phone must be 10 Digits");
                    return;
                }
                if (newPhone.length() != 10) {
                    EditMarketPhoneOldPhone.setError("Phone must be 10 Digits");
                    return;
                }
                if (customer.getPhone().equals(currentPhone)) {

                    conditionRef.child(newPhone).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.getValue() != null) {
                                //phone exists as an unregistered customer! , show the user that in a toast
                                Toast.makeText(getActivity(), "This Phone Number is already used", Toast.LENGTH_SHORT).show();
                                System.out.println("This Phone number is already used !!!");
                            } else {
                                //check if the phone's already used as a customer!
                                customer.setStatus(0);
                                customer.setPhone(newPhone);
                                conditionRef.child(newPhone).setValue(customer);
                                conditionRef.child(currentPhone).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getActivity(), "Phone Number Has Changed!", Toast.LENGTH_LONG).show();
                                            Intent i = new Intent(getActivity(), NumberVerification.class);
                                            Bundle b = new Bundle();
                                            b.putSerializable("Customer", customer);
                                            i.putExtras(b);
                                            startActivity(i);
                                            getActivity().finish();
                                        }
                                    }
                                });                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                }else{
                    Toast.makeText(getActivity(), "Please Make Sure Of Your Old Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        EditMarketPhoneBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ChangePhoneCustomerFragment.this).navigate(R.id.action_fragment_change_phone_customer_to_nav_customer_settings);

            }
        });
    }
}