package com.JewelleryShop.Jewellery.Model;

/**
 * Created by Anju on 14-04-2018.
 */

public class Contact {
    private int Image;
    private String Name;
    private String City;
    private String Phone;
    private String Email;


    public Contact(int Image, String Name, String City, String Phone, String Email)
    {
        this.Image=Image;
        this.City=City;
        this.Name=Name;
        this.Email=Email;
        this.Phone=Phone;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
