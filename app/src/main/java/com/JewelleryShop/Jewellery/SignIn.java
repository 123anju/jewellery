package com.JewelleryShop.Jewellery;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.JewelleryShop.Jewellery.Model.User;
import com.JewelleryShop.Jewellery.common.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import info.hoang8f.widget.FButton;

public class SignIn extends AppCompatActivity {
    ViewFlipper viewFlipper;
    EditText edtPhone,edtPassword;
    FButton btnSignIn;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    //int[] image={R.drawable.aa1,R.drawable.aa1,R.drawable.offers_icon_01,R.drawable.pubs_icon_01};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        MainActivity.a=0;
        edtPassword =(EditText) findViewById(R.id.edtPassword);
        edtPhone =(EditText) findViewById(R.id.edtPhone);
        btnSignIn =(FButton) findViewById(R.id.btnSignIn);
        viewFlipper=(ViewFlipper)findViewById(R.id.viewFlipper);
        btnSignIn.setButtonColor(R.color.splash);

//
//        for(int i=0;i<image.length;i++)
//        {
//            ImageView imageView=new ImageView(this);
//            imageView.setImageResource(image[i]);
//            viewFlipper.addView(imageView);
//
//        }

//        viewFlipper.setFlipInterval(2000);
//        viewFlipper.setAutoStart(true);

//        Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("User");
        sharedpreferences = getSharedPreferences("abc", Context.MODE_PRIVATE);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Common.isConnectedToInterner(getBaseContext())) {

                    final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                    mDialog.setMessage("Please waiting");
                    mDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Check if User not Exist in the database
                            if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                                //Get User Information
                                mDialog.dismiss();
                                User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);

                                //set Phone

                                user.setPhone(edtPhone.getText().toString());

                                if (user.getPassword().equals(edtPassword.getText().toString()))
                                {
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("mno",edtPhone.getText().toString());
                                    editor.putString("mname", user.getName());
                                    editor.apply();
                                    Intent homeIntent = new Intent(SignIn.this, Home.class);
                                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Common.currentUser = user;
                                    startActivity(homeIntent);
                                    finish();
                                    Toast.makeText(SignIn.this, "SignIn Sucessfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignIn.this, "SignIn Failed", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(SignIn.this, "User not exist", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(SignIn.this, "Please Check Your Connection!!!!", Toast.LENGTH_SHORT).show();
                    return ;
                }
            }

        });

    }
}