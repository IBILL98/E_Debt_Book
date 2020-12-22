package com.example.e_debt_book;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class myCustomers extends AppCompatActivity {
    //sign up attributes
    ListView debtsList;
    Button addNewDebtButton;

    //Firebase attributes
    FirebaseAuth kAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mRootRef,conditionRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_customers);

        kAuth = FirebaseAuth.getInstance();

        if(kAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        mRootRef = FirebaseDatabase.getInstance().getReference();

        debtsList = findViewById(R.id.debtsList);
        String[] values  = new String[] {
          //fill the list items
        };
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.layout.text1, values);
        //debtsList.setAdapter(adapter);
        debtsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //if (position==0) {
                //    Intent intent = new Intent(view.getContext(), addNewDept.class);
                //    startActivity(intent);
                //}
            }
        });

    }
}