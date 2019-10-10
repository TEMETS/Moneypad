package com.example.moneypadv2;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Data implements Serializable {
    private Calendar calendar;
    private double value;

    public Data(Calendar calendar, double value) {
        this.calendar = calendar;
        this.value = value;
    }

    public Calendar getTime() {
        return this.calendar;
    }

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

    @Override
    public String toString() {
        String minutesS = Integer.toString(this.calendar.get(Calendar.MINUTE));
        if (minutesS.length() <= 1){
            minutesS = "0" + minutesS;
        }


        return "Date: " + this.calendar.get(Calendar.DAY_OF_MONTH) + "." + (this.calendar.get(Calendar.MONTH) + 1) + "\nTime: " + this.calendar.get(Calendar.HOUR_OF_DAY) + ":" + minutesS + "\nAmount: " + this.value;
    }

}
