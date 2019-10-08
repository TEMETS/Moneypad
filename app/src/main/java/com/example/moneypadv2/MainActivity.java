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

        updateUI();

    }

    public void updateUI() {
        TextView textView = findViewById(R.id.spendToday);
        textView.setText(Double.toString(dataHandler.spentToday()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
