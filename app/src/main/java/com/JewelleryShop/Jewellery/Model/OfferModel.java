package com.JewelleryShop.Jewellery.Model;

/**
 * Created by Anju on 09-04-2018.
 */

public class OfferModel {

    String image;
    String title;
    String couponcode;
    String rules1,rules2,rules3,rules4;

    public String getRules2() {
        return rules2;
    }

    public void setRules2(String rules2) {
        this.rules2 = rules2;
    }

    public String getRules3() {
        return rules3;
    }

    public void setRule3(String rules3) {
        this.rules3 = rules3;
    }

    public void setRules3(String rules3) {
        this.rules3 = rules3;
    }

    public String getRules4() {
        return rules4;
    }

    public void setRules4(String rules4) {
        this.rules4 = rules4;
    }

    public OfferModel(String image, String title, String couponcode, String rules1, String rules2, String rules3, String rules4) {
        this.image = image;
        this.title = title;
        this.couponcode = couponcode;
        this.rules1 = rules1;

        this.rules2 = rules2;
        this.rules3 = rules3;
        this.rules4 = rules4;
    }

    public String getRules1() {

        return rules1;

    }

    public void setRules1(String rules1) {
        this.rules1 = rules1;
    }

    public String getCouponcode() {
        return couponcode;
    }

    public void setCouponcode(String couponcode) {
        this.couponcode = couponcode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }
    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OfferModel()
    {
    }


}
