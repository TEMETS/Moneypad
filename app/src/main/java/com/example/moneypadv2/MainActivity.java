package com.example.moneypadv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    DataHandler dataHandler = new DataHandler(500);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataHandler.add(50);
        dataHandler.add(50);

        dataHandler.add(-1,0,0,50);

        updateUI();

    }

    public void updateUI() {
        TextView spentToday = findViewById(R.id.spentToday);
        spentToday.setText(Double.toString(dataHandler.spentToday()));

        TextView spentYesterday = findViewById(R.id.spentYesterday);
        spentYesterday.setText(Double.toString(dataHandler.spentYesterday()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
