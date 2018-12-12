package com.JewelleryShop.Jewellery.Interface;

import android.content.Context;

/**
 * Created by saurabh omer on 09-May-18.
 */

public interface Ezycook
{
    // 200 to 499 off 15%
    int discountEzycook(Context context,float percentage, String Coupon_code, int minbill, int maxbill,int offeramount);

    //500 to 999 off 20%

    int discountEzycook1(Context context,float percentage,String Coupon_code, int minbill,int maxbill,int offeramount);

    //above 999/-Flat 25% off

    int discountEzycook2(Context context, float percentage, String Coupon_code, int minbill, int offeramount);

}
