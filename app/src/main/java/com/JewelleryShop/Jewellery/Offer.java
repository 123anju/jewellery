package com.JewelleryShop.Jewellery;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.JewelleryShop.Jewellery.Model.OfferModel;
import com.JewelleryShop.Jewellery.ViewHolder.OfferViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Offer extends AppCompatActivity
{

    FirebaseRecyclerAdapter<OfferModel,OfferViewHolder> adapter;
    DatabaseReference category;
    FirebaseDatabase database;
    RecyclerView recyler_menu;
    ProgressDialog mProgress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        mProgress=new ProgressDialog(Offer.this);
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.show();
        database = FirebaseDatabase.getInstance();
        category =database.getReference("Offers");
        recyler_menu = (RecyclerView) findViewById(R.id.recycler_View);
        recyler_menu.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayout.HORIZONTAL);
        recyler_menu.setLayoutManager(layoutManager);
        loadMenu();
       // toolbar.setTitle("Blog");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Blog");

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

    private void loadMenu() {
        adapter =new FirebaseRecyclerAdapter<OfferModel, OfferViewHolder>(OfferModel.class,R.layout.horizontal_single_row,OfferViewHolder.class,category) {
            @Override
            protected void populateViewHolder(OfferViewHolder viewHolder, OfferModel model, int position) {
                mProgress.dismiss();
                viewHolder.txtMenuName.setText(model.getTitle());
                viewHolder.txtCouponName.setText(model.getCouponcode());
                viewHolder.rule1.setText(model.getRules1());
                viewHolder.rule2.setText(model.getRules2());
                viewHolder.rule3.setText(model.getRules3());
                viewHolder.rule4.setText(model.getRules4());

                Picasso.with(getBaseContext()).load(model.getImage()).placeholder(R.mipmap.ic_launcher).into(viewHolder.imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        
                    }

                    @Override
                    public void onError() {

                    }
                });

            }
        };
        recyler_menu.setAdapter(adapter);
    }

}
