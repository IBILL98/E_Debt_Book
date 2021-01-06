package com.example.e_debt_book.ui.customerSettings;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
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

import com.example.e_debt_book.CustomerMain;
import com.example.e_debt_book.R;
import com.example.e_debt_book.model.Customer;
import com.example.e_debt_book.ui.changePassword.ChangePasswordMarketFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class CustomerSettingsFragment extends Fragment {

    LinearLayout customer_settings_change_language, customer_settings_change_name, customer_settings_change_password, customer_settings_change_email, customer_settings_change_Phone;
    TextView customer_choosed_language, customer_actual_name, customer_actual_email, customer_actual_Phone;
    DatabaseReference mRootRef, conditionRef;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container ,Bundle savedInstanceState){
        mRootRef = FirebaseDatabase.getInstance().getReference();
        conditionRef = mRootRef.child("Customers");
        return inflater.inflate(R.layout.fragment_setting_customer, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Customer customer = (Customer) getActivity().getIntent().getSerializableExtra("Customer");


        customer_settings_change_language = getActivity().findViewById(R.id.customer_settings_change_language);
        customer_settings_change_name = getActivity().findViewById(R.id.customer_settings_change_name);
        customer_settings_change_password = getActivity().findViewById(R.id.customer_settings_change_password);
        customer_settings_change_email = getActivity().findViewById(R.id.customer_settings_change_email);
        customer_settings_change_Phone = getActivity().findViewById(R.id.customer_settings_change_Phone);

        customer_choosed_language = getActivity().findViewById(R.id.customer_choosed_language);
        customer_actual_name = getActivity().findViewById(R.id.customer_actual_name);
        customer_actual_email = getActivity().findViewById(R.id.customer_actual_email);
        customer_actual_Phone = getActivity().findViewById(R.id.customer_actual_Phone);

        customer_actual_name.setText(customer.getName());
        customer_actual_email.setText(customer.getEmail());
        customer_actual_Phone.setText(customer.getPhone());


        customer_settings_change_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] listitem = new String[]{"English", "Turkish"};
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(getActivity());
                mbuilder.setTitle("Choose Your Language");
                mbuilder.setIcon(R.drawable.language_icon);
                mbuilder.setSingleChoiceItems(listitem, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        customer_choosed_language.setText(listitem[i]);
                        if (i==0) {
                            setLocale("en");
                        } else {
                            setLocale("tr");
                        }
                        dialogInterface.dismiss();
                        Intent intent = new Intent(getActivity(), CustomerMain.class);
                        Bundle b = new Bundle();
                        b.putSerializable("Customer",customer);
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

        customer_settings_change_name.setOnClickListener(new View.OnClickListener() {
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
                builder.setMessage(" Your Actuall Name is " + customer_actual_name.getText() + "\n\n Please Entre Your New Name");
                new_Name.setHint("New Adress");
                builder.setView(linearLayout);

                new_Name.setLayoutParams(layoutParams);
                linearLayout.addView(new_Name);
                linearLayout.setPadding(60, 10, 60, 0);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        customer_actual_name.setText(new_Name.getText().toString());
                        customer.setName(customer_actual_name.getText().toString());
                        conditionRef.child(customer.getPhone()).child("name").setValue(customer.getName());
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

        customer_settings_change_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_market_fragment, new ChangePasswordMarketFragment());
                fragmentTransaction.commit();
            }
        });
        customer_settings_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CustomerSettingsFragment.this).navigate(R.id.action_nav_customer_settings_to_nav_change_password);
            }
        });
        customer_settings_change_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CustomerSettingsFragment.this).navigate(R.id.action_nav_customer_settings_to_fragment_change_phone_customer);
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