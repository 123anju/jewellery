package com.JewelleryShop.Jewellery.Offercode;

import android.content.Context;
import android.widget.Toast;


import com.JewelleryShop.Jewellery.Cart;
import com.JewelleryShop.Jewellery.Interface.Ezycook;

/**
 * Created by Anju on 18-05-2018.
 */

public class EzyCook implements Ezycook {

    int final_money=0;
    // 200 to 499 flat 15%off
    @Override
    public int discountEzycook(Context context,float percentage, String Coupon_code, int minbill,int maxbill,int offeramount)
    {
        Toast.makeText(context, "200 to 499", Toast.LENGTH_SHORT).show();
        if(offeramount>=minbill && offeramount<=maxbill)
        {
            //   Toast.makeText(context, "200 to 499", Toast.LENGTH_SHORT).show();
            int s=100-15;
            final_money = (int) (Cart.total*s)/100;
        }
        return final_money;
    }
    // 500 to 999 flat 20%off
    @Override
    public int discountEzycook1(Context context,float percentage, String Coupon_code, int minbill,int maxbill, int offeramount) {
        Toast.makeText(context, "500 to 999", Toast.LENGTH_SHORT).show();
        if(offeramount>=minbill && offeramount<=maxbill)
        {
//            Toast.makeText(context, "200 to 499", Toast.LENGTH_SHORT).show();
            int s=100-20;
            final_money = (int) (Cart.total*s)/100;
        }
        return final_money;
    }
    // above 999/-flat 25%off
    @Override
    public int discountEzycook2(Context context,float percentage, String Coupon_code, int minbill, int offeramount)
    {
        Toast.makeText(context, "above 999", Toast.LENGTH_SHORT).show();
        if(offeramount>=minbill)
        {
//            Toast.makeText(context, "200 to 499", Toast.LENGTH_SHORT).show();
            int s=100-25;
            final_money = (int) (Cart.total*s)/100;
        }
        return final_money;
    }
}
