package com.example.e_debt_book.ui.marketSettings;

import android.content.Context;
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
import androidx.navigation.fragment.NavHostFragment;

import com.example.e_debt_book.MarketMain1;
import com.example.e_debt_book.R;
import com.example.e_debt_book.model.Market;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;


public class MarketSettingsFragment extends Fragment {
    //setting the attributes for the settings screen

    private LinearLayout market_settings_change_language, market_settings_change_Adress, market_settings_change_name, market_settings_change_password, market_settings_change_email, market_settings_change_Phone;
    private TextView choosed_language, actual_adress, actual_name, actual_email, actual_Phone;
    private DatabaseReference mRootRef, conditionRef;

    private Context context;

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
        //finding the Ids of the xml file
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
        //setting the setting profile texts depending on the market values
        actual_adress.setText(market.getAdress());
        actual_name.setText(market.getName());
        actual_email.setText(market.getEmail());
        actual_Phone.setText(market.getPhone());

        int w = whichLanguageIsRunning();
        //changing the language list
        String[] listitem = new String[]{"English", "Deutsch", "Türkçe", "العربية"};
        choosed_language.setText(listitem[w]);
        mRootRef = FirebaseDatabase.getInstance().getReference();
        //creting a dialoge to change the language
        market_settings_change_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] listitem = new String[]{"English", "Deutsch", "Türkçe", "العربية"};
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(getActivity());
                mbuilder.setTitle("Choose Your Language");
                mbuilder.setIcon(R.drawable.language_icon);
                // the dialog is shown with the selected language
                // the first parameter is the list of the available langs, the second is the function
                //that returnes a number of the language
                mbuilder.setSingleChoiceItems(listitem, whichLanguageIsRunning(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        choosed_language.setText(listitem[i]);
                        if (i == 0) {
                            setLocale("en");
                        } else if (i == 2) {
                            setLocale("tr");
                        } else if (i == 1) {
                            setLocale("de");
                        } else if (i == 3) {
                            setLocale("ar");
                        }
                        //reload the whole activity after changing the language
                        dialogInterface.dismiss();
                        Intent intent = new Intent(getActivity(), MarketMain1.class);
                        Bundle b = new Bundle();
                        b.putSerializable("Market",market);
                        intent.putExtras(b);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
                mbuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
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
                //creting a dialoge to change the adress
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.gravity = Gravity.CENTER;
                LinearLayout linearLayout = new LinearLayout(getActivity());
                //showing the dialog with the old values and textfield for the new input values
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
        //changing the name window
        //creting a dialoge to change the name
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
        //Move to change email fragment after clicking on the market_settings_change_email
        market_settings_change_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(MarketSettingsFragment.this).navigate(R.id.action_nav_market_settings_to_fragment_change_email_market);

            }
        });
        //Move to change password fragment after clicking on the market_settings_change_password
        market_settings_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(MarketSettingsFragment.this).navigate(R.id.action_nav_market_settings_to_fragment_change_password);
            }
        });
        //Move to change phone fragment after clicking on the market_settings_change_Phone
        market_settings_change_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(MarketSettingsFragment.this).navigate(R.id.action_nav_market_settings_to_fragment_change_phone_market);
            }
        });
    }

    //change language function
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Configuration c = res.getConfiguration();
    }

    // detecting which language is set currently
    private int whichLanguageIsRunning() {
        // it works through looking which is the word that's shown at the "Settings" Text view
        TextView t = getActivity().findViewById(R.id.textView7);
        if (t.getText().equals("Settings")) return 0;
        else if (t.getText().equals("Einstellungen")) return 1;
        else if (t.getText().equals("Ayarlar")) return 2;
        else return 3;
    }

}


