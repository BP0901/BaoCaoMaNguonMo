package com.stu.doantinhoc.Activities.Admin.ui.FoodMenu.Components;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.stu.doantinhoc.Controller.AdminController.FoodController;
import com.stu.doantinhoc.Controller.LoginController;
import com.stu.doantinhoc.Model.Category;
import com.stu.doantinhoc.R;
import com.stu.doantinhoc.RequestHttp.CategoryRequestHttp;
import com.stu.doantinhoc.RequestHttp.FoodRequestHttp;
import com.stu.doantinhoc.Util.ListData;
import com.stu.doantinhoc.Util.Url;
import com.stu.doantinhoc.databinding.ActivityAddFoodBinding;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class AddFoodActivity extends AppCompatActivity {

    private ActivityAddFoodBinding binding;
    private Button btnAdd;
    private Button btnCancel;
    private Spinner spnCate;
    private ImageView img;
    private Bitmap bitmap;
    private Button browse;
    private String encodeImageString;
    private FoodController controller;
    private static final String url = "http://192.168.1.11/androidDACN/insertFood.php";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        btnAdd = binding.btnAddFood;
        btnCancel = binding.btnCancelAddFood;
        spnCate = binding.spinnerAddCategories;
        img = binding.imgAddFoodPic;
        controller = new FoodController(AddFoodActivity.this);
        initSpinnerCate();
        setEvent();
    }

    private void setEvent() {
        btnCancel.setOnClickListener(v -> super.onBackPressed());

        browse = binding.btnSelectPicFood;

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(AddFoodActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Browse Image"), 1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        btnAdd.setOnClickListener(v -> {
            controller.addFoodToDB(
                    url,
                    binding.edtAddFoodname.getText().toString().trim(),
                    binding.edtAddFoodPrice.getText().toString().trim(),
                    binding.edtAddFoodDiscount.getText().toString().trim(),
                    ListData.listCate.get(spnCate.getSelectedItemPosition()).getId(),
                    encodeImageString
            );
        });
    }

    private void initSpinnerCate() {
        ArrayList<String> cateNames = new ArrayList<>();
        for (Category cate : ListData.listCate) {
            cateNames.add(cate.getCateName());
        }
        ArrayAdapter adapter = new ArrayAdapter(AddFoodActivity.this, android.R.layout.simple_spinner_item, cateNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCate.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            } catch (Exception ex) {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        encodeImageString = android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    @Override
    public void onBackPressed() {
        FoodRequestHttp requestHttp = new FoodRequestHttp(AddFoodActivity.this);
        ListData.listFood.clear();
        requestHttp.getData(Url.URLGETALLFOOD);
        super.onBackPressed();
    }
}