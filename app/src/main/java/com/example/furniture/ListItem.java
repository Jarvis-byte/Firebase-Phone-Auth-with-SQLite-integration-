package com.example.furniture;

import android.graphics.Bitmap;

public class ListItem {
    private String name,type,price,discount,id;
Bitmap img;
    public ListItem(String name, String type, String price, String discount, Bitmap img, String id) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.discount = discount;
        this.img = img;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPrice() {
        return price;
    }

    public String getDiscount() {
        return discount;
    }

    public Bitmap getImg() {
        return img;
    }

    public String getId() {
        return id;
    }
}
