package com.example.bungee;

import android.net.Uri;

public class ProductData {
    String name, seller;
    Float price;
    Uri image;
    int quantity, sold, bought;

    public ProductData(String name, String seller, Float price, Uri image, int quantity, int sold, int bought) {
        this.name = name;
        this.seller = seller;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.sold = sold;
        this.bought = bought;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getBought() {
        return bought;
    }

    public void setBought(int bought) {
        this.bought = bought;
    }
}
