package com.example.e_debt_book;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_debt_book.model.Customer;
import com.example.e_debt_book.model.Debt;
import com.example.e_debt_book.model.Item;
import com.example.e_debt_book.model.Market;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

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
    DatabaseReference mRootRef, conditionRef;
    ListView listView;
    ArrayList<Debt> arrayList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_customers);
        mRootRef = FirebaseDatabase.getInstance().getReference();
        listView = findViewById(R.id.debtsList);
        addNewDebtButton = findViewById(R.id.addNewDebtButton3);

        Market market = (Market) getIntent().getSerializableExtra("Market");

        ArrayList<String> displayProductsList = new ArrayList<String>();
        final ArrayAdapter<String>  arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,displayProductsList);
        listView.setAdapter(arrayAdapter);



        conditionRef = mRootRef.child("Debts");
        Query query = conditionRef.orderByChild("marketPhone").equalTo(market.getPhone());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    String debtId = data.getKey();
                    Debt debt = data.getValue(Debt.class);
                    getitems(debtId, new MyCallback() {
                        @Override
                        public void onCallback(ArrayList<Item> itemArrayList) {
                            debt.setItemList(itemArrayList);
                            debt.setDebtID(debtId);
                            arrayList.add(debt);
                            displayProductsList.add(debt.toString());
                            arrayAdapter.notifyDataSetChanged();

                        }
                    });
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


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int i=position;
                Debt selectedDebt = arrayList.get(i);
                Intent intent = new Intent(myCustomers.this, debtsDetails.class);
                Bundle bundle = new Bundle();
                getCustomer(selectedDebt, new CustomerCallback() {
                    @Override
                    public void customerOnCallback(Customer customer) {
                        System.out.println("//////////////////////////////////////////////");
                        bundle.putSerializable("Customer", customer);
                        bundle.putSerializable("Market",market);
                        bundle.putSerializable("Debt",selectedDebt);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        });
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

    public ArrayList<Item> getitems(String id,MyCallback myCallback){
        ArrayList<Item> itemlist = new ArrayList<>();
        DatabaseReference conditionRefitems = mRootRef.child("Debts").child(id).child("items");
        conditionRefitems.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    Item item = data.getValue(Item.class);
                    Item item1 = new Item(item.getName(),item.getPrice());
                    itemlist.add(item1);
                }
                myCallback.onCallback(itemlist);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return itemlist;
    }


    public void getCustomer(Debt selectedDebt,CustomerCallback customerCallback) {
        DatabaseReference customerRef = mRootRef.child("Customers");
        Customer customer = new Customer();
        customerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()) {
                    Customer customer1 = ds.getValue(Customer.class);
                    String userId = ds.getKey();
                    customer1.setPhone(userId);
                    if (customer1.getPhone().equals(selectedDebt.getCustomerPhone())) {
                        customer.setPhone(customer1.getPhone());
                        customer.setStatus(customer1.getStatus());
                        customer.setEmail(customer1.getEmail());
                        customer.setName(customer1.getName());
                        customer.setLastname(customer1.getLastname());

                        customerCallback.customerOnCallback(customer);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private interface MyCallback {
        void onCallback(ArrayList<Item> arrayList);
    }

    private interface CustomerCallback {
        void customerOnCallback(Customer customer);
    }

}