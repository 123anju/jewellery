package com.JewelleryShop.Jewellery.Offercode;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by saurabh omer on 09-May-18.
 */

public class AuthFirebase {

    FirebaseDatabase database;
    static int status=0;
    DatabaseReference category;
    public int  getConcetion(String mobileNumber, final Context con, String couponcode)
    {
        database = FirebaseDatabase.getInstance();
        category = database.getReference("userCoupon/"+ mobileNumber);
        category.child("FRE247").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String a=dataSnapshot.getValue(String.class);
                status=Integer.parseInt(a);
                Toast.makeText(con, "jsk"+a, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return status;

    }

}

