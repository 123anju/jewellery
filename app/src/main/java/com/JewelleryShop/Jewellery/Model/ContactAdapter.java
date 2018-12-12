package com.JewelleryShop.Jewellery.Model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.JewelleryShop.Jewellery.R;

import java.util.ArrayList;

/**
 * Created by Anju on 14-04-2018.
 */

public class ContactAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Contact> contacts;

    public ContactAdapter(Context context, ArrayList<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
            view = View.inflate(context, R.layout.contact_item, null);

        ImageView images = (ImageView)view.findViewById(R.id.image_view);
        TextView name=(TextView)view.findViewById(R.id.Name);
        TextView city=(TextView)view.findViewById(R.id.City);
        TextView phone=(TextView)view.findViewById(R.id.Phone);
        TextView email=(TextView)view.findViewById(R.id.Email);

        Contact contact = contacts.get(i);

        images.setImageResource(contact.getImage());
        name.setText(contact.getName());
        city.setText(contact.getCity());
        phone.setText(contact.getPhone());
        email.setText(contact.getEmail());


        return view;
    }
}
