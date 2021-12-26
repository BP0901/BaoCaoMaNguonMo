package com.stu.doantinhoc.Activities.Admin.ui.TableInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stu.doantinhoc.Activities.Admin.ui.AddFoodToTableActivity;
import com.stu.doantinhoc.Activities.Admin.ui.FoodMenu.Components.SearchActivity;
import com.stu.doantinhoc.Adapter.TableFoodAdaptorRCV;
import com.stu.doantinhoc.Controller.AdminController.TableController;
import com.stu.doantinhoc.Model.Food;
import com.stu.doantinhoc.Model.FoodTable;
import com.stu.doantinhoc.Model.Table;
import com.stu.doantinhoc.Util.ListData;
import com.stu.doantinhoc.databinding.ActivityTableInfoBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Objects;

public class TableInfoActivity extends AppCompatActivity {

    private ActivityTableInfoBinding binding;
    private ArrayList<FoodTable> listFood;
    private int table;
    private RecyclerView rcvFood;
    private TextView txtvTotalMoney;
    private TableController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        binding = ActivityTableInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            return;
        table = (int) bundle.get("table");
        init();
    }

    private void init() {
        binding.tableNameInfo.setText(ListData.listTable.get(table).getName());
        rcvFood = binding.rcvTableInfo;
        listFood = new ArrayList<>();
        listFood = ListData.listTable.get(table).getListFoods();
        txtvTotalMoney = binding.txtvTotalMoney;
        controller = new TableController(TableInfoActivity.this, rcvFood, txtvTotalMoney);
        loadRCV(listFood);
        setTotalMoney(listFood);
        setEvent();
    }

    private void setEvent() {
        binding.btnPayFoodTable.setOnClickListener(v -> {
            controller.payMoney(table);
            txtvTotalMoney.setText("Tổng: 0 VND");
        });
        binding.btnAddFoodTable.setOnClickListener(v -> senData());
    }


    private void senData() {
        Intent intent = new Intent(TableInfoActivity.this, AddFoodToTableActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("table", table);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void loadRCV(ArrayList<FoodTable> list) {
        controller.loadRCV(list,table);
        setTotalMoney(list);
    }

    private void setTotalMoney(ArrayList<FoodTable> list) {
        NumberFormat myFormat = NumberFormat.getInstance();
        int total = controller.totalMoney(list);
        txtvTotalMoney.setText("Tổng: " + myFormat.format(total) + " VND");
    }

    @Override
    protected void onResume() {
//        Toast.makeText(TableInfoActivity.this,String.valueOf(table),Toast.LENGTH_SHORT).show();
        init();
        super.onResume();
    }
}