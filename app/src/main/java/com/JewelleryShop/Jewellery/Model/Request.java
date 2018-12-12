package com.JewelleryShop.Jewellery.Model;

import java.util.List;

/**
 * Created by saurabh omer on 29-Mar-18.
 */

public class Request
{
    private String phone;
    private String name;
    private String address;
    private String total;
    private String date;
    private String time;
    private List<Order> foods; // list of food's order
    private String status;

    public Request(String phone, String name, String address, String total, List<Order> foods, String date, String time)
    {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.total = total;
        this.date = date;
        this.time = time;
        this.foods = foods;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Request() {

    }

    public String getStatus() {
        return status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }
}
