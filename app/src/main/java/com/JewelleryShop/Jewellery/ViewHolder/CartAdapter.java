package com.JewelleryShop.Jewellery.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.JewelleryShop.Jewellery.Cart;
import com.JewelleryShop.Jewellery.Interface.ItemClickListerner;
import com.JewelleryShop.Jewellery.Model.Order;
import com.JewelleryShop.Jewellery.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by saurabh omer on 29-Mar-18.
 */

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txt_cart_name,txt_price;
    public ImageView img_cart_count;
    public ImageButton deleteitem;

    private ItemClickListerner itemClickListerner;

    public void setTxt_cart_name(TextView txt_cart_name) {
        this.txt_cart_name = txt_cart_name;
    }

    public CartViewHolder(View itemView)
    {
        super(itemView);
        txt_cart_name=(TextView) itemView.findViewById(R.id.cart_item_name);
        txt_price=(TextView) itemView.findViewById(R.id.cart_item_price);
        img_cart_count=(ImageView)itemView.findViewById(R.id.cart_item_count);
        deleteitem=(ImageButton)itemView.findViewById(R.id.deleteitem);
        //itemView.setOnCreateContextMenuListener(this);
    }


    @Override
    public void onClick(View view)
    {


    }

//    @Override
//    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo)
//    {
//        contextMenu.setHeaderTitle("Select action");
//        contextMenu.add(0,0,getAdapterPosition(), Common.DELETE);
//    }
}

public class CartAdapter extends  RecyclerView.Adapter<CartViewHolder>
{
    private List<Order> listData= new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> cart, Context context) {
        this.listData =cart;
        this.context =context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout,parent,false);
        return new CartViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, final int position) {
        TextDrawable drawable = TextDrawable.builder().buildRound(""+ listData.get(position).getQuantity(), Color.RED);
        holder.img_cart_count.setImageDrawable(drawable);
//        Locale locale =new Locale("en","US");
//        NumberFormat fmt= NumberFormat.getCurrencyInstance(locale);
        int price =(Integer.parseInt(listData.get(position).getPrice()))*(Integer.parseInt(listData.get(position).getQuantity()));
        holder.txt_price.setText(String.valueOf(price));
        holder.txt_cart_name.setText(listData.get(position).getProductName());

        holder.deleteitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(context, "a"+Cart.cart.get(position).getID(), Toast.LENGTH_SHORT).show();

                Cart.deleteCart1(Cart.cart.get(position).getID(),context);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


}
