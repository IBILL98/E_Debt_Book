package com.example.e_debt_book;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_debt_book.model.Debt;
import com.example.e_debt_book.model.Item;
import com.example.e_debt_book.model.Market;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class myCustomers extends AppCompatActivity {
//    ListView debtsList;
//    FloatingActionButton addNewDebtButton;
//    ArrayList<String> list;
//    ArrayAdapter<String> adapter;
//    ArrayList<Debt> debtsArray;
//    Debt debt;
//    Customer customer;
//
//    FirebaseDatabase database;
//    DatabaseReference reference;
//    DatabaseReference reference2;


    FloatingActionButton addNewDebtButton;
    DatabaseReference mRootRef, conditionRef,itemref;
    ListView listView;
    ArrayList<Debt> arrayList = new ArrayList<>();
    ArrayAdapter<Debt> arrayAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_customers);
        mRootRef = FirebaseDatabase.getInstance().getReference();
        listView = findViewById(R.id.debtsList);
        addNewDebtButton = findViewById(R.id.addNewDebtButton);
        arrayAdapter = new ArrayAdapter<Debt>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        Market market = (Market) getIntent().getSerializableExtra("Market");


        conditionRef = mRootRef.child("Debts");
        conditionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    String debtId = data.getKey();
                    Debt debt = data.getValue(Debt.class);
                    debt.setDebtID(debtId);
                    System.out.println("************************************************************************************");
                    System.out.println(market.getPhone());
                    System.out.println(debt.getMarketPhone());
                    if (market.getPhone().equals(debt.getMarketPhone())){
                        System.out.println("///////////////////////////////////////////////////////////////////////\n///////////////////////////////////////////////////////");

                        System.out.println(debt.getDueDate());
                        System.out.println(debt.getDescription());
                        System.out.println(debt.getAmount());
                        System.out.println(debt.getItemList());
                        System.out.println(debt.getMarketPhone());
                        System.out.println("///////////////////////////////////////////////////////////////////////\n///////////////////////////////////////////////////////");

                        arrayList.add(debt);
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







//        debtsList = findViewById(R.id.debtsList);
//        addNewDebtButton = findViewById(R.id.addNewDebtButton);
//
//        database = FirebaseDatabase.getInstance();
//
//        reference = database.getReference("Debts");
//        reference2 = database.getReference("Customers");
//
//        list = new ArrayList<String>();
//        debt = new Debt();
//        customer = new Customer();
//        debtsArray = new ArrayList<Debt>();
//        Market market = (Market) getIntent().getSerializableExtra("Market");
//
//        adapter = new ArrayAdapter<String>(myCustomers.this, R.layout.debts_infos_resource, list);
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot ds: snapshot.getChildren()){
//                    debt = ds.getValue(Debt.class);
//                    assert debt != null;
//                    if(debt.getMarketPhone().equals(market.getPhone())) {
//                        list.add(debt.getCustomerPhone()+ ", " + debt.getAmount());
//                        debtsArray.add(debt);
//                    }
//                }
//                debtsList.setAdapter(adapter);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        debtsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                int i=position;
//                Debt selectedDebt = debtsArray.get(i);
//                Intent intent = new Intent(myCustomers.this, debtsDetails.class);
//                Bundle bundle = new Bundle();
//                reference2.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot ds: snapshot.getChildren()) {
//                            customer = ds.getValue(Customer.class);
//                            assert customer != null;
//                            if (customer.getPhone() == selectedDebt.getCustomerPhone()) {
//                                bundle.putSerializable("Customer", customer);
//                                break;
//                            }
//                        }
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//                bundle.putSerializable("Market",market);
//                bundle.putSerializable("Debt",selectedDebt);
//                intent.putExtras(bundle);
//                startActivity(intent);
//                finish();
//            }
//        });
        addNewDebtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(myCustomers.this,addNewDebt.class);
                Bundle b = new Bundle();
                b.putSerializable("Market",market);
                i.putExtras(b);
                startActivity(i);
                finish();
            }
        });
    }
}