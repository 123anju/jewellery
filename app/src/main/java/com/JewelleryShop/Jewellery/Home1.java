package com.JewelleryShop.Jewellery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.andremion.counterfab.CounterFab;
import com.JewelleryShop.Jewellery.Database.CreateDB;
import com.JewelleryShop.Jewellery.Interface.ItemClickListerner;
import com.JewelleryShop.Jewellery.Model.Category;
import com.JewelleryShop.Jewellery.ViewHolder.MenuViewHolder;
import com.JewelleryShop.Jewellery.common.Common;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Home1 extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference category;
    TextView txtFullName;
    RecyclerView recyler_menu;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;
    ViewFlipper viewFlipper;
    static String categoryId;
    String item="";
    ProgressDialog mDialog;
    CounterFab fab;
    //   ImageView image;
    //   int[] image={R.drawable.bike_icon,R.drawable.blogs_icon_01,R.drawable.offers_icon_01,R.drawable.pubs_icon_01};
    @RequiresApi(api = Build.VERSION_CODES.ECLAIR_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);
        mDialog=new ProgressDialog(Home1.this);
        mDialog.setMessage("Please Wait");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
        // Init firebase
        database = FirebaseDatabase.getInstance();
        categoryId =getIntent().getStringExtra("CategoryId");
        setSupportActionBar(toolbar);

        if(categoryId.equals("0"))
        {
            item ="Neckless";

        }
        else if(categoryId.equals("02"))
        {

            item="Bracelet";
        }
        else if(categoryId.equals("03"))
        {
            item="Ring";
        }
        else if(categoryId.equals("04"))
        {
            item="offers";
        }



        toolbar.setTitle(item);
    //       Toast.makeText(this, ""+item+"kkk", Toast.LENGTH_SHORT).show();
        category =database.getReference("Menu/"+item);
        fab = (CounterFab) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent =new Intent(Home1.this,Cart.class);
                startActivity(cartIntent);


            }
        });


        fab.setCount(new CreateDB(this).getTotalItem());
        viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);

        viewFlipper.setFlipInterval(2000);
        viewFlipper.setAutoStart(true);
        recyler_menu = (RecyclerView) findViewById(R.id.recycler_menu);
        recyler_menu.setHasFixedSize(true);


        recyler_menu.setLayoutManager(new GridLayoutManager(this,2));
        if(Common.isConnectedToInterner(this))
            loadMenu();
        else
        {
            Toast.makeText(this, "Please Check Your Connection!!!!", Toast.LENGTH_SHORT).show();
            return ;
        }
    }

    private void loadMenu() {
        adapter =new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item1,MenuViewHolder.class,category) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, final Category model, int position) {
                mDialog.dismiss();
                viewHolder.txtMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);
                final Category clickItem =model;

                viewHolder.setItemClickListerner(new ItemClickListerner() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Get category and send to new Activity
                       // Toast.makeText(Home1.this, ""+model.getName(), Toast.LENGTH_SHORT).show();
                        if(position==0)
                        {
                            Intent jewelleryList = new Intent(Home1.this, JewelleryList.class);
                            jewelleryList.putExtra("item",model.getName());
                            jewelleryList.putExtra("comp",item);
                            startActivity(jewelleryList);
                        }
                        else {
                            Toast.makeText(Home1.this, "Coming soon", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
        recyler_menu.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.refresh)
            loadMenu();

        return super.onOptionsItemSelected(item);
    }
}
