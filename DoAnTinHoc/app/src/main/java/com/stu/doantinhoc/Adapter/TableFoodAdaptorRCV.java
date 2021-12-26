package com.stu.doantinhoc.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.stu.doantinhoc.Controller.AdminController.TableController;
import com.stu.doantinhoc.Model.FoodTable;
import com.stu.doantinhoc.R;
import com.stu.doantinhoc.Util.ListData;

import java.text.NumberFormat;
import java.util.ArrayList;


public class TableFoodAdaptorRCV extends RecyclerView.Adapter<TableFoodAdaptorRCV.ViewHolder> {
    private ArrayList<FoodTable> listFood;
    private Context context;
    private ViewBinderHelper binding;
    private int table;
    private TextView money;
    private RecyclerView rcv;

    public TableFoodAdaptorRCV(TextView money, ArrayList<FoodTable> listFood, Context context, int table, RecyclerView rcv) {
        this.money = money;
        this.rcv = rcv;
        this.listFood = listFood;
        this.context = context;
        this.table = table;
        this.binding = new ViewBinderHelper();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.table_food_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            NumberFormat myFormat = NumberFormat.getInstance();
            holder.txtvName.setText(listFood.get(position).getFood().getName());
            holder.txtvAmount.setText(String.valueOf(listFood.get(position).getAmount()));
            if (listFood.get(position).getFood().getDiscount() == 0)
                holder.txtvPrice.setText(myFormat.format(listFood.get(position).getFood().getPrice()));
            else
                holder.txtvPrice.setText(myFormat.format(listFood.get(position).getFood().getDiscount()));
            binding.bind(holder.swipeRevealLayout, String.valueOf(listFood.get(position).getFood().getId()));
            holder.del.setOnClickListener(v -> delFoodFromTable(position));
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtvName;
        TextView txtvAmount;
        TextView txtvPrice;
        SwipeRevealLayout swipeRevealLayout;
        ImageView del;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtvName = itemView.findViewById(R.id.table_food_row_name);
            txtvAmount = itemView.findViewById(R.id.table_food_row_amount);
            txtvPrice = itemView.findViewById(R.id.table_food_row_price);
            swipeRevealLayout = itemView.findViewById(R.id.Swipe_row_table_food);
            del = itemView.findViewById(R.id.imgDelTableFoodRow);
        }
    }

    private void delFoodFromTable(int pos) {
        new AlertDialog.Builder(context)
                .setMessage("Có chắc muốn xóa?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            TableController controller = new TableController(context, rcv, money);
                            controller.delFoodInTable(table, listFood.get(pos));
                        } catch (Exception e) {
                            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
