package com.JewelleryShop.Jewellery.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.JewelleryShop.Jewellery.Interface.ItemClickListerner;
import com.JewelleryShop.Jewellery.R;

/**
 * Created by saurabh omer on 28-Mar-18.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
    public TextView txtMenuName;
    public TextView txtMenuLocation;
    public ImageView imageView;

    private ItemClickListerner itemClickListerner;
    public MenuViewHolder(View itemView) {
        super(itemView);
        txtMenuName = (TextView) itemView.findViewById(R.id.menu_name);
        //txtMenuLocation = (TextView) itemView.findViewById(R.id.menu_location);
        imageView =(ImageView) itemView.findViewById(R.id.menu_image);
        itemView.setOnClickListener(this);
    }



    public void setItemClickListerner(ItemClickListerner itemClickListerner) {
        this.itemClickListerner = itemClickListerner;
    }
    @Override
    public void onClick(View view) {
        itemClickListerner.onClick(view,getAdapterPosition(),false);
    }
}
