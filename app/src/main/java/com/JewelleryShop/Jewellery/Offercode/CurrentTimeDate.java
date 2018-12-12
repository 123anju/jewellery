package com.JewelleryShop.Jewellery.Offercode;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by saurabh omer on 09-May-18.
 */

public class CurrentTimeDate {
    int min,sec,hours;
    public CurrentTimeDate()
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String current_time=sdf.format(cal.getTime()).toString();
        String[] array1= current_time.split(":");
        SetTime(Integer.parseInt(array1[0]),Integer.parseInt(array1[1]),Integer.parseInt(array1[2]));



    }

    public void SetTime( int hours,int min, int sec) {
        this.min = min;
        this.sec = sec;
        this.hours = hours;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getSec() {
        return sec;
    }

    public void setSec(int sec) {
        this.sec = sec;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
