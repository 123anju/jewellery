package com.JewelleryShop.Jewellery.Model;

import com.JewelleryShop.Jewellery.R;

import java.util.ArrayList;

/**
 * Created by Anju on 14-04-2018.
 */

public class ContactDetails {
    public static ArrayList<Contact> getList()
    {

        ArrayList<Contact> details= new ArrayList<>();
        details.add(new Contact(R.drawable.vedant,"Vedant Naik","Designation: CEO ","",""));
        details.add(new Contact(R.drawable.sidhajyoti,"Sidhajyoti","Designation: CTO ","",""));
        details.add(new Contact(R.drawable.biswadeep,"Biswadeep Khan","Designation: Data Scientist ","",""));
        details.add(new Contact(R.drawable.rohit,"Rohit Thacker","Designation:CFO ","",""));
        return details;
    }
}
