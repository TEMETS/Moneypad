package com.example.moneypadv2;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class DataHandler implements Serializable {

    private ArrayList<Data> data;

    private static final DataHandler ourInstance = new DataHandler(500);

    public static DataHandler getInstance() {
        return ourInstance;
    }

    /**
     * Creates a DataHandler with a specified amount of balance
     * (balance not currently implemented)
     * @param amount
     */
    private DataHandler(double amount) {
        this.data = new ArrayList<>(); // Create an empty Data-list
    }

    /**
     * Used to synchronise data with SharedPreferences
     * It takes temporary
     * @param dataHandler
     */
    public void overrideData(DataHandler dataHandler) {

        ArrayList<Data> tempData = dataHandler.data;

        this.data = tempData;
    }

    /**
     * Clears ArrayList
     * Used in resetMemory
     */
    public void clearData() {
        this.data.clear();
    }

    /**
     *  Adds a Data entry to DataHandler's ArrayList
     * @param value A value to present spent money
     */
    public void add(double value) {

        if (value >= 0) {
            Calendar time = Calendar.getInstance();

            this.data.add(new Data(time, value));
        }

    }

    /**
     *  Adds a specified Data entry to ArrayList
     *      using Calendar-object and a money value.
     *      Default time is current time.
     *      In example dataHandler.add(0,0,-1,5) adds an entry with current time except one hour earlier and a money value of 5
     *
     * @param day   Change day by incrementing or decrementing
     * @param month Change month by incrementing or decrementing
     * @param hour  Change hour by incrementing or decrementing
     * @param value A value to present spent money
     */
    public void add(int day, int month, int hour, double value) {

        if (value >= 0) {
            Calendar time = Calendar.getInstance();
            time.add(Calendar.DAY_OF_MONTH, day);
            time.add(Calendar.MONTH, month);
            time.add(Calendar.HOUR_OF_DAY, hour);

            this.data.add(new Data(time, value));
        }
    }

    /**
     * Gets the information of Data in a simple string which holds date and value.
     * @param i ArrayList's index
     * @return Information of Data
     */
    public String get(int i) {
        return this.data.get(i).toString();
    }

    /**
     * Prints every element of ArrayList.
     * Method not currently used.
     */
    public void getAll() {
        for (Data data : this.data) {
            System.out.println(data + "\n");
        }
    }

    /**
     * Returns the spent amount of money from today.
     * @return double
     */
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

    /**
     * Returns the spent amount of money from yesterday.
     * @return double
     */
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

    /**
     * Returns the spent amount of money from all the time.
     * @return double
     */
    public double spentAll() {
        Double spent = 0.0;

        for (Data data : this.data) {
            spent += data.getValue();
        }

        return spent;
    }

    /**
     * Return the size of the ArrayList
     * @return int
     */
    public int getSize() {
        return this.data.size();
    }

}
