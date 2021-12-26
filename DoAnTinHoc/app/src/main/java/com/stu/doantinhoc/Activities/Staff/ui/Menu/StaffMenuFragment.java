package com.stu.doantinhoc.Activities.Staff.ui.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stu.doantinhoc.Activities.Admin.ui.FoodMenu.Components.SearchActivity;
import com.stu.doantinhoc.Adapter.Category_foods_horizontal;
import com.stu.doantinhoc.Adapter.IUpdateVerticalFoodsFgm;
import com.stu.doantinhoc.Adapter.food_item_card_adaptor;
import com.stu.doantinhoc.Controller.AdminController.FoodController;
import com.stu.doantinhoc.Controller.StaffController.MenuController;
import com.stu.doantinhoc.Model.Food;
import com.stu.doantinhoc.Util.ListData;
import com.stu.doantinhoc.databinding.FragmentHomeBinding;

import java.util.ArrayList;


public class StaffMenuFragment extends Fragment implements IUpdateVerticalFoodsFgm {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerViewCate, recyclerViewFood;
    private Category_foods_horizontal categoryAdaptor;
    private MenuController controller;
    private food_item_card_adaptor foodAdaptor;
    private TextView edtSearch;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        init();
        return root;
    }

    private void init() {

        edtSearch = binding.searchFoodBox;

        recyclerViewCate = binding.cateHorizontalBox;
        initCateView();

        recyclerViewFood = binding.foodVerticalBox;
        initFoodView();

        controller = new MenuController(getContext(),getActivity(), recyclerViewCate, recyclerViewFood);

        setEvent();
    }

    private void setEvent() {
        edtSearch.setOnClickListener(v -> startActivity(new Intent(getActivity(), StaffSearchActivity.class)));
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

    @Override
    public void callBack(int position, ArrayList<Food> list) {
        foodAdaptor = new food_item_card_adaptor(getContext(), list);
        foodAdaptor.notifyDataSetChanged();
        recyclerViewFood.setAdapter(foodAdaptor);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}