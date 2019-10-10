package com.example.moneypadv2;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class DataHandler implements Serializable {

    private ArrayList<Data> data;
    private Data lastData; // Used to get most recent time and value

    private static final DataHandler ourInstance = new DataHandler(500);

    public static DataHandler getInstance() {
        return ourInstance;
    }

    private DataHandler(double amount) {
        this.data = new ArrayList<>(); // Create an empty Data-list
    }

    public void overrideData(DataHandler dataHandler) {

        ArrayList<Data> tempData = dataHandler.data;

        this.data = tempData;
    }

    public void clearData() {
        this.data.clear();
    }

    public void setLast(Data data) {
        this.lastData = data;
    }

    public void add(double value) {

        if (value >= 0) {
            Calendar time = Calendar.getInstance();

            this.data.add(new Data(time, value));
        }

    }

    public void add(int day, int month, int hour, double value) {

        if (value >= 0) {
            Calendar time = Calendar.getInstance();
            time.add(Calendar.DAY_OF_MONTH, day);
            time.add(Calendar.MONTH, month);
            time.add(Calendar.HOUR_OF_DAY, hour);

            this.data.add(new Data(time, value));
        }
    }

    public String get(int i) {
        return this.data.get(i).toString();
    }

    public void getAll() {
        for (Data data : this.data) {
            System.out.println(data + "\n");
        }
    }

    public double spentToday() {
        Calendar today = Calendar.getInstance();
        System.out.println("Looking for: " + today.get(Calendar.DAY_OF_MONTH));

        Double spent = 0.0;

        for (Data data : this.data) {
            Calendar test = data.getTime();

            if ((today.get(Calendar.DAY_OF_MONTH) == test.get(Calendar.DAY_OF_MONTH)) && (today.get(Calendar.MONTH) == test.get(Calendar.MONTH)) && (today.get(Calendar.YEAR) == test.get(Calendar.YEAR))) {
                spent += data.getValue();
            }
        }

        return spent;
    }

    public double spentYesterday() {
        Calendar today = Calendar.getInstance();
        System.out.println("Looking for: " + (today.get(Calendar.DAY_OF_MONTH)-1));

        Double spent = 0.0;

        for (Data data : this.data) {
            Calendar test = data.getTime();

            if ((today.get(Calendar.DAY_OF_MONTH) -1) == test.get(Calendar.DAY_OF_MONTH) && (today.get(Calendar.MONTH) == test.get(Calendar.MONTH)) && (today.get(Calendar.YEAR) == test.get(Calendar.YEAR))) {
                spent += data.getValue();
            }
        }

        return spent;
    }

    public double spentAll() {
        Double spent = 0.0;

        for (Data data : this.data) {
            spent += data.getValue();
        }

        return spent;
    }

    public int getSize() {
        return this.data.size();
    }

}
