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

import com.JewelleryShop.Jewellery.Model.User;
import com.JewelleryShop.Jewellery.common.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import info.hoang8f.widget.FButton;

public class SignUp extends AppCompatActivity {

    EditText edtPhone ,edtName,edtPassword;
    FButton btnSignUp;
    int x=0;
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        MainActivity.a=0;
        edtName = (EditText) findViewById(R.id.edtName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtPhone = (EditText) findViewById(R.id.edtPhone);



        btnSignUp = (FButton) findViewById(R.id.btnSignUp);
        //        btnSignUp.setButtonColor(R.color.splash);

        //Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtPhone.getText().length() == 10 && edtName.getText().toString() != null && edtPassword.getText().toString() != null) {
                    if (edtName.getText().toString() != null ) {

                        if (Common.isConnectedToInterner(getBaseContext())) {
                            final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                            mDialog.setMessage("Please waiting");
                            mDialog.show();


                            table_user.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Check  if already exixt
                                    if (dataSnapshot.child(edtPhone.getText().toString()).exists() && x==0)
                                    {
                                        mDialog.dismiss();
                                        Toast.makeText(SignUp.this, "Phone Number already register", Toast.LENGTH_SHORT).show();
                                    } else {

                                        mDialog.dismiss();
                                        SharedPreferences sharedpreferences = getSharedPreferences("abc", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString("mno",edtPhone.getText().toString());
                                        editor.putString("mname", edtName.getText().toString());
                                        editor.apply();
                                        x=1;
                                        User user = new User(edtName.getText().toString(), edtPassword.getText().toString());
                                        table_user.child(edtPhone.getText().toString()).setValue(user);
                                        Intent homeIntent = new Intent(SignUp.this, Home.class);

                                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        Common.currentUser = user;
                                        Common.currentUser.Phone=edtPhone.getText().toString();
                                        startActivity(homeIntent);
                                        Toast.makeText(SignUp.this, "Thank you,Sign Up Successful", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        } else {
                            Toast.makeText(SignUp.this, "Please Check Your Connection!!!!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                else
                {
                    Toast.makeText(SignUp.this, "Mobile number is wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
