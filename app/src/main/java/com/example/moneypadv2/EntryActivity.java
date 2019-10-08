package com.example.moneypadv2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class EntryActivity extends AppCompatActivity {

    DataHandler dataHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add a new entry");


        dataHandler = DataHandler.getInstance();

        //TextView test = findViewById(R.id.textView7);
        //test.setText(Double.toString(dataHandler.spentToday()));

    }

}
