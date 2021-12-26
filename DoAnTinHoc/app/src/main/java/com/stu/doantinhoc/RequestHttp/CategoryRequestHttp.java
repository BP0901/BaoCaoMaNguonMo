package com.stu.doantinhoc.RequestHttp;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.stu.doantinhoc.Activities.Components.MsgDialog;
import com.stu.doantinhoc.Controller.AdminController.CaterogyController;
import com.stu.doantinhoc.Model.Category;
import com.stu.doantinhoc.Model.User;
import com.stu.doantinhoc.Util.ListData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryRequestHttp {
    private Context context;

    public CategoryRequestHttp(Context context) {
        this.context = context;
    }

    public void getData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject object = null;
                            try {
                                object = response.getJSONObject(i);
                                ListData.listCate.add(new Category(object));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
//                        Toast.makeText(context, ListData.listCate.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    public void insertAndDeleteAndUpdate(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(context, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

}
