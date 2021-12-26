package com.stu.doantinhoc.Model;

import java.io.Serializable;

public class FoodTable implements Serializable {
    private Food food;
    private int amount;

    public FoodTable(Food food, int amount) {
        this.food = food;
        this.amount = amount;
    }

    public FoodTable() {
        new FoodTable(null,0);
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
