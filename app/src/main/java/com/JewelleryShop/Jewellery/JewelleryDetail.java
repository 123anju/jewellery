package com.JewelleryShop.Jewellery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.JewelleryShop.Jewellery.Database.CreateDB;
import com.JewelleryShop.Jewellery.Model.Food;
import com.JewelleryShop.Jewellery.common.Common;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class JewelleryDetail extends AppCompatActivity {

    TextView food_name,food_price,food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;
    String foodId="";
    FirebaseDatabase database;
    DatabaseReference foods;
    Food currentFood;

    String menu,ref;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jewellery_detail);

        database= FirebaseDatabase.getInstance();
        ref = getIntent().getStringExtra("ref");
        menu = getIntent().getStringExtra("menu");
        foods=database.getReference("Food/"+ref+"/"+menu);
        //init view
        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        btnCart = (FloatingActionButton) findViewById(R.id.btncart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sharedpreferences = getSharedPreferences("abc", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedpreferences.edit();
                String vender_id = sharedpreferences.getString("vender_id", "");
                if (!vender_id.equals("") && vender_id.equals(Home1.categoryId)) {
                    CreateDB db = new CreateDB(JewelleryDetail.this);

                    Boolean status = db.insertData(foodId, numberButton.getNumber(), currentFood.getPrice(), Common.currentUser.Phone, currentFood.getName(), ref, currentFood.getDiscount(), menu);

                    Intent home = new Intent(JewelleryDetail.this, Home.class);
                    home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(home);
                    Toast.makeText(JewelleryDetail.this, "Added to Card", Toast.LENGTH_SHORT).show();

                }
                else if(vender_id.equals(""))
                {
                    editor = sharedpreferences.edit();
                    editor.putString("vender_id", Home1.categoryId);
                    editor.apply();
                    CreateDB db = new CreateDB(JewelleryDetail.this);

                    Boolean status = db.insertData(foodId, numberButton.getNumber(), currentFood.getPrice(), Common.currentUser.Phone, currentFood.getName(), ref, currentFood.getDiscount(), menu);
                    Intent home = new Intent(JewelleryDetail.this, Home.class);
                    home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(home);
                    Toast.makeText(JewelleryDetail.this, "Added to Card", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(JewelleryDetail.this, "other vender", Toast.LENGTH_SHORT).show();
                }
            }
        });

        food_description = (TextView) findViewById(R.id.food_description);
        food_name = (TextView) findViewById(R.id.food_name);
        food_price = (TextView) findViewById(R.id.food_price);
        food_image = (ImageView) findViewById(R.id.img_food);
        collapsingToolbarLayout =(CollapsingToolbarLayout) findViewById( R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        //get food id from intent
        if(getIntent()!=null)
            foodId= getIntent().getStringExtra("FoodId");

        if(!foodId.isEmpty())
        {
            if(Common.isConnectedToInterner(getBaseContext()))
                getDetailFood(foodId);
            else
            {
                Toast.makeText(JewelleryDetail.this, "Please Check Your Connection!!!!", Toast.LENGTH_SHORT).show();
                return ;
            }
        }


    }

    private void getDetailFood(String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentFood= dataSnapshot.getValue(Food.class);
                Picasso.with(getBaseContext()).load(currentFood.getImage()).into(food_image);
                collapsingToolbarLayout.setTitle(currentFood.getName());
                food_price.setText(currentFood.getPrice());
                food_name.setText(currentFood.getName());
                food_description.setText(currentFood.getDescription());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
