package com.stu.doantinhoc.Controller.StaffController;

import android.content.Context;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stu.doantinhoc.Adapter.StaffTableFoodAdaptorRCV;
import com.stu.doantinhoc.Model.Food;
import com.stu.doantinhoc.Model.FoodTable;
import com.stu.doantinhoc.Util.ListData;

import java.text.NumberFormat;
import java.util.ArrayList;

public class StaffTableController {
    private Context context;
    private RecyclerView rcvFood;
    private TextView txtvMoney;

    public StaffTableController(Context context, RecyclerView rcvFood) {
        this.context = context;
        this.rcvFood = rcvFood;
    }

    public StaffTableController(Context context, RecyclerView rcvFood, TextView txtvMoney) {
        this.context = context;
        this.rcvFood = rcvFood;
        this.txtvMoney = txtvMoney;
    }

    public StaffTableController(Context context) {
        this.context = context;
    }


    public void loadRCV(ArrayList<FoodTable> list, int table) {
        StaffTableFoodAdaptorRCV adaptor = new StaffTableFoodAdaptorRCV(txtvMoney ,list, context, table, rcvFood);
        rcvFood.setAdapter(adaptor);
        rcvFood.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        rcvFood.setHasFixedSize(true);
        rcvFood.setNestedScrollingEnabled(false);
    }

    public int totalMoney(ArrayList<FoodTable> list) {
        if (list.size() != 0) {
            int total = 0;
            for (FoodTable ft : list) {
                if (ft.getFood().getDiscount() == 0)
                    total += ft.getFood().getPrice() * ft.getAmount();
                else
                    total += ft.getFood().getDiscount() * ft.getAmount();
            }
            return total;
        }
        return 0;
    }

    private void setTotalMoney(ArrayList<FoodTable> list) {
        NumberFormat myFormat = NumberFormat.getInstance();
        int total = totalMoney(list);
        txtvMoney.setText("Tổng: " + myFormat.format(total) + " VND");
    }
}
