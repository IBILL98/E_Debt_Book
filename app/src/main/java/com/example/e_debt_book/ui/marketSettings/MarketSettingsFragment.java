package com.example.e_debt_book.ui.marketSettings;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.example.e_debt_book.MarketMain1;
import com.example.e_debt_book.NumberVerification;
import com.example.e_debt_book.R;
import com.example.e_debt_book.model.Market;
import com.example.e_debt_book.ui.changePassword.ChangePasswordFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;


public class MarketSettingsFragment extends Fragment {

    LinearLayout market_settings_change_language, market_settings_change_Adress, market_settings_change_name, market_settings_change_password, market_settings_change_email, market_settings_change_Phone;
    TextView choosed_language, actual_adress, actual_name, actual_email, actual_Phone;
    DatabaseReference mRootRef, conditionRef;

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootRef = FirebaseDatabase.getInstance().getReference();
        conditionRef = mRootRef.child("Markets");
        return inflater.inflate(R.layout.fragment_market_setting, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        Market market = (Market) getActivity().getIntent().getSerializableExtra("Market");


        market_settings_change_language = getActivity().findViewById(R.id.market_settings_change_language);
        market_settings_change_Adress = getActivity().findViewById(R.id.market_settings_change_Adress);
        market_settings_change_name = getActivity().findViewById(R.id.market_settings_change_name);
        market_settings_change_password = getActivity().findViewById(R.id.market_settings_change_password);
        market_settings_change_email = getActivity().findViewById(R.id.market_settings_change_email);
        market_settings_change_Phone = getActivity().findViewById(R.id.market_settings_change_Phone);

        choosed_language = getActivity().findViewById(R.id.choosed_language);
        actual_adress = getActivity().findViewById(R.id.actual_adress);
        actual_name = getActivity().findViewById(R.id.actual_name);
        actual_email = getActivity().findViewById(R.id.actual_email);
        actual_Phone = getActivity().findViewById(R.id.actual_Phone);

        actual_adress.setText(market.getAdress());
        actual_name.setText(market.getName());
        actual_email.setText(market.getEmail());
        actual_Phone.setText(market.getPhone());

        mRootRef = FirebaseDatabase.getInstance().getReference();

        market_settings_change_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] listitem = new String[]{"English", "Turkish"};
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(getActivity());
                mbuilder.setTitle("Choose Your Language");
                mbuilder.setIcon(R.drawable.language_icon);
                mbuilder.setSingleChoiceItems(listitem, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        choosed_language.setText(listitem[i]);
                        if (i==0) {
                            setLocale("en");
                        } else {
                            setLocale("tr");
                        }
                        dialogInterface.dismiss();
                        Intent intent = new Intent(getActivity(), MarketMain1.class);
                        Bundle b = new Bundle();
                        b.putSerializable("Market",market);
                        intent.putExtras(b);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
                mbuilder.setNeutralButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog mdialog = mbuilder.create();
                mdialog.show();
            }
        });

        market_settings_change_Adress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.gravity = Gravity.CENTER;
                LinearLayout linearLayout = new LinearLayout(getActivity());

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Change Adress");
                builder.setIcon(R.drawable.place_icon);
                EditText new_adress = new EditText(getActivity());
                new_adress.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setMessage(" Your Actuall Adress is " + actual_adress.getText() + "\n\n Please Entre Your New Adress");
                new_adress.setHint("New Adress");
                builder.setView(linearLayout);
                new_adress.setLayoutParams(layoutParams);
                linearLayout.addView(new_adress);
                linearLayout.setPadding(60, 10, 60, 0);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        actual_adress.setText(new_adress.getText().toString());
                        market.setName(actual_adress.getText().toString());
                        conditionRef.child(market.getPhone()).child("adress").setValue(market.getAdress());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        market_settings_change_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.gravity = Gravity.CENTER;
                LinearLayout linearLayout = new LinearLayout(getActivity());


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Change Name");
                builder.setIcon(R.drawable.account_icon1);
                EditText new_Name = new EditText(getActivity());
                new_Name.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setMessage(" Your Actuall Name is " + actual_name.getText() + "\n\n Please Entre Your New Name");
                new_Name.setHint("New Adress");
                builder.setView(linearLayout);

                new_Name.setLayoutParams(layoutParams);
                linearLayout.addView(new_Name);
                linearLayout.setPadding(60, 10, 60, 0);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        actual_name.setText(new_Name.getText().toString());
                        market.setName(actual_name.getText().toString());
                        conditionRef.child(market.getPhone()).child("name").setValue(market.getName());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        market_settings_change_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_market_fragment, new ChangePasswordFragment());
                fragmentTransaction.commit();
            }
        });
        market_settings_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(MarketSettingsFragment.this).navigate(R.id.action_nav_market_settings_to_fragment_change_password);
            }
        });
        market_settings_change_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(MarketSettingsFragment.this).navigate(R.id.action_nav_market_settings_to_fragment_change_phone_market);
            }
        });
    }
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
}


