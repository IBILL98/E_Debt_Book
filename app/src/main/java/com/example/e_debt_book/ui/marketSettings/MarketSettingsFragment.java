package com.example.e_debt_book.ui.MarketSettings;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.example.e_debt_book.R;
import com.example.e_debt_book.model.Market;

public class MarketSettingsFragment extends Fragment {

    LinearLayout market_settings_change_language,market_settings_change_Adress,market_settings_change_name;
    TextView choosed_language,actuall_adress,actuall_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting_market, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        Market market = (Market) getActivity().getIntent().getSerializableExtra("Market");

        market_settings_change_language = getActivity().findViewById(R.id.market_settings_change_language);
        market_settings_change_Adress = getActivity().findViewById(R.id.market_settings_change_Adress);
        market_settings_change_name = getActivity().findViewById(R.id.market_settings_change_name);
        choosed_language = getActivity().findViewById(R.id.choosed_language);
        actuall_adress = getActivity().findViewById(R.id.actuall_adress);
        actuall_name = getActivity().findViewById(R.id.actuall_name);

        actuall_adress.setText(market.getAdress());
        actuall_name.setText(market.getName());

        market_settings_change_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] listitem = new String[]{"English","Arabic"};
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(getActivity());
                mbuilder.setTitle("Choose Your Language");
                mbuilder.setIcon(R.drawable.language_icon);
                mbuilder.setSingleChoiceItems(listitem, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        choosed_language.setText(listitem[i]);
                        dialogInterface.dismiss();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Change Adress");
                builder.setIcon(R.drawable.place_icon);
                EditText new_adress = new EditText(getActivity());
                new_adress.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setMessage("Your Actuall Adress is "+actuall_adress.getText()+ "\n\n Please Entre Your New Adress");
                builder.setView(new_adress);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        actuall_adress.setText(new_adress.getText().toString());
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Change Your Market Name");
                builder.setIcon(R.drawable.account_icon1);
                EditText new_name = new EditText(getActivity());
                new_name.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setMessage("Your Actuall Name is "+actuall_name.getText()+ "\n\n Please Entre Your New Name");
                builder.setView(new_name);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        actuall_adress.setText(new_name.getText().toString());
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




    }
}