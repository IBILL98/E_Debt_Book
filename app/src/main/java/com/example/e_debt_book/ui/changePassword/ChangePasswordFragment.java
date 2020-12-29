package com.example.e_debt_book.ui.changePassword;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_debt_book.MainActivity;
import com.example.e_debt_book.R;
import com.example.e_debt_book.ui.MarketSettings.MarketSettingsFragment;
import com.example.e_debt_book.ui.marketHome.MarketHomeFragment;
import com.example.e_debt_book.ui.marketProfile.MarketProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;

public class ChangePasswordFragment extends Fragment {
    Button EditCustomerPassBackButton,EditCustomerPassDoneButton;
    EditText EditCustomerPassNewPassword,EditCustomerPassNewPasswordConfirmation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        EditCustomerPassNewPasswordConfirmation = getActivity().findViewById(R.id.EditCustomerPassNewPasswordConfirmation);
        EditCustomerPassNewPassword = getActivity().findViewById(R.id.EditCustomerPassNewPassword);
        EditCustomerPassDoneButton = getActivity().findViewById(R.id.EditCustomerPassDoneButton);

        EditCustomerPassDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String newPassword = EditCustomerPassNewPassword.getText().toString().trim();
                String newPasswordconf = EditCustomerPassNewPasswordConfirmation.getText().toString().trim();
                if (newPassword.length()<7){
                    EditCustomerPassNewPassword.setError("Password must be longer than 7 letters");
                    return;
                }
                if (newPasswordconf.length()<7){
                    EditCustomerPassNewPasswordConfirmation.setError("Password must be longer than 7 letters");
                    return;
                }
                if(newPassword.equals(newPasswordconf)){
                    user.updatePassword(newPassword)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.nav_host_market_fragment, new MarketSettingsFragment());
                                        fragmentTransaction.commit();
                                        Toast.makeText(getActivity(), "User password updated..",
                                                Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "User password updated.");
                                    }
                                }
                            });
                }
            }
        });



        EditCustomerPassBackButton = getActivity().findViewById(R.id.EditCustomerPassBackButton);
        EditCustomerPassBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_market_fragment, new MarketSettingsFragment());
                fragmentTransaction.commit();
            }
        });
    }
}