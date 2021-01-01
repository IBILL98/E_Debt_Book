package com.example.e_debt_book.ui.debtInfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.e_debt_book.R;
import com.example.e_debt_book.debtsDetails;
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

import java.util.ArrayList;

public class DebtInfoFragment extends Fragment {


    EditText amountDisplay, descriptionDisplay,dueDateDisplay;
    TextView customerInfoDisplay, dateOFLoanDisplay;
    Button editButton, deleteButton, printButton;
    ImageButton changeDueDateButton;
    ListView listView;

    DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_debt_info, container, false);
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
        editButton = (Button)getActivity().findViewById(R.id.editButton);
        deleteButton = (Button)getActivity().findViewById(R.id.deleteButton);
        printButton = (Button)getActivity().findViewById(R.id.printButton);
        listView = (ListView)getActivity().findViewById(R.id.productsList);

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

        changeDueDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getActivity().getSupportFragmentManager(),"Date_Picker");
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                dueDateDisplay.setText(materialDatePicker.getHeaderText());
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String debtAmount = amountDisplay.getText().toString();
                String dueDate = dueDateDisplay.getText().toString();
                String description = descriptionDisplay.getText().toString();
                debt.setDueDate(dueDate);
                debt.setAmount(debtAmount);
                debt.setDescription(description);
                reference.child(debt.getDebtID()).setValue(debt);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(debt.getDebtID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Debt removed successfully!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });





    }

}