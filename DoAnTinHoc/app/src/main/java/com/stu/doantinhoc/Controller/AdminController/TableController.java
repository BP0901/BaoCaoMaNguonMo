package com.stu.doantinhoc.Controller.AdminController;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stu.doantinhoc.Activities.Admin.ui.TableInfo.TableInfoActivity;
import com.stu.doantinhoc.Adapter.TableFoodAdaptorRCV;
import com.stu.doantinhoc.Model.Food;
import com.stu.doantinhoc.Model.FoodTable;
import com.stu.doantinhoc.Model.Table;
import com.stu.doantinhoc.Util.ListData;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;

public class TableController {
    private Context context;
    private RecyclerView rcvFood;
    private TextView txtvMoney;

    public TableController(Context context, RecyclerView rcvFood) {
        this.context = context;
        this.rcvFood = rcvFood;
    }

    public TableController(Context context, RecyclerView rcvFood, TextView txtvMoney) {
        this.context = context;
        this.rcvFood = rcvFood;
        this.txtvMoney = txtvMoney;
    }

    public TableController(Context context) {
        this.context = context;
    }

    public static void addFoodToTable(Food food, int table) {
        for (int i = 0; i < ListData.listTable.get(table).getListFoods().size(); i++) {
            if (ListData.listTable.get(table).getListFoods().get(i).getFood().equals(food)) {
                ListData.listTable.get(table).getListFoods().get(i).setAmount(ListData.listTable.get(table).getListFoods().get(i).getAmount() + 1);
                return;
            }
        }
        ListData.listTable.get(table).getListFoods().add(new FoodTable(food, 1));
        ListData.listTable.get(table).setStatus(true);
    }

    public void loadRCV(ArrayList<FoodTable> list, int table) {
        TableFoodAdaptorRCV adaptor = new TableFoodAdaptorRCV(txtvMoney ,list, context, table, rcvFood);
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

    public void payMoney(int table) {
        new AlertDialog.Builder(context)
                .setMessage("Thanh toán " + ListData.listTable.get(table).getName() + "?")
                .setPositiveButton("Có", (dialog, which) -> {
                    ListData.listTable.get(table).getListFoods().clear();
                    ListData.listTable.get(table).setStatus(false);
                    loadRCV(ListData.listTable.get(table).getListFoods(), table);
                })
                .setNegativeButton("Không", null)
                .show();
    }

    public void delFoodInTable(int table, FoodTable foodTable) {
        for (int i = 0; i < ListData.listTable.get(table).getListFoods().size(); i++) {
            if (ListData.listTable.get(table).getListFoods().get(i).equals(foodTable)) {
                ListData.listTable.get(table).getListFoods().remove(i);
                if(ListData.listTable.get(table).getListFoods().size()==0)
                    ListData.listTable.get(table).setStatus(false);
            }
        }
        loadRCV(ListData.listTable.get(table).getListFoods(), table);
        setTotalMoney(ListData.listTable.get(table).getListFoods());
    }

    private void setTotalMoney(ArrayList<FoodTable> list) {
        NumberFormat myFormat = NumberFormat.getInstance();
        int total = totalMoney(list);
        txtvMoney.setText("Tổng: " + myFormat.format(total) + " VND");
    }
}
