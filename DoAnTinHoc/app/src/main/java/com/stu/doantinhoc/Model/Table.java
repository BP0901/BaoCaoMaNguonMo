package com.stu.doantinhoc.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Table implements Serializable {
    private String name;
    private boolean status;
    private ArrayList<FoodTable> listFoods;

    public Table(String name, ArrayList<FoodTable> listFoods) {
        this.name = name;
        this.listFoods = listFoods;
        this.status = listFoods.size() == 0 ? false : true;
    }

    public Table() {
        new Table(null, new ArrayList<>());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<FoodTable> getListFoods() {
        return listFoods;
    }

    public void setListFoods(ArrayList<FoodTable> listFoods) {
        this.listFoods = listFoods;
    }
}


