package com.example.moneypadv2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Used to save activity to DataHandler and also to SharedPreferences
 * @author Teemu Luhtanen
 */
public class EntryActivity extends AppCompatActivity {

    DataHandler dataHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        ActionBar actionBar = getSupportActionBar(); // Actionbar backbutton to MainActivity
        actionBar.setTitle("Add a new entry");


        dataHandler = DataHandler.getInstance(); //Gets the singleton DataHandler
    }

    /**
     * Saves added entry to Datahandler's ArrayList
     *  Makes a toast if add was successfull
     *  Also clears text boxes
     *
     *  An interesting thing to note is that almost any amount of value can be set.
     *  It only has to be positive. During testing purposes, it was even possible to set the value to infinity
     * @param view
     */
    public void saveEntry(View view) {
        //First convert's textbox's text to a String, then to a Double and at the end rounds it to an Integer
        //so the app doesn't crash if user adds decimals
        TextView month = findViewById(R.id.editText5);
        Double monthD;
        int monthI;
        if (month.getText().length() != 0) {
            String monthS = month.getText().toString();
            monthD = Double.parseDouble(monthS);
            monthI = (int)Math.round(monthD);
        } else {
            monthI = 0;
        }

        //First convert's textbox's text to a String, then to a Double and at the end rounds it to an Integer
        //so the app doesn't crash if user adds decimals
        TextView day = findViewById(R.id.editText6);
        Double dayD;
        int dayI;
        if (day.getText().length() != 0) {
            String dayS = day.getText().toString();
            dayD = Double.parseDouble(dayS);
            dayI = (int)Math.round(dayD);
        } else {
            dayI = 0;
        }

        //First convert's textbox's text to a String, then to a Double and at the end rounds it to an Integer
        //so the app doesn't crash if user adds decimals
        TextView hour = findViewById(R.id.editText7);
        Double hourD;
        int hourI;
        if (hour.getText().length() != 0) {
            String hourS = hour.getText().toString();
            hourD = Double.parseDouble(hourS);
            hourI = (int)Math.round(hourD);
        } else {
            hourI = 0;
        }

        //Takes textbox's text as a string and then converts it to a Double
        TextView value = findViewById(R.id.editText8);
        double valueI;
        if (value.getText().length() != 0) {
            String valueS = value.getText().toString();
            valueI = Double.parseDouble(valueS);
        } else {
            valueI = 0;
        }

        //Checks if value is positive or not
        if (valueI != 0 && valueI > 0) {
            Toast.makeText(EntryActivity.this,"Added to chart",Toast.LENGTH_SHORT).show();

            dataHandler.add(dayI, monthI, hourI, valueI); //Adds the specified entry to the DataHandler

            String dataHandlerString = objectToString(dataHandler); //Convert's DataHandler to a string using Base64 encoding

            //Saves the converted DataHandler to SharedPreferences immeaditely
            SharedPreferences prefPut = getSharedPreferences("DataPref", Activity.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = prefPut.edit();
            prefEditor.putString("DataHandler", dataHandlerString);
            prefEditor.commit();


        } else {
            Log.d("EntryLogger", "Illegal value");
            Toast.makeText(EntryActivity.this,"Illegal value",Toast.LENGTH_SHORT).show();
        }

        //Following code just resets the textboxes
        final EditText editText5 =  findViewById(R.id.editText5);
        final EditText editText6 =  findViewById(R.id.editText6);
        final EditText editText7 =  findViewById(R.id.editText7);
        final EditText editText8 =  findViewById(R.id.editText8);

        editText5.setText("");
        editText6.setText("");
        editText7.setText("");
        editText8.setText("");
    }

    /**
     * Changes object to string. Uses Base64 to transform data.
     * @param object Serializable object
     * @return Serialized object
     */
    static public String objectToString(Serializable object) {
        String encoded = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
            encoded = new String(Base64.encodeToString(byteArrayOutputStream.toByteArray(),0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encoded;
    }

}
