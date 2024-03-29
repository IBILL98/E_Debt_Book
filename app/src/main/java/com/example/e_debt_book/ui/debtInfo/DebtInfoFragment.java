package com.example.e_debt_book.ui.debtInfo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.e_debt_book.R;
import com.example.e_debt_book.model.Customer;
import com.example.e_debt_book.model.Debt;
import com.example.e_debt_book.model.Item;
import com.example.e_debt_book.model.Market;
import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.gkemon.XMLtoPDF.model.FailureResponse;
import com.gkemon.XMLtoPDF.model.SuccessResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class DebtInfoFragment extends Fragment {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private EditText amountDisplay, descriptionDisplay,dueDateDisplay;
    private TextView customerInfoDisplay, dateOFLoanDisplay;
    private Button editButton, deleteButton, printButton,saveButton;
    private ImageButton changeDueDateButton;
    private ListView listView;

    private DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_debt_info, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        requestForSpecificPermission();


        customerInfoDisplay = getActivity().findViewById(R.id.customerInfoDisplay);
        amountDisplay = getActivity().findViewById(R.id.amountDisplay);
        dateOFLoanDisplay = getActivity().findViewById(R.id.dateOFLoanDisplay);
        dueDateDisplay = getActivity().findViewById(R.id.dueDateDisplay);
        descriptionDisplay = getActivity().findViewById(R.id.descriptionDisplay);
        changeDueDateButton = getActivity().findViewById(R.id.changeDueDateButton);
        editButton = getActivity().findViewById(R.id.editButton);
        deleteButton = getActivity().findViewById(R.id.deleteButton);
        printButton = getActivity().findViewById(R.id.printButton);
        saveButton = getActivity().findViewById(R.id.saveButton);
        listView = getActivity().findViewById(R.id.productsList);
        //LinearLayout content = getActivity().findViewById(R.id.printedLayout);
        ScrollView content = getActivity().findViewById(R.id.scrollViewmarkettoprint);


        setuneditable();


        Debt debt = (Debt) getActivity().getIntent().getSerializableExtra("Debt");
        Market market = (Market) getActivity().getIntent().getSerializableExtra("Market");
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
                if (saveButton.getVisibility() == View.VISIBLE){
                    materialDatePicker.show(getActivity().getSupportFragmentManager(),"Date_Picker");
                }else {
                    Toast.makeText(getActivity(), "Press on edit Button to enable editing", Toast.LENGTH_LONG).show();

                }
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
                seteditable();
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String debtAmount = amountDisplay.getText().toString();
                String dueDate = dueDateDisplay.getText().toString();
                String description = descriptionDisplay.getText().toString();
                debt.setDueDate(dueDate);
                debt.setAmount(debtAmount);
                debt.setDescription(description);
                reference.child(debt.getDebtID()).setValue(debt);
                Toast.makeText(getActivity(), "Debt has been Updated", Toast.LENGTH_LONG).show();
                setuneditable();
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
                            NavHostFragment.findNavController(DebtInfoFragment.this).navigate(R.id.action_debt_info_to_nav_market_home);
                        }
                    }
                });
            }
        });


        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                printButton.setVisibility(View.INVISIBLE);
                editButton.setVisibility(View.INVISIBLE);
                deleteButton.setVisibility(View.INVISIBLE);

//
//                try {
//                    content.setDrawingCacheEnabled(true);
//                    Bitmap bitmap = content.getDrawingCache();
//                    File file,f = null;
//
//
//                if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
//
//                    file  = new File(android.os.Environment.getExternalStorageDirectory(),"E-DebtBook");
//                    if(!file.exists()) {
//                        file.mkdirs();
//                    }
//
//                    f = new File(file.getAbsolutePath()+file+ "filename"+".png");
//
//                }
//                FileOutputStream ostream = new FileOutputStream(f);
//                bitmap.compress(Bitmap.CompressFormat.PNG, 10, ostream);
//                ostream.close();
//
//                }
//                catch (Exception e){
//                    e.printStackTrace();
//
//                }
//
                PdfGenerator.getBuilder()
                        .setContext(getContext())
                        .fromViewIDSource()
                        .fromViewID(getActivity(),R.id.scrollViewmarkettoprint)
                        /* "fromViewID()" takes array of view ids those MUST BE and MUST BE contained in the inserted "activity" .
                         * You can also invoke "fromViewIDList()" method here which takes list of view ids instead of array. */
                        .setCustomPageSize(2000,3000)
                        /* Here I used ".setCustomPageSize(3000,3000)" to set custom page size.*/
                        .setFileName(debt.getDebtID())
                        .setFolderName("E-DebtBook/Market Reports")
                        .openPDFafterGeneration(false)
                        .build(new PdfGeneratorListener() {
                            @Override
                            public void onFailure(FailureResponse failureResponse) {
                                super.onFailure(failureResponse);
                            }

                            @Override
                            public void showLog(String log) {
                                super.showLog(log);
                            }

                            @Override
                            public void onSuccess(SuccessResponse response) {
                                super.onSuccess(response);
                                Toast.makeText(getActivity(), "Debt PDF Saved in your Files successfully!", Toast.LENGTH_LONG).show();

                            }
                        });


                printButton.setVisibility(View.VISIBLE);
                editButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
            }
        });




    }


    public void seteditable(){
        printButton.setVisibility(View.GONE);
        saveButton.setVisibility(View.VISIBLE);
        amountDisplay.setClickable(true);
        amountDisplay.setFocusable(true);
        amountDisplay.setFocusableInTouchMode(true);
        dueDateDisplay.setClickable(true);
        dueDateDisplay.setFocusable(true);
        dueDateDisplay.setFocusableInTouchMode(true);
        descriptionDisplay.setClickable(true);
        descriptionDisplay.setFocusable(true);
        descriptionDisplay.setFocusableInTouchMode(true);
    }

    public void setuneditable(){
        saveButton.setVisibility(View.GONE);
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
    }



    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.GET_ACCOUNTS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET}, 101);
    }

}