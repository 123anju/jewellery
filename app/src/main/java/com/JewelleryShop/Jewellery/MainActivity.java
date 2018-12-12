package com.JewelleryShop.Jewellery;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.JewelleryShop.Jewellery.Model.User;
import com.JewelleryShop.Jewellery.common.Common;

public class MainActivity extends AppCompatActivity
{
    static int start=0,a=0;
    Button btnSignUp,btnSignIn;
    TextView txtSlogan;
    ViewFlipper viewflipper;

    int[] image={R.drawable.dow,R.drawable.dow1,R.drawable.dow3,R.drawable.dow11,R.drawable.down22};
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewflipper=(ViewFlipper) findViewById(R.id.viewFlipper);

        SharedPreferences sharedpreferences = getSharedPreferences("abc", Context.MODE_PRIVATE);
        String session=sharedpreferences.getString("mno","");
        String name=sharedpreferences.getString("mname", "");

        if(start==0 && session=="" )
        {
            a=1;
           // Toast.makeText(this, "s", Toast.LENGTH_SHORT).show();
                Intent splash=new Intent(this,Splash.class);
                startActivity(splash);
        }
        a=0;
        sharedpreferences = getSharedPreferences("abc", Context.MODE_PRIVATE);
        session=sharedpreferences.getString("mno","");
        name=sharedpreferences.getString("mname", "");
        if(session!="" && name!="")
        {
            //Toast.makeText(this, "s", Toast.LENGTH_SHORT).show();
            //  Toast.makeText(MainActivity.this, "session start"+session+name, Toast.LENGTH_SHORT).show();
            User user=new User(name,session);
            Common.currentUser.Phone=session;
            // Toast.makeText(this, ""+session, Toast.LENGTH_SHORT).show();
          //  SuperToast.create(this, "Rail Restro - COMING SOON", 3*1000,Style.deepOrange()).show();
            Common.currentUser = user;
            Intent i=new Intent(MainActivity.this,Home.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        else {


            //  Toast.makeText(MainActivity.this, "ffrf::"+""+session, Toast.LENGTH_SHORT).show();
            btnSignUp = (Button) findViewById(R.id.btnSignjoin);
            btnSignIn = (Button) findViewById(R.id.btnSignIn);
            txtSlogan = (TextView) findViewById(R.id.txtSlogan);

            for (int i = 0; i < image.length; i++) {
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(image[i]);
                viewflipper.addView(imageView);

            }

            viewflipper.setFlipInterval(2000);
            viewflipper.setAutoStart(true);

            Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Nabila.ttf");
            txtSlogan.setTypeface(face);

            btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent signUp = new Intent(MainActivity.this, SignUp.class);
                    startActivity(signUp);
                }
            });

            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent signIn = new Intent(MainActivity.this, SignIn.class);
                    startActivity(signIn);
                }
            });

        }
    }
}