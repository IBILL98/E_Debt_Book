package com.example.e_debt_book.ui.debtInfo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.e_debt_book.R;
import com.example.e_debt_book.model.Customer;
import com.example.e_debt_book.model.Debt;
import com.example.e_debt_book.model.Item;
import com.example.e_debt_book.model.Market;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class DebtInfoCustomerFragment extends Fragment {


    EditText amountDisplay, descriptionDisplay,dueDateDisplay;
    TextView customerInfoDisplay, dateOFLoanDisplay;
    Button  printButton;
    ImageButton changeDueDateButton;
    ListView listView;

    DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_debt_info_customer, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        customerInfoDisplay = (TextView) getActivity().findViewById(R.id.customerInfoDisplay);
        amountDisplay = (EditText)getActivity().findViewById(R.id.amountDisplay);
        dateOFLoanDisplay = (TextView) getActivity().findViewById(R.id.dateOFLoanDisplay);
        dueDateDisplay = (EditText) getActivity().findViewById(R.id.dueDateDisplay);
        descriptionDisplay = (EditText)getActivity().findViewById(R.id.descriptionDisplay);
        changeDueDateButton =  getActivity().findViewById(R.id.changeDueDateButton);
        printButton = (Button)getActivity().findViewById(R.id.printButton);
        listView = (ListView)getActivity().findViewById(R.id.productsList);
        LinearLayout content = getActivity().findViewById(R.id.printedLayout);


        setuneditable();


        Debt debt = (Debt) getActivity().getIntent().getSerializableExtra("Debt");
        Market market = (Market) getActivity().getIntent().getSerializableExtra("Market") ;
        Customer customer = (Customer) getActivity().getIntent().getSerializableExtra("Customer");

        reference = FirebaseDatabase.getInstance().getReference().child("Debts");

        ArrayList<String> displayarray = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,displayarray);
        listView.setAdapter(arrayAdapter);
        for (int i = 0; i<debt.getItemList().size();i++){
            Item item = debt.getItemList().get(i);
            displayarray.add(i,item.toString());
            arrayAdapter.notifyDataSetChanged();

        }

        customerInfoDisplay.setText(customer.getName()+" "+customer.getLastname()+", "+customer.getPhone());
        amountDisplay.setText(debt.getAmount());
        dateOFLoanDisplay.setText(debt.getDateOfLoan());
        dueDateDisplay.setText(debt.getDueDate());
        descriptionDisplay.setText(debt.getDescription());

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select A Date");
        MaterialDatePicker materialDatePicker = builder.build();



        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                dueDateDisplay.setText(materialDatePicker.getHeaderText());
            }
        });



        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





            }
        });




    }


    public void seteditable(){
        printButton.setVisibility(View.GONE);
        amountDisplay.setClickable(true);
        amountDisplay.setFocusable(true);
        amountDisplay.setFocusableInTouchMode(true);
        dueDateDisplay.setClickable(true);
        dueDateDisplay.setFocusable(true);
        dueDateDisplay.setFocusableInTouchMode(true);
        descriptionDisplay.setClickable(true);
        descriptionDisplay.setFocusable(true);
        descriptionDisplay.setFocusableInTouchMode(true);
        changeDueDateButton.setClickable(true);
        changeDueDateButton.setFocusable(true);
        changeDueDateButton.setFocusableInTouchMode(true);
    }

    public void setuneditable(){
        printButton.setVisibility(View.VISIBLE);
        amountDisplay.setClickable(false);
        amountDisplay.setFocusable(false);
        amountDisplay.setFocusableInTouchMode(false);
        dueDateDisplay.setClickable(false);
        dueDateDisplay.setFocusable(false);
        dueDateDisplay.setFocusableInTouchMode(false);
        descriptionDisplay.setClickable(false);
        descriptionDisplay.setFocusable(false);
        descriptionDisplay.setFocusableInTouchMode(false);
        changeDueDateButton.setClickable(false);
        changeDueDateButton.setFocusable(false);
        changeDueDateButton.setFocusableInTouchMode(false);
    }



}