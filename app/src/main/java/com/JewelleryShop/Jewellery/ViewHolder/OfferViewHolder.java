package com.JewelleryShop.Jewellery.ViewHolder;

/**
 * Created by Anju on 09-04-2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.JewelleryShop.Jewellery.R;

/**
 * Created by saurabh omer on 05-Apr-18.
 */

public class OfferViewHolder  extends RecyclerView.ViewHolder  {
    public TextView txtMenuName,txtCouponName,rule1,rule2,rule3,rule4;
    public ImageView imageView;


    public OfferViewHolder(View itemView) {
        super(itemView);
        txtMenuName = (TextView) itemView.findViewById(R.id.offer_title);
        txtCouponName = (TextView) itemView.findViewById(R.id.coupon_code);
        imageView = (ImageView) itemView.findViewById(R.id.nav_image_view);
        rule1=(TextView) itemView.findViewById(R.id.rule1);
        rule2=(TextView) itemView.findViewById(R.id.rule2);
        rule3=(TextView) itemView.findViewById(R.id.rule3);
        rule4=(TextView) itemView.findViewById(R.id.rule4);

    }
}