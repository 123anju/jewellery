package com.JewelleryShop.Jewellery.Model;


public class Order {
    private String ID;
    private String ProductId;
    private String Quantity;
    private String Price;
    private String Mobile;
    private String ProductName;
    private String Refer;

    public String getMenu() {
        return Menu;
    }

    public void setMenu(String menu) {
        Menu = menu;
    }

    private String Discount;
    private String Menu;

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }


    public Order(String id, String productId, String quantity, String price, String discount, String mobile, String productName, String menu, String refer) {

            ProductId = productId;
            Quantity = quantity;
            Price = price;
            Mobile = mobile;
            ProductName = productName;
            Refer = refer;
            Discount = discount;
            Menu = menu;
            ID=id;

    }

    public String getID() {
        return ID;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }


    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getRefer() {
        return Refer;
    }

    public void setRefer(String refer) {
        Refer = refer;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public Order() {

    }
}
