package com.JewelleryShop.Jewellery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.JewelleryShop.Jewellery.Model.Request;
import com.JewelleryShop.Jewellery.ViewHolder.OrderViewHolder;
import com.JewelleryShop.Jewellery.common.Common;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import steelkiwi.com.library.DotsLoaderView;

public class OrderStatus extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference requests;
  //  DotsLoaderView dotsLoaderView;
//    ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
//        mDialog=new ProgressDialog(OrderStatus.this);
//        mDialog.setMessage("Please waiting");
//        mDialog.setCanceledOnTouchOutside(false);
//        mDialog.show();

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle("Order History");

//        if(getSupportActionBar()!=null)
//        {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }
       // Toast.makeText(this, ""+Common.currentUser.Phone, Toast.LENGTH_SHORT).show();
        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView=(RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("phone").equalTo(Common.currentUser.Phone)


        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {


                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText("Payment Mode:"+model.getTime());
                viewHolder.txtOrderAddress.setText("Address:"+model.getAddress());
            //   viewHolder.txtOrderPhone.setText(model.getTotal().toString());
            }
        };
        recyclerView.setAdapter(adapter);
    }

//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId()==android.R.id.home)
//        {
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
    private void loadOrders() {


    }
       // private String  convertCodeToStatus(String status) {
    //        if(status.equals("0"))
    //            return "Placed";
    //        else if(status.equals("1"))
    //            return "On my way";
    //        else
    //            return "Shipped";
       // }
}
