package com.stu.doantinhoc.Activities.Admin.ui.FoodMenu.Components;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.stu.doantinhoc.Activities.Admin.MainMenuActivity;
import com.stu.doantinhoc.Activities.Components.MsgDialog;
import com.stu.doantinhoc.Controller.LoginController;
import com.stu.doantinhoc.R;
import com.stu.doantinhoc.RequestHttp.CategoryRequestHttp;
import com.stu.doantinhoc.Util.ListData;
import com.stu.doantinhoc.Util.Url;
import com.stu.doantinhoc.databinding.ActivityAddCategoryBinding;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AddCategoryActivity extends AppCompatActivity {

    Button browse, upload;
    ImageView img;
    Bitmap bitmap;
    String encodeImageString;
    private static final String url = "http://192.168.1.11/androidDACN/insertCate.php";

    ActivityAddCategoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        img = binding.imgAddCatePic;
        upload = binding.btnAddCate;
        browse = binding.btnSelectPicCate;

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(AddCategoryActivity.this)
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

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploaddatatodb();
            }
        });
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

    private void uploaddatatodb() {
        EditText edtName = binding.edtAddCateName;
        String name = edtName.getText().toString().trim();
        if(name.isEmpty()){
            MsgDialog.showDialog(AddCategoryActivity.this,"Name is empty!");
            return;
        }
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                edtName.setText("");
                img.setImageResource(R.drawable.ic_launcher_foreground);
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("t1", name);
                map.put("upload", "");
                return map;
            }
        };


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    @Override
    public void onBackPressed() {
        CategoryRequestHttp requestHttp = new CategoryRequestHttp(AddCategoryActivity.this);
        ListData.listCate.clear();
        requestHttp.getData(Url.URLGETALLCATE);
        super.onBackPressed();
    }
}