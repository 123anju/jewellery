package com.JewelleryShop.Jewellery.Offercode;

import android.content.Context;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.JewelleryShop.Jewellery.Interface.Biryani247;
import com.JewelleryShop.Jewellery.Model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by saurabh omer on 09-May-18.
 */

public class Biriyani247offer implements Biryani247 {

    private FirebaseDatabase database;
    private DatabaseReference category;
    int final_money=0;
    int flag=0;
    //happy hours get flat  discount 10% Off

    //public boolean happyHoursBiriyani247(int hour, int min, int sec, String am_pm, String Coupon_code) {

    public int happyHoursBiriyani247(final Context con, int hour, int min,  String Coupon_code) {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String current_time=sdf.format(cal.getTime()).toString();
        database = FirebaseDatabase.getInstance();

        category = database.getReference("userCoupon/"+ User.Phone);
        category.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // decrese value of coupun
                //Toast.makeText(con, ""+dataSnapshot.child("FRE247").getValue(), Toast.LENGTH_SHORT).show();
                int activate=Integer.parseInt(dataSnapshot.child("FRE247").getValue().toString());

                if(activate!=0 && flag==0)
                {
                    Toast.makeText(con, ""+(activate-1), Toast.LENGTH_SHORT).show();
                    category.child("FRE247").setValue(activate-1);
                    flag=1;
                }



//                dataSnapshot.child("FRE247").getValue();
               // dataSnapshot.getValue(String.class);
                //int status = Integer.parseInt(a);
            //    Toast.makeText(con, "jsk"+a, Toast.LENGTH_SHORT).show();
//                if(status!=0)
//                {
//                    //reset value of firebase
////                    status=status-1;
////                     category.child("FRE247").setValue((status));
////                    Toast.makeText(con, "code applied", Toast.LENGTH_SHORT).show();
////                    // change 500 to cart.total
////                    final_money=500+(500/10);
//
//                    // write your logic
//
//                }
//                else
//                {
//                    Toast.makeText(con, "NOT applicable for this code", Toast.LENGTH_SHORT).show();
//                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return final_money;
        //int offer=new AuthFirebase().getConcetion(User.Phone,context,"FRE247");

    }


    @Override
    public int happyHoursBiriyani247(Context con, int starttime, int Endtime, String am_pm, String Coupon_code) {
        return 0;
    }

    @Override
    public boolean discount(float percentage, String Coupon_code, int minbill)
    {


        return false;
    }

    @Override
    public boolean firstBiriyani(int minbill, String Coupon_code)
    {

        return false;
    }

    @Override
    public boolean preorder(int hour, int min, int sec, String am_pm, int amt, String Coupon_code) {
        return false;
    }
}
