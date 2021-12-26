package com.stu.doantinhoc.Model;

import com.stu.doantinhoc.Util.ListData;

import org.json.JSONException;
import org.json.JSONObject;

public class Food {
    private int id;
    private String name;
    private int price;
    private int discount;
    private String image;
    private int category;


    public Food(JSONObject object) throws JSONException {
        this.id = object.getInt("id");
        this.name = object.getString("namefood");
        this.price = object.getInt("price");
        this.discount = object.getInt("discount");
        this.image = object.getString("picture");
        this.category = object.getInt("category");
    }

    public Food(int id, String name, int price, int discount, String image, int category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.image = image;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
