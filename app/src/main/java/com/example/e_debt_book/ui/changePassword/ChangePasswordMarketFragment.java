package com.example.e_debt_book.ui.changePassword;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class ChangePasswordMarketFragment extends Fragment {
    private Button EditMarketPassBackButton,EditMarketPassDoneButton;
    private EditText EditMarketPassNewPassword,EditMarketPassNewPasswordConfirmation;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_password_market, container, false);
    }



    @Override
    public void onStart() {
        super.onStart();

        EditMarketPassDoneButton = getActivity().findViewById(R.id.EditMarketPassDoneButton);
        EditMarketPassBackButton = getActivity().findViewById(R.id.EditMarketPassBackButton);
        EditMarketPassNewPassword = getActivity().findViewById(R.id.EditMarketPassNewPassword);
        EditMarketPassNewPasswordConfirmation = getActivity().findViewById(R.id.EditMarketPassNewPasswordConfirmation);



        EditMarketPassDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String newPassword = EditMarketPassNewPassword.getText().toString().trim();
                String newPasswordconf = EditMarketPassNewPasswordConfirmation.getText().toString().trim();
                if (newPassword.length()<7){
                    EditMarketPassNewPassword.setError("Password must be longer than 7 letters");
                    return;
                }
                if (newPasswordconf.length()<7){
                    EditMarketPassNewPasswordConfirmation.setError("Password must be longer than 7 letters");
                    return;
                }
                if(newPassword.equals(newPasswordconf)){
                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                NavHostFragment.findNavController(ChangePasswordMarketFragment.this).navigate(R.id.action_fragment_change_password_to_nav_market_settings);
                                Toast.makeText(getActivity(), "User password updated..", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "User password updated.");
                            }
                        }
                    });
                }
            }
        });



        EditMarketPassBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ChangePasswordMarketFragment.this).navigate(R.id.action_fragment_change_password_to_nav_market_settings);

            }
        });
    }
}