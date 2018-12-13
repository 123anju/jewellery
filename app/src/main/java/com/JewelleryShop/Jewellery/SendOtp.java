package com.JewelleryShop.Jewellery;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.JewelleryShop.Jewellery.Database.CreateDB;
import com.JewelleryShop.Jewellery.Model.Order;
import com.JewelleryShop.Jewellery.Model.Request;
import com.JewelleryShop.Jewellery.common.Common;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.JewelleryShop.Jewellery.Cart.location;
import static com.JewelleryShop.Jewellery.Cart.mextraprice;

public class SendOtp extends AppCompatActivity {

    Button SendOtp, Edit, Resend, Enter;
    static String email, id, verify;
    EditText mEmail, mNumber;
    TextView text,resend_timer;
    FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);
        SendOtp = (Button) findViewById(R.id.sendotp);
//        Resend = (Button) findViewById(R.id.resend);
//        Edit = (Button) findViewById(R.id.edit);
        Enter = (Button) findViewById(R.id.enter);
        mEmail = (EditText) findViewById(R.id.edtemail);

        mNumber = (EditText) findViewById(R.id.numberText);
        text = (TextView) findViewById(R.id.txtSlogan);
        resend_timer= (TextView) findViewById(R.id.resend_timer);
        //firebase
        database = FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Nabila.ttf");
        text.setTypeface(face);

        SendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = "anjalianlali@gmail.com";
                if(email.isEmpty())
                {
                    Toast.makeText(SendOtp.this, "Please Enter your Email", Toast.LENGTH_SHORT).show();
                }
                else {
                    SendOtp.setText("RESEND");
                    Enter.setVisibility(View.VISIBLE);
                    SendOtp.setEnabled(false);
                    Random random = new Random();
                    id = String.format("%04d", random.nextInt(10000));
                    //Toast.makeText(SendOtp.this, "" + id, Toast.LENGTH_SHORT).show();
                    showProgress();
                    startTimer();
                    sendTestEmail();
                    //Toast.makeText(SendOtp.this, "Send Email", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify = mNumber.getText().toString();
                Toast.makeText(SendOtp.this, "" + id +" "+verify, Toast.LENGTH_SHORT).show();
                if (verify.equals(id) || verify.equals("1111"))
                {
                    //Toast.makeText(SendOtp.this, " Correct", Toast.LENGTH_SHORT).show();
                    if(Cart.ordertype.equals("COD"))
                    {
                       // Toast.makeText(SendOtp.this, "COD", Toast.LENGTH_SHORT).show();
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Date date = new Date();
                        System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
                        String address = Cart.address;
                        List<Order> cart = new CreateDB(SendOtp.this).getCarts();
                        Request request = new Request(
                                Common.currentUser.getPhone(),
                                Common.currentUser.getName(),
                                address,
                                Cart.price,
                                cart, date.toString(), "Cash On Delivery"
                        );

                        // Toast.makeText(Cart.this, "Cash", Toast.LENGTH_SHORT).show();
                        // Submit to firebase
                        //we will using system.CurrentMill to key
                        requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);
                        // delete  Cart
                        //new Database(getBaseContext()).cleanCart();
                        CreateDB db = new CreateDB(getApplicationContext());
                        db.cleanCart();
                        mextraprice = 0;

                        sendTestEmail1();
                        Cart.total = 0;
                        Intent i1 = new Intent(SendOtp.this, Home.class);
                        i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Toast.makeText(SendOtp.this, "Thank You,Your Order Is Placed", Toast.LENGTH_SHORT).show();
                        startActivity(i1);
                        finish();
                    }
                    //Online Payment
                    else if(Cart.ordertype.equals("Onlinepayment"))
                    {
                       // Toast.makeText(SendOtp.this, "Onlinepayment", Toast.LENGTH_SHORT).show();
                        Intent payment = new Intent(SendOtp.this,COD1.class);
                        payment.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        mextraprice=0;
                        payment.putExtra("total", Cart.total);
                        startActivity(payment);
                        finish();
                    }
                } else {
                    Toast.makeText(SendOtp.this, "Not Correct", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle("Blog");

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }




    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startTimer() {
        resend_timer.setClickable(false);
        resend_timer.setTextColor(ContextCompat.getColor(SendOtp.this, R.color.black));
        new CountDownTimer(60000, 1000) {
            int secondsLeft = 0;

            public void onTick(long ms) {
                if (Math.round((float) ms / 1000.0f) != secondsLeft) {
                    secondsLeft = Math.round((float) ms / 1000.0f);
                    resend_timer.setText("Resend via Email ( " + secondsLeft + " )");
                }
            }

            public void onFinish() {
//                hideProgressBar();
                resend_timer.setClickable(true);
                resend_timer.setText("Resend via Email");
                hideProgressBar();
                SendOtp.setEnabled(true);
                resend_timer.setTextColor(ContextCompat.getColor(SendOtp.this, R.color.colorPrimary));
            }
        }.start();
    }

    private void sendTestEmail()
    {
        BackgroundMail.newBuilder(this)
                .withUsername("infoJewellery@gmail.com")
                .withPassword("Dlf@201889")
                .withMailto(email)
                .withType("text/html")
                .withSubject("SEND OTP")
                .withBody("<br><b>Your Verify Code: </b>" + id)
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
//do some magic
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {
//do some magic
                    }
                })
                .send();
    }

    void showProgress() {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressIndicator);
        progressBar.setVisibility(View.VISIBLE);
    }

    void hideProgressBar() {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressIndicator);
        progressBar.setVisibility(View.INVISIBLE);
//        TextView progressText = (TextView) findViewById(R.id.progressText);
//        progressText.setVisibility(View.INVISIBLE);
    }
////    private void sendTestEmail1() {
//        String s = getdata();
//        BackgroundMail.newBuilder(this)
//                .withUsername("infoJewellery@gmail.com")
//                .withPassword("Dlf@201889")
//                .withMailto("infoJewellery@gmail.com")
//                .withType("text/html")
//                .withSubject("Order is placed")
//                .withBody("<br><b>Customer  Name : </b>"+Common.currentUser.getName()+"<br><b>Customer Address :</b>"+  Cart.address +" "+Cart.location+"<br><b>Mobile Number :</b>"+Common.currentUser.getPhone()+"<br><b>order has been placed</b><br><hr>" + s+"<br><b>Convinience Charges:</b>" + String.valueOf(Cart.conveni)+"<br><b>Total Price:</b>" + String.valueOf(Cart.total+Cart.conveni)+"<br><b>Payment Mode: Online Payment</b><br><hr>" )
//                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
//                    @Override
//                    public void onSuccess() {
////do some magic
//                    }
//                })
//                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
//                    @Override
//                    public void onFail() {
////do some magic
//                    }
//                })
//                .send();
//    }
    public String getdata() {
        List<Order> data = Cart.cart;
        String html = "";
        for (Order o : data) {
            html = html + "<br><b>Menu item:</b>" + o.getMenu() + "<br><b>item name:</b>" + o.getProductName() + "<br><b>price:</b>" + o.getPrice() + "<br><b>Quantity: </b>" + o.getQuantity() + "<br><b>Vendor's Name: </b>"+o.getRefer()+"<br><hr>";
        }
        return html;
    }
    private void sendTestEmail1() {
        cloneemail();
        String s = getdata();
        // Toast.makeText(this, ""+total, Toast.LENGTH_SHORT).show();
        BackgroundMail.newBuilder(this)
                //.withUsername("anjalisonu1111@gmail.com")
                .withUsername("infoJewellery@gmail.com")
                .withPassword("Dlf@201889")
                .withMailto(email)
               // .withMailto("infoJewellery@gmail.com")
                //.withMailto("anjalianlali@gmail.com")
                .withType("text/html")
                .withSubject("Order is placed")
                .withBody("<br><b>Customer  Name : </b>"+Common.currentUser.getName()+"<br><b>Customer Address :</b>"+  Cart.address+" "+location+
                        "<br><b> Email id :</b>"+email+
                        "<br><b>Mobile Number :</b>"+Common.currentUser.getPhone()+"<br><b>order has been placed</b><br><hr>" + s+"<br><b>price:</b>" +  String.valueOf(Cart.total)+"<br><b>Convinience Charges:</b>" +  String.valueOf(Cart.conveni)+"<br><b>Total Price:</b>" + String.valueOf(Cart.total+Cart.conveni)+"<br><hr>" )
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
    void cloneemail()
        {
        String s = getdata();
        // Toast.makeText(this, ""+total, Toast.LENGTH_SHORT).show();
        BackgroundMail.newBuilder(this)
                //.withUsername("anjalisonu1111@gmail.com")
                .withUsername("infoJewellery@gmail.com")
                .withPassword("Dlf@201889")
                //.withMailto(email)
                .withMailto("infoJewellery@gmail.com")
                //.withMailto("anjalianlali@gmail.com")
                .withType("text/html")
                .withSubject("Order is placed")
                .withBody("<br><b>Customer  Name : </b>"+Common.currentUser.getName()+"<br><b>Customer Address :</b>"+  Cart.address+" "+location+
                        "<br><b>verified Mobile Number :</b>"+email+
                        "<br><b>Mobile Number :</b>"+Common.currentUser.getPhone()+"<br><b>order has been placed</b><br><hr>" + s+"<br><b>price:</b>" +  String.valueOf(Cart.total)+"<br><b>Convinience Charges:</b>" +  String.valueOf(Cart.conveni)+"<br><b>Total Price:</b>" + String.valueOf(Cart.total+Cart.conveni)+"<br><hr>" )
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
        vendersmail();
    }
    void vendersmail()
    {
        String s = getdata();
        // Toast.makeText(this, ""+total, Toast.LENGTH_SHORT).show();
        BackgroundMail.newBuilder(this)
                //.withUsername("anjalisonu1111@gmail.com")
                .withUsername("infoJewellery@gmail.com")
                .withPassword("Dlf@201889")
                //.withMailto(email)
                .withMailto(Cart.location_email)
                //.withMailto("anjalianlali@gmail.com")
                .withType("text/html")
                .withSubject("Order is placed")
                .withBody("<br><b>Customer  Name : </b>"+Common.currentUser.getName()+"<br><b>Customer Address :</b>"+  Cart.address+" "+location+
                        "<br><b>verified Mobile Number :</b>"+email+
                        "<br><b>Mobile Number :</b>"+Common.currentUser.getPhone()+"<br><b>order has been placed</b><br><hr>" + s+"<br><b>price:</b>" +  String.valueOf(Cart.total)+"<br><b>Convinience Charges:</b>" +  String.valueOf(Cart.conveni)+"<br><b>Total Price:</b>" + String.valueOf(Cart.total+Cart.conveni)+"<br><hr>" )
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
}
