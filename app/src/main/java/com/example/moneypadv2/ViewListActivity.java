package com.example.moneypadv2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.drm.DrmStore;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Creates a simple list from all entries that DataHandler has stored
 * @author Eetu Penttilä
 */
public class ViewListActivity extends AppCompatActivity {

    DataHandler dataHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        ActionBar actionBar = getSupportActionBar(); // Actionbar backbutton to MainActivity
        actionBar.setTitle("Chart");

        final ListView listView = (ListView) findViewById(R.id.listView); //Creates a listview

        dataHandler = DataHandler.getInstance(); //Gets the singleton DataHandler

        //Defines an ArrayList with all the DataHandlers content
        ArrayList<String> values = new ArrayList<>();
        int i = 0;
        while (i < dataHandler.getSize()) {
            values.add(dataHandler.get(i));
            i++;
        }

        //Makes an ArrayAdapter to ease displaying text in ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1,values);

        listView.setAdapter(adapter);

        //Makes a toast with current content when an element is clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) listView.getItemAtPosition(position);
                Toast.makeText(ViewListActivity.this, itemValue, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
