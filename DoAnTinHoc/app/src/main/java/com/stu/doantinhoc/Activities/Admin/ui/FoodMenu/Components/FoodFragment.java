package com.stu.doantinhoc.Activities.Admin.ui.FoodMenu.Components;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.stu.doantinhoc.Activities.Components.MsgDialog;
import com.stu.doantinhoc.Adapter.Category_foods_horizontal;
import com.stu.doantinhoc.Adapter.IUpdateVerticalFoodsFgm;
import com.stu.doantinhoc.Adapter.food_item_card_adaptor;
import com.stu.doantinhoc.Controller.AdminController.FoodController;
import com.stu.doantinhoc.Model.Category;
import com.stu.doantinhoc.Model.Food;
import com.stu.doantinhoc.Util.ListData;
import com.stu.doantinhoc.databinding.FragmentFoodBinding;

import java.util.ArrayList;

public class FoodFragment extends Fragment implements IUpdateVerticalFoodsFgm{

    private FragmentFoodBinding binding;
    private RecyclerView recyclerViewCate, recyclerViewFood;
    private Category_foods_horizontal categoryAdaptor;
    private FoodController controller;
    private food_item_card_adaptor foodAdaptor;
    private FloatingActionButton fab;
    private TextView edtSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFoodBinding.inflate(getLayoutInflater(), container, false);

        init();

        return binding.getRoot();
    }

    private void init() {

        edtSearch = binding.searchFoodBox;

        fab = binding.btnAddFoodItem;
        setEventAddFood();

        recyclerViewCate = binding.cateHorizontalBox;
        initCateView();

        recyclerViewFood = binding.foodVerticalBox;
        initFoodView();

        controller = new FoodController(getContext(),getActivity(), recyclerViewCate, recyclerViewFood);
    }

    private void initCateView() {

        categoryAdaptor = new Category_foods_horizontal(this, getActivity(), ListData.listCate);

        recyclerViewCate.setAdapter(categoryAdaptor);
        recyclerViewCate.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        recyclerViewCate.setHasFixedSize(true);
        recyclerViewCate.setNestedScrollingEnabled(false);
    }

    private void initFoodView() {
        foodAdaptor = new food_item_card_adaptor(getActivity(), ListData.listFood);
        recyclerViewFood.setAdapter(foodAdaptor);
        recyclerViewFood.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

    }

    private void setEventAddFood() {
        fab.setOnClickListener(v -> startActivity(new Intent(getActivity(),AddFoodActivity.class)));
        edtSearch.setOnClickListener(v -> startActivity(new Intent(getActivity(),SearchActivity.class)));
    }

    @Override
    public void callBack(int position, ArrayList<Food> list) {
        foodAdaptor = new food_item_card_adaptor(getContext(), list);
        foodAdaptor.notifyDataSetChanged();
        recyclerViewFood.setAdapter(foodAdaptor);
    }

    @Override
    public void onResume() {
        initCateView();
        super.onResume();
    }
}