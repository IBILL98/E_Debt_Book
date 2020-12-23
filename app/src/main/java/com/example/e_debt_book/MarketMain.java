package com.example.e_debt_book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.e_debt_book.model.Market;

public class MarketMain extends AppCompatActivity {

    Button mainMarketAddCustomertButton ,mainMarketeditinfoButton , mainMarketmyCostumertButton ,mainMarketLogoutButton;
    ConstraintLayout passwordconfirmationmeny , mainmarketmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_main);

        mainMarketAddCustomertButton = findViewById(R.id.mainMarketAddCustomertButton);
        mainMarketeditinfoButton = findViewById(R.id.mainMarketeditinfoButton);
        mainMarketmyCostumertButton = findViewById(R.id.mainMarketmyCostumertButton);
        mainMarketLogoutButton = findViewById(R.id.mainMarketLogoutButton);

        Market market = (Market) getIntent().getSerializableExtra("Market");




        mainMarketAddCustomertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mainMarketeditinfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
   /*
                Intent i = new Intent(MarketMain.this, editMarketInfo.class);
                Bundle b = new Bundle();
                b.putSerializable("Market",market);
                i.putExtras(b);
                startActivity(i);
                finish();
*/

                startActivity(new Intent(getApplicationContext(), editMarketInfo.class));
                finish();

            }
        });
        mainMarketmyCostumertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mainMarketLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });













    }
}