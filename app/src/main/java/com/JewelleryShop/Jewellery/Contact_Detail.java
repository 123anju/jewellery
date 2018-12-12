package com.JewelleryShop.Jewellery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.JewelleryShop.Jewellery.Model.Contact;
import com.JewelleryShop.Jewellery.Model.ContactAdapter;
import com.JewelleryShop.Jewellery.Model.ContactDetails;

import java.util.ArrayList;

public class Contact_Detail extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Contact> contacts;
    private ContactAdapter contactAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact__detail);

        listView = (ListView)findViewById(R.id.listview);
        contacts= ContactDetails.getList();
        contactAdapter=new ContactAdapter(Contact_Detail.this,contacts);
        listView.setAdapter(contactAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Contact Detail");

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
