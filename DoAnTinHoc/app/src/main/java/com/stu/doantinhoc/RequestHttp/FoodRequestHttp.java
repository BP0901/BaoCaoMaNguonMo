package com.stu.doantinhoc.RequestHttp;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.stu.doantinhoc.Model.Category;
import com.stu.doantinhoc.Model.Food;
import com.stu.doantinhoc.Util.ListData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FoodRequestHttp {
    private Context context;

    public FoodRequestHttp(Context context) {
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
                                ListData.listFood.add(new Food(object));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
//                        Toast.makeText(context, ListData.listFood.toString(), Toast.LENGTH_SHORT).show();
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
