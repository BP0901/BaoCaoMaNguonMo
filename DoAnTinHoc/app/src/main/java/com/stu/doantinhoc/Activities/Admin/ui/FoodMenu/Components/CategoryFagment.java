package com.stu.doantinhoc.Activities.Admin.ui.FoodMenu.Components;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.stu.doantinhoc.Activities.Components.MsgDialog;
import com.stu.doantinhoc.Adapter.CategoryAdaptorRCV;
import com.stu.doantinhoc.Controller.AdminController.CaterogyController;
import com.stu.doantinhoc.Model.Category;
import com.stu.doantinhoc.R;
import com.stu.doantinhoc.databinding.FragmentCategoryFagmentBinding;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CategoryFagment extends Fragment {


    private CaterogyController controller;
    private FloatingActionButton fab;


    private FragmentCategoryFagmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCategoryFagmentBinding.inflate(getLayoutInflater(), container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        controller = new CaterogyController(getContext(), binding.rcvCategories);
        controller.loadRCVCate();
        fab = binding.btnAddCateItem;
        fab.setOnClickListener(v -> startActivity(new Intent(getActivity(),AddCategoryActivity.class)));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//    @Override
//    public void onResume() {
//        controller.loadRCVCate();
//        super.onResume();
//    }
}