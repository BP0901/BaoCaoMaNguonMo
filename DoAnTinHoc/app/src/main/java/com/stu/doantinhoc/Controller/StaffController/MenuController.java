package com.stu.doantinhoc.Controller.StaffController;

import android.app.Activity;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.stu.doantinhoc.Adapter.food_item_card_adaptor;

public class MenuController {
    private Context context;
    private Activity activity;
    private RecyclerView rcvH, rcvV;
    private food_item_card_adaptor foodAdaptor;


    public MenuController(Context context, Activity activity, RecyclerView rcvH, RecyclerView rcvV) {
        this.context = context;
        this.activity = activity;
        this.rcvH = rcvH;
        this.rcvV = rcvV;
    }

    public MenuController(Context context) {
        this.context = context;
    }
}
