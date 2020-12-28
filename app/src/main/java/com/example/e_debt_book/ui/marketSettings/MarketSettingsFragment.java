package com.example.e_debt_book.ui.marketSettings;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    EditTextPreference editTextPreference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting_market, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        Market market = (Market) getActivity().getIntent().getSerializableExtra("Market");

//
//        String[] listitem = new String[]{"English","Arabic"};
//        TextView mtextView = null;
//        AlertDialog.Builder mbuilder = new AlertDialog.Builder(getActivity());
//        mbuilder.setTitle("Choose Your Language");
//        mbuilder.setIcon(R.drawable.language_icon);
//        mbuilder.setSingleChoiceItems(listitem, -1, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                mtextView.setText(listitem[i]);
//                dialogInterface.dismiss();
//            }
//        });
//        mbuilder.setNeutralButton("Cancle", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//        AlertDialog mdialog = mbuilder.create();
//        mdialog.show();


    }
}