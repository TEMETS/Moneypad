package com.example.moneypadv2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

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

    public void saveEntry(View view) {
        TextView month = findViewById(R.id.editText5);
        int monthI;
        if (month.getText().length() != 0) {
            String monthS = month.getText().toString();
            monthI = Integer.parseInt(monthS);
        } else {
            monthI = 0;
        }

        TextView day = findViewById(R.id.editText6);
        int dayI;
        if (day.getText().length() != 0) {
            String dayS = day.getText().toString();
            dayI = Integer.parseInt(dayS);
        } else {
            dayI = 0;
        }

        TextView hour = findViewById(R.id.editText7);
        int hourI;
        if (hour.getText().length() != 0) {
            String hourS = hour.getText().toString();
            hourI = Integer.parseInt(hourS);
        } else {
            hourI = 0;
        }

        TextView value = findViewById(R.id.editText8);
        int valueI;
        if (value.getText().length() != 0) {
            String valueS = value.getText().toString();
            valueI = Integer.parseInt(valueS);
        } else {
            valueI = 0;
        }

        if (valueI != 0 && valueI > 0) {
            dataHandler.add(dayI, monthI, hourI, valueI);


            String dataHandlerString = objectToString(dataHandler);

            //System.out.println(dataHandlerString);

            SharedPreferences prefPut = getSharedPreferences("DataPref", Activity.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = prefPut.edit();
            prefEditor.putString("DataHandler", dataHandlerString);
            prefEditor.commit();


        } else {
            Log.d("EntryLogger", "Illegal value");
        }

        //TextView test = findViewById(R.id.textView7);
        //test.setText(Integer.toString(monthI));

        //int month = Integer.getInteger(monthS.getText().toString());

        //dataHandler.add(50);
    }

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
