package com.example.moneypadv2;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Holds a private Calendar-object to store time
 * and a private double value to store money as a value
 */
public class Data implements Serializable {
    private Calendar calendar;
    private double value;

    public Data(Calendar calendar, double value) {
        this.calendar = calendar;
        this.value = value;
    }

    /**
     * Returns the Calendar object of Data
     * @return Calendar
     */
    public Calendar getTime() {
        return this.calendar;
    }

    /**
     * Gets the money value of Data
     * @return double
     */
    public double getValue() {
        return this.value;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.value) ^ (Double.doubleToLongBits(this.value) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Data other = (Data) obj;
        if (this.calendar != other.calendar) {
            return false;
        }
        return true;
    }

    /**
     * Returns all necessary information of the Data-object in format Date: day.month Time: hour:minute
     * @return String
     */
    @Override
    public String toString() {
        String minutesS = Integer.toString(this.calendar.get(Calendar.MINUTE));
        if (minutesS.length() <= 1){ //If the represented minute is 1 digit long add a zero in front of it
            minutesS = "0" + minutesS; //Makes minutes look like xx:0x instead of xx:x
        }


        return "Date: " + this.calendar.get(Calendar.DAY_OF_MONTH) + "." + (this.calendar.get(Calendar.MONTH) + 1) + "\nTime: " + this.calendar.get(Calendar.HOUR_OF_DAY) + ":" + minutesS + "\nAmount: " + this.value;
    }

}
