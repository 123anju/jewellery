package com.JewelleryShop.Jewellery;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.andremion.counterfab.CounterFab;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.JewelleryShop.Jewellery.Database.CreateDB;
import com.JewelleryShop.Jewellery.Interface.ItemClickListerner;
import com.JewelleryShop.Jewellery.Model.Category;
import com.JewelleryShop.Jewellery.Model.Order;
import com.JewelleryShop.Jewellery.ViewHolder.MenuViewHolder;
import com.JewelleryShop.Jewellery.common.Common;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//import steelkiwi.com.library.DotsLoaderView;
public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    FirebaseDatabase database;
    DatabaseReference category;
    TextView txtFullName,txtMobile;
    RecyclerView recyler_menu;
    //DotsLoaderView dotsLoaderView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;
    ViewFlipper viewFlipper;
    ProgressDialog mDialog;
    CounterFab fab;
    List<Order> val = new ArrayList<>();

    //   ImageView image;
    //   int[] image={R.drawable.bike_icon,R.drawable.blogs_icon_01,R.drawable.offers_icon_01,R.drawable.pubs_icon_01};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Jewellery");
        mDialog=new ProgressDialog(Home.this);
        setSupportActionBar(toolbar);
        //  dotsLoaderView=(DotsLoaderView) findViewById(R.id.dotsLoaderView);
        //Toast.makeText(this, ""+new CreateDB(Home.this).getAllData()., Toast.LENGTH_SHORT).show();
        // Init firebase
        database = FirebaseDatabase.getInstance();
        category =database.getReference("Category");
        mDialog.setMessage("Please Wait");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        val=new CreateDB(Home.this).getCarts();
        for(Order o: val )
        {
            //    Toast.makeText(this, ""+o.getProductName()+" "+o.getRefer(), Toast.LENGTH_SHORT).show();
        }
        SharedPreferences sharedpreferences = getSharedPreferences("abc", Context.MODE_PRIVATE);
        String session=sharedpreferences.getString("mno","ooo");

        fab = (CounterFab) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent cartIntent =new Intent(Home.this,Cart.class);
                startActivity(cartIntent);
            }
        });


        fab.setCount(new CreateDB(this).getTotalItem());
        viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);

        viewFlipper.setFlipInterval(2000);
        viewFlipper.setAutoStart(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        txtFullName=(TextView) headerView.findViewById(R.id.txtFullName);
        txtMobile=(TextView)headerView.findViewById(R.id.txtMobile);
        //Load menu
        recyler_menu = (RecyclerView) findViewById(R.id.recycler_menu);
        recyler_menu.setHasFixedSize(true);

        recyler_menu.setLayoutManager(new GridLayoutManager(this,1));
        if(Common.isConnectedToInterner(this))
            loadMenu();
        else
        {
            Toast.makeText(this, "Please Check Your Connection!!!!", Toast.LENGTH_SHORT).show();
            return ;
        }
    }

    private void loadMenu()
    {
        adapter =new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category)
        {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position)
            {
                // dotsLoaderView.hide();
                mDialog.dismiss();
                viewHolder.txtMenuName.setText(model.getName());
               // Toast.makeText(Home.this, ""+model.getName(), Toast.LENGTH_SHORT).show();
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);

                final Category clickItem = model;
                viewHolder.setItemClickListerner(new ItemClickListerner() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Get category and send to new Activity
                            Intent foodList = new Intent(Home.this, Home1.class);
                            Toast.makeText(Home.this, "iske age jo ae batana " + position, Toast.LENGTH_SHORT).show();
                            foodList.putExtra("CategoryId", adapter.getRef(position).getKey());
                            startActivity(foodList);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            // Handle the camera action
            Intent intent=new Intent(Home.this,Home.class);
            startActivity(intent);
        } else if (id == R.id.nav_cart) {
            Intent cartIntent = new Intent(Home.this,Cart.class);
            startActivity(cartIntent);
        } else if(id == R.id.nav_log_out){

            //Logout
            SharedPreferences sharedpreferences = getSharedPreferences("abc", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("mno","");
            editor.apply();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to Sign out ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent signIn = new Intent(Home.this,MainActivity.class);
                            signIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            MainActivity.start=1;
                            startActivity(signIn);

                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.show();
       }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        fab.setCount(new CreateDB(this).getTotalItem());
    }
}
