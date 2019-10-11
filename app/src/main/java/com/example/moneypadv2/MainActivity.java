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
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * The main activity of the app
 * @author Touko Ala-Savikota
 */
public class MainActivity extends AppCompatActivity {

    DataHandler dataHandler = DataHandler.getInstance(); //Gets the singleton DataHandler
    boolean resetData = false; //Enable this to add new methods to DataHandler. Otherwise program crashes.
    boolean resetMemorySure = false; //Used with Reset memory -button. Makes sure if it's the second time touching the button

    /**
     * Gets previous DataHandler from SharedPreferences or creates a new one
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Gets encoded string from SharedPreferences
        SharedPreferences prefGet = getSharedPreferences("DataPref" , Activity.MODE_PRIVATE);
        String dataHandlerString = prefGet.getString("DataHandler", "Nothing stored");


        if (!dataHandlerString.contains("Nothing stored") && resetData == false) { //If previous data exists and we don't want debug reset
            Log.d("DataPref","FOUND PREVIOUS DATA");
            //Decode string to a temporary DataHandler
            Serializable dataHandlerSerializable = stringToObject(dataHandlerString);
            DataHandler tempHandler = (DataHandler) dataHandlerSerializable;

            //Override current DataHandler with the DataHandler got from memory
            dataHandler.overrideData(tempHandler);

        }

        //If debug reset is true, wipe the memory
        if (resetData) {
            SharedPreferences prefPut = getSharedPreferences("DataPref", Activity.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = prefPut.edit();
            prefEditor.putString("DataHandler", "Nothing stored");
            prefEditor.commit();
        }

        updateUI();

    }

    /**
     * Updates the TextViews from MainActivity
     */
    public void updateUI() {
        TextView spentToday = findViewById(R.id.spentToday);
        spentToday.setText(Double.toString(dataHandler.spentToday()));

        TextView spentYesterday = findViewById(R.id.spentYesterday);
        spentYesterday.setText(Double.toString(dataHandler.spentYesterday()));

        TextView spentAll = findViewById(R.id.spentAll);
        spentAll.setText(Double.toString(dataHandler.spentAll()));
    }

    /**
     * Creates menu-button to Actionbar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * Creates a new activity (EntryActivity)
     * @param menuItem
     */
    public void entryActivity (MenuItem menuItem) {
        Intent intent = new Intent(this, EntryActivity.class);
        startActivity(intent);
    }

    /**
     * Creates new activity (ViewListActivity)
     * @param menuItem
     */
    public void viewListActivity (MenuItem menuItem) {
        Intent intent = new Intent(this, ViewListActivity.class);
        startActivity(intent);
    }

    /**
     * Resets app's memory / Clears SharedPreferences and Datahandler
     * @param menuItem
     */
    public void resetMemory(MenuItem menuItem) {
        Toast.makeText(MainActivity.this,"Are you sure?",Toast.LENGTH_LONG).show();

        if (resetMemorySure) {

            resetMemorySure = false;

            SharedPreferences prefPut = getSharedPreferences("DataPref", Activity.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = prefPut.edit();
            prefEditor.putString("DataHandler", "Nothing stored");
            prefEditor.commit();

            dataHandler.clearData();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
        resetMemorySure = true;
    }

    /**
     * Changes strings to Serializable-objects. Uses Base64 to decode data.
     */
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
