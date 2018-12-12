package com.JewelleryShop.Jewellery.Interface;

import android.content.Context;

/**
 * Created by saurabh omer on 09-May-18.
 */

public interface Biryani247  {
    // happy hours get flat  discount 10% Off 4 to 6 pm
     int happyHoursBiriyani247(Context con, int starttime, int Endtime, String am_pm, String Coupon_code);
     //boolean happyHoursBiriyani247(Context con);
    // get flat discount
    boolean discount(float percentage, String Coupon_code, int minbill);
    // GET your first
    boolean firstBiriyani(int minbill, String Coupon_code);
    // get deals with preorder 5%,10%
    boolean preorder(int hour, int min, int sec, String am_pm, int amt, String Coupon_code);
}
