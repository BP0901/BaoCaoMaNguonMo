package com.stu.doantinhoc.Activities.Admin.ui.FoodMenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.stu.doantinhoc.Activities.Admin.ui.FoodMenu.Components.CategoryFagment;
import com.stu.doantinhoc.Activities.Admin.ui.FoodMenu.Components.FoodFragment;
import com.stu.doantinhoc.R;
import com.stu.doantinhoc.databinding.FragmentMenuFoodBinding;

public class FoodMenuFragment extends Fragment {

    private FragmentMenuFoodBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentMenuFoodBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initBottomNavigationBar();

        return root;
    }
    private void initBottomNavigationBar() {
        BottomNavigationView mNavigationView = binding.bottomNavFoods;
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.body_foods, new FoodFragment());
        transaction.commit();
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_food:
                        loadFragment(new FoodFragment());
                        break;
                    case R.id.action_categories:
                        loadFragment(new CategoryFagment());
                        break;
                }

                return true;
            }
        });
    }

    private void loadFragment(Fragment fgm) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.body_foods, fgm);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}