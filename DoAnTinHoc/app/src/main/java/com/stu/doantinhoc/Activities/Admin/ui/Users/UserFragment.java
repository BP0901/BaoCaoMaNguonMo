package com.stu.doantinhoc.Activities.Admin.ui.Users;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.stu.doantinhoc.Controller.AdminController.UserController;
import com.stu.doantinhoc.R;
import com.stu.doantinhoc.databinding.FragmentUserBinding;

public class UserFragment extends Fragment {

    private FragmentUserBinding binding;
    private UserController controller;
    private FloatingActionButton fabAdd;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        init();

        return root;
    }

    private void init() {
        controller = new UserController(getContext(),binding.userRcv);
        controller.loadRCVUser();
        fabAdd = binding.fabAddUser;
        fabAdd.setOnClickListener(v -> controller.clickAddUser());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}