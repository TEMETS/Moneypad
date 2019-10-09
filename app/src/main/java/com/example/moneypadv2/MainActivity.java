package com.example.moneypadv2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    DataHandler dataHandler = DataHandler.getInstance();
    //DataHandler dataHandler;
    boolean resetData = false; //Enable this to add new methods to DataHandler. Otherwise program crashes.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefGet = getSharedPreferences("DataPref" , Activity.MODE_PRIVATE);
        String dataHandlerString = prefGet.getString("DataHandler", "Nothing stored");

        //System.out.println(dataHandlerString);

        if (!dataHandlerString.contains("Nothing stored") && resetData == false) {
            Log.d("DataPref","FOUND PREVIOUS DATA");
            Serializable dataHandlerSerializable = stringToObject(dataHandlerString);
            DataHandler tempHandler = (DataHandler) dataHandlerSerializable;

            dataHandler.overrideData(tempHandler);

            //dataHandler = (DataHandler) dataHandlerSerializable;
        }

        if (resetData) {
            SharedPreferences prefPut = getSharedPreferences("DataPref", Activity.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = prefPut.edit();
            prefEditor.putString("DataHandler", "Nothing stored");
            prefEditor.commit();
        }

        //dataHandler.add(50);
        //dataHandler.add(50);

        //dataHandler.add(-1,0,0,50);

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

    public void entryActivity (MenuItem menuItem) {
        Intent intent = new Intent(this, EntryActivity.class);
        startActivity(intent);
    }

    public void viewListActivity (MenuItem menuItem) {
        Intent intent = new Intent(this, ViewListActivity.class);
        startActivity(intent);
    }

    @SuppressWarnings("unchecked")
    static public Serializable stringToObject(String string){
        byte[] bytes = Base64.decode(string,0);
        Serializable object = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream( new ByteArrayInputStream(bytes) );
            object = (Serializable)objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return object;
    }
}
