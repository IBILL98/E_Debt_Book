package com.example.e_debt_book.ui.changeEmailMarket;

import android.os.Bundle;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.e_debt_book.R;
import com.example.e_debt_book.ui.changePhoneMarket.ChangePhoneMarketFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.e_debt_book.R;

import static android.content.ContentValues.TAG;


public class ChangeEmailMarketFragment extends Fragment {
    private Button EditMarketEmailDoneButton,EditMarketEmailBackButton;
    private EditText EditMarketEmailNewEmail;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_email_market, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        EditMarketEmailNewEmail = getActivity().findViewById(R.id.EditMarketEmailNewEmail);
        EditMarketEmailDoneButton = getActivity().findViewById(R.id.EditMarketEmailDoneButton);
        EditMarketEmailBackButton = getActivity().findViewById(R.id.EditMarketEmailBackButton);

        EditMarketEmailDoneButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = EditMarketEmailNewEmail.getText().toString().trim();
                if (email.length()==0){
                    EditMarketEmailNewEmail.setError("Please Entere your email adress");
                    return;
                }

                user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "User Email updated..", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "User email address updated.");
                        }else{
                            Toast.makeText(getActivity(), "User Email Didn't Change..", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });



        EditMarketEmailBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ChangeEmailMarketFragment.this).navigate(R.id.action_fragment_change_email_market_to_nav_market_settings);

            }
        });






    }
}