package com.example.e_debt_book.ui.debtInfo;

import android.Manifest;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
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


import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.e_debt_book.R;
import com.example.e_debt_book.model.Customer;
import com.example.e_debt_book.model.Debt;
import com.example.e_debt_book.model.Item;
import com.example.e_debt_book.model.Market;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

public class DebtInfoCustomerFragment extends Fragment {


    private EditText amountDisplay, descriptionDisplay,dueDateDisplay;
    private TextView customerInfoDisplay, dateOFLoanDisplay;
    private Button  printButton;
    private ImageButton changeDueDateButton;
    private ListView listView;
    private DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_debt_info_customer, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        requestForSpecificPermission();
        customerInfoDisplay = (TextView) getActivity().findViewById(R.id.customerInfoDisplay);
        amountDisplay = (EditText) getActivity().findViewById(R.id.amountDisplay);
        dateOFLoanDisplay = (TextView) getActivity().findViewById(R.id.dateOFLoanDisplay);
        dueDateDisplay = (EditText) getActivity().findViewById(R.id.dueDateDisplay);
        descriptionDisplay = (EditText) getActivity().findViewById(R.id.descriptionDisplay);
        changeDueDateButton = getActivity().findViewById(R.id.changeDueDateButton);
        printButton = (Button) getActivity().findViewById(R.id.printButton);
        listView = (ListView) getActivity().findViewById(R.id.productsList);
        LinearLayout printedLayout = getActivity().findViewById(R.id.printedLayout);


        setuneditable();


        Debt debt = (Debt) getActivity().getIntent().getSerializableExtra("Debt");
        Market market = (Market) getActivity().getIntent().getSerializableExtra("Market");
        Customer customer = (Customer) getActivity().getIntent().getSerializableExtra("Customer");

        reference = FirebaseDatabase.getInstance().getReference().child("Debts");

        ArrayList<String> displayarray = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, displayarray);
        listView.setAdapter(arrayAdapter);
        for (int i = 0; i < debt.getItemList().size(); i++) {
            Item item = debt.getItemList().get(i);
            displayarray.add(i, item.toString());
            arrayAdapter.notifyDataSetChanged();

        }

        customerInfoDisplay.setText(customer.getName() + " " + customer.getLastname() + ", " + customer.getPhone());
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
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                try{
                    printButton.setVisibility(View.INVISIBLE);
                    android.graphics.pdf.PdfDocument document = new android.graphics.pdf.PdfDocument();

                    android.graphics.pdf.PdfDocument.PageInfo pageInfo = new android.graphics.pdf.PdfDocument.PageInfo.Builder(printedLayout.getWidth(), printedLayout.getHeight(), 1).create();

                    android.graphics.pdf.PdfDocument.Page page = document.startPage(pageInfo);

                    Canvas canvas = page.getCanvas();
                    Paint paint = new Paint();
                    canvas.drawPaint(paint);
                    printedLayout.draw(canvas);
                    document.finishPage(page);

                    String path = "/sdcard/E-DebtBook/"+debt.getDebtID()+".pdf";

                    java.io.File myFile = new java.io.File(path);

                    java.io.FileOutputStream fOut = new java.io.FileOutputStream(myFile);

                    java.io.OutputStreamWriter myOutWriter = new java.io.OutputStreamWriter(fOut);

                    document.writeTo(fOut);

                    document.close();
                    myOutWriter.close();
                    fOut.close();

                    Toast.makeText(getActivity(), "File Saved", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }





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

    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.GET_ACCOUNTS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET}, 101);
    }

}