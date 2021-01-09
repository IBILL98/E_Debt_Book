package com.example.e_debt_book.ui.marketHome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.e_debt_book.R;
import com.example.e_debt_book.model.Customer;
import com.example.e_debt_book.model.Debt;
import com.example.e_debt_book.model.Item;
import com.example.e_debt_book.model.Market;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;




public class MarketHomeFragment extends Fragment {
    // declaring the necessary attributes

    private FloatingActionButton addNewDebtButton;
    private DatabaseReference mRootRef, conditionRef;
    private ListView listView;
    private final ArrayList<Debt> arrayList = new ArrayList<>();
    private int totallend;
    private TextView textView2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_market_home, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        //setContentView(R.layout.activity_my_customers);
        arrayList.clear();
        //amount of the total lends
        totallend = 0;
        mRootRef = FirebaseDatabase.getInstance().getReference();
        listView = getActivity().findViewById(R.id.debtsList);
        textView2 = getActivity().findViewById(R.id.textView2);
        addNewDebtButton = getActivity().findViewById(R.id.addNewDebtButton2);
        Market market = (Market) getActivity().getIntent().getSerializableExtra("Market");
        MyAdapter arrayAdapter = new MyAdapter(getActivity(),arrayList);
        listView.setAdapter(arrayAdapter);


        conditionRef = mRootRef.child("Debts");
        Query query = conditionRef.orderByChild("marketPhone").equalTo(market.getPhone());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String debtId = data.getKey();
                    Debt debt = data.getValue(Debt.class);
                    totallend = Integer.parseInt(debt.getAmount()) + totallend;
                    getitems(debtId, new MyCallback() {
                        @Override
                        public void onCallback(ArrayList<Item> itemArrayList) {
                            debt.setItemList(itemArrayList);
                            debt.setDebtID(debtId);
                            arrayList.add(debt);
                            arrayAdapter.notifyDataSetChanged();
                        }
                    });
                }
                textView2.setText(textView2.getText().toString() + " " + totallend);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int i = position;
                Debt selectedDebt = arrayList.get(i);
                Intent intent = getActivity().getIntent();
                Bundle bundle = new Bundle();

                getCustomer(selectedDebt.getCustomerPhone(), new CustomerCallback() {
                    @Override
                    public void customerOnCallback(Customer customer) {
                        bundle.putSerializable("Customer", customer);
                        bundle.putSerializable("Market", market);
                        bundle.putSerializable("Debt", selectedDebt);
                        intent.putExtras(bundle);

                        NavHostFragment.findNavController(MarketHomeFragment.this).navigate(R.id.action_nav_market_home_to_debt_info);
                    }
                });
            }
        });

        addNewDebtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getActivity().getIntent();
                Bundle b = new Bundle();
                b.putSerializable("Market", market);
                i.putExtras(b);
                NavHostFragment.findNavController(MarketHomeFragment.this).navigate(R.id.action_nav_market_home_to_add_debt);

            }
        });
    }

    public ArrayList<Item> getitems(String id, MyCallback myCallback) {
        ArrayList<Item> itemlist = new ArrayList<>();
        DatabaseReference conditionRefitems = mRootRef.child("Debts").child(id).child("itemList");
        conditionRefitems.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Item item = data.getValue(Item.class);
                    Item item1 = new Item(item.getName(), item.getPrice());
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


    public void getCustomer(String phone, CustomerCallback customerCallback) {
        DatabaseReference customerRef = mRootRef.child("Customers");
        Customer customer = new Customer();
        customerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Customer customer1 = ds.getValue(Customer.class);
                    String userId = ds.getKey();
                    customer1.setPhone(userId);
                    if (customer1.getPhone().equals(phone)) {
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


    public class MyAdapter extends ArrayAdapter<Debt> {

        public MyAdapter(Context context, ArrayList<Debt> debts) {
            super(context, 0, debts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Debt debt = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_listview, parent, false);
            }
            // Lookup view for data population
            TextView fullname = convertView.findViewById(R.id.fullName);
            TextView amount = convertView.findViewById(R.id.amount);
            TextView phone = convertView.findViewById(R.id.phone);
            TextView date = convertView.findViewById(R.id.date);
            // Populate the data into the template view using the data object
            phone.setText(debt.getCustomerPhone());
            amount.setText("Amount : " + debt.getAmount());
            date.setText(debt.getDateOfLoan());
            DatabaseReference databaseReferenc = FirebaseDatabase.getInstance().getReference();

            getCustomer(debt.getCustomerPhone(), new CustomerCallback() {
                @Override
                public void customerOnCallback(Customer customer) {
                    fullname.setText(customer.getName() + " " + customer.getLastname());
                }
            });

            // Return the completed view to render on screen
            return convertView;
        }
    }


}

