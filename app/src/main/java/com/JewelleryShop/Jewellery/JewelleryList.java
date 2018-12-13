package com.JewelleryShop.Jewellery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.JewelleryShop.Jewellery.Interface.ItemClickListerner;
import com.JewelleryShop.Jewellery.Model.Food;
import com.JewelleryShop.Jewellery.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class JewelleryList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;
    String item="";
    String menu ;
    String categoryId="";
    ProgressDialog mProgress;
    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;
    //Search Functionality
    FirebaseRecyclerAdapter<Food,FoodViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    // MaterialSearchBar materialSearchBar;
    String refer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jewellery_list);

        item = getIntent().getStringExtra("item");
        categoryId = getIntent().getStringExtra("comp");

        Toast.makeText(this, ""+item+categoryId, Toast.LENGTH_SHORT).show();
        //Firebase
        database = FirebaseDatabase.getInstance();
        menu=categoryId;


        foodList = database.getReference("Food/" + menu + "/" + item);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_food1);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.food_image);
                // mProgress.dismiss();
                final Food local = model;

                viewHolder.setItemClickListerner(new ItemClickListerner() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Start New Activity

                        Intent foodDetail =new Intent(JewelleryList.this,JewelleryDetail.class);
                        foodDetail.putExtra("ref",menu);  //send Food id to new activity
                        foodDetail.putExtra("menu",item);  //send Food id to new activity
                        foodDetail.putExtra("FoodId",adapter.getRef(position).getKey());  //send Food id to new activity
                        startActivity(foodDetail);

                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);

    }
}
