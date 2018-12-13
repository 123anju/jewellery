package com.JewelleryShop.Jewellery;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.JewelleryShop.Jewellery.Model.Request;
import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.JewelleryShop.Jewellery.Database.CreateDB;
import com.JewelleryShop.Jewellery.Model.Order;
import com.JewelleryShop.Jewellery.ViewHolder.CartAdapter;
import com.JewelleryShop.Jewellery.common.Common;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import info.hoang8f.widget.FButton;

public class Cart extends AppCompatActivity {
    static RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference requests;
    static TextView txtTotalPrice;
    static TextView extraprice;
    static TextView foodcharge;
    FButton btnPlace;
    static String location=" ";
    ImageButton deleteItem;
    static CartAdapter adapter;
    static int REQ_CODE=1;
    public static int total=0;
    static String price,address,ordertype;
    String amount;
    static int mextraprice=0,conveni=0;
    public static List<Order> cart = new ArrayList<>();
    static String address1;
    static String location_email="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        //firebase
        database = FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");
        amount = String.valueOf(Cart.total);
        //init
        recyclerView =(RecyclerView) findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        txtTotalPrice = (TextView) findViewById(R.id.total);
        extraprice=(TextView)findViewById(R.id.extracharge);
        foodcharge=(TextView)findViewById(R.id.foodcharge);
        btnPlace = (FButton) findViewById(R.id.btnPlaceOrder);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Cart");

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }



        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //Create new Request
                if(cart.size()> 0)
                    showAlertDialog();
                else
                    Toast.makeText(Cart.this, "Your Cart is Empty!!!", Toast.LENGTH_SHORT).show();

            }
        });
        loadListFood();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete1, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.delete:
                if (Cart.cart.size() == 0) {
                    Toast.makeText(this, "No Item In the Cart", Toast.LENGTH_SHORT).show();
                } else {


                    //Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                    builder.setMessage("Do you want to delete all item in cart")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Cart.cart.clear();
                                    new CreateDB(Cart.this).cleanCart();
                                    adapter = new CartAdapter(cart, Cart.this);
                                    adapter.notifyDataSetChanged();
                                    recyclerView.setAdapter(adapter);
                                    refresh();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                }
                            });
                    android.app.AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.show();
                }
                    return true;
                    case android.R.id.home:
                        finish();
                        return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("One More Step!");
        if(cart.get(0).getRefer().equals("biriyani247") || cart.get(0).getRefer().equals("biryani247") || cart.get(0).getRefer().equals("Biryani247") || cart.get(0).getRefer().equals("BIRYANI247")  )
        {
            location="";
            location_email="info@biryani247.com";
        }
        else if(cart.get(0).getRefer().equals("fitmeals"))
        {
            location="";
            location_email="info@fitmeals.co.in";
        }
        else if(cart.get(0).getRefer().equals("esycook") ||cart.get(0).getRefer().equals("Ezycook"))
        {
            location="";
            location_email="info@ezycook.in";
        }
        else if(cart.get(0).getRefer().equals("railrestro"))
        {
            location="";

        }
        alertDialog.setMessage("Enter Your Address "+location);
        final EditText edtAddress = new EditText(Cart.this);
        LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams
                (
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtAddress.setLayoutParams(lp);
       // Toast.makeText(this, ""+foodcharge.getText().toString(), Toast.LENGTH_SHORT).show();
        if(foodcharge.getText().toString().equals("0"))
        {
            extraprice.setText("0");
        }

        alertDialog.setView(edtAddress); // Add edit Text to alert dialog
        //alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        price=txtTotalPrice.getText().toString();
        alertDialog.setPositiveButton("Pay Now", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(edtAddress.getText().toString().equals(""))
                {

                    Toast.makeText(Cart.this, "Address is Empty, Please try again", Toast.LENGTH_SHORT).show();
    //                alertDialog.show();
                }
                else {

                    address = edtAddress.getText().toString();
                    ordertype="Onlinepayment";
                    Intent intent=new Intent(Cart.this,OnlinePayment.class);
                    startActivity(intent);


                }
            }
        });

        alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }
    public String getdata()
    {
        List<Order> data = Cart.cart;
        String html = "";
        for (Order o : data)
        {
            html = html + "<br><b>Menu item:</b>" + o.getMenu() + "<br><b>item name:</b>" + o.getProductName() + "<br><b>price:</b>" + o.getPrice() + "<br><b>Quantity: </b>" + o.getQuantity() + "<br><b>Vendor's Name: </b>"+o.getRefer()+"<br><b>Payment Mode: Cash On Delivery</b><br><hr>";
        }
        return html;
    }

    private void sendTestEmail() {
        String s = getdata();
       // Toast.makeText(this, ""+total, Toast.LENGTH_SHORT).show();
        BackgroundMail.newBuilder(this)
                //.withUsername("anjalisonu1111@gmail.com")
                .withUsername("infoJewellery@gmail.com")
                .withPassword("Dlf@201889")
                .withMailto("infoJewellery@gmail.com")
                //.withMailto("saurabhomer258@gmail.com")
                //.withMailto("anjalianlali@gmail.com")
                .withType("text/html")
                .withSubject("Order is placed")
                .withBody("<br><b>Customer  Name : </b>"+Common.currentUser.getName()+"<br><b>Customer Address :</b>"+  Cart.address+" "+location+"<br><b>Mobile Number :</b>"+Common.currentUser.getPhone()+"<br><b>order has been placed</b><br><hr>" + s+"<br><b>Convinience Charges:</b>" + extraprice.getText().toString()+"<br><b>Total Price:</b>" + txtTotalPrice.getText().toString()+"<br><hr>" )
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        //Toast.makeText(Cart.this, "as", Toast.LENGTH_SHORT).show();
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail()
                    {
                        //  Toast.makeText(Cart.this, "sadh", Toast.LENGTH_SHORT).show();
//do some magic
                    }
                })
                .send();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
              //  Toast.makeText(this, "okk", Toast.LENGTH_SHORT).show();
    }
    public  void loadListFood() {
        //cart =new Database(this).getCarts();
        cart =new CreateDB(this).getCarts();
        adapter =new CartAdapter(cart,this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        //calculate total
        total=0;
        for(Order order:cart) {

            total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));

        }
    }
    //Press ctrl+o

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(Common.DELETE))
            deleteCart(item.getOrder());

        return true;
    }

    public void deleteCart(int position) {
       // We will remove item at List<Order>by position
        cart.remove(position);
        //After that , we will delete all data  from  SQLite
        new CreateDB(this).cleanCart();
        //And final , we will update new data from List<Order> to SQlite
        for(Order item:cart)
            new CreateDB(this).addTOCard(item);
        //Refresh
        loadListFood();
    }
    public static void deleteCart1(String id, Context context) {
        // We will remove item at List<Order>by position
        cart.clear();
        new CreateDB(context).deletetoId(id);
        cart =new CreateDB(context).getCarts();
           // Toast.makeText(context, ""+cart, Toast.LENGTH_SHORT).show();
        adapter =new CartAdapter(cart,context);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        refresh();
    }
    public static void refresh()
    {
        total=0;
        for(Order order:cart) {

            total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));

        }
        mextraprice=0;
        if(total>0 && total<100)
        {
            mextraprice=5;
            // Toast.makeText(this, "a", Toast.LENGTH_SHORT).show();
        }
        else if(total>=100 && total<200)
        {
            mextraprice=10;
            //Toast.makeText(this, "b", Toast.LENGTH_SHORT).show();
        }
        else if(total>=200 && total<300)
        {
            mextraprice=15;
            //Toast.makeText(this, "c", Toast.LENGTH_SHORT).show();
        }
        else if(total>=300)
        {
            mextraprice=20;
            //Toast.makeText(this, "d", Toast.LENGTH_SHORT).show();
        }

        extraprice.setText(String.valueOf(mextraprice));
        foodcharge.setText(String.valueOf(total));
        txtTotalPrice.setText(String.valueOf(total+mextraprice));
        conveni=mextraprice;

    }
}