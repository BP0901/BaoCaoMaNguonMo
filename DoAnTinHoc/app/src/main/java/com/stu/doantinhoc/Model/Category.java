package com.stu.doantinhoc.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Objects;

public class Category implements Serializable {
    private int id;
    private String cateName;
    private String catePic;

    public Category(int id, String cateName, String catePic) {
        this.id = id;
        this.cateName = cateName;
        this.catePic = catePic;
    }

    public Category(JSONObject object) throws JSONException {
        this.id = object.getInt("ID");
        this.cateName = object.getString("category");
        this.catePic = object.getString("picture");
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, cateName, catePic);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getCatePic() {
        return catePic;
    }

    public void setCatePic(String catePic) {
        this.catePic = catePic;
    }
}
