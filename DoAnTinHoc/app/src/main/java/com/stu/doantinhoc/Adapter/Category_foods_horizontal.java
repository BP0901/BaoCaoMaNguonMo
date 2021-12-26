package com.stu.doantinhoc.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stu.doantinhoc.Controller.AdminController.FoodController;
import com.stu.doantinhoc.Model.Category;
import com.stu.doantinhoc.Model.Food;
import com.stu.doantinhoc.R;
import com.stu.doantinhoc.Util.ListData;
import com.stu.doantinhoc.Util.Url;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class Category_foods_horizontal extends RecyclerView.Adapter<Category_foods_horizontal.ViewHoder> {

    IUpdateVerticalFoodsFgm iUpdateVerticalFoodsFgm;
    Activity activity;
    ArrayList<Category> listCate;

    boolean selected = true;
    boolean checked = true;
    int row_index = -1;


    public Category_foods_horizontal(IUpdateVerticalFoodsFgm iUpdateVerticalFoodsFgm, Activity activity, ArrayList<Category> listCate) {
        this.iUpdateVerticalFoodsFgm = iUpdateVerticalFoodsFgm;
        this.activity = activity;
        this.listCate = listCate;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHoder(LayoutInflater.from(parent.getContext()).inflate(R.layout.foods_cate_horizontal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        new LoadImage(holder.imageView).execute(Url.URLLOADIMG+listCate.get(position).getCatePic());
        holder.name.setText(listCate.get(position).getCateName());
        if (checked) {
            ArrayList<Food> list = new ArrayList<>();
            try {
                Category cate = ListData.listCate.get(1);
                for (Food food:ListData.listFood) {
                    if(food.getCategory()== cate.getId())
                        list.add(food);
                }
                iUpdateVerticalFoodsFgm.callBack(position, list);
                checked = false;
            }catch (Exception e){
                Toast.makeText(activity,e.toString(),Toast.LENGTH_SHORT).show();
            }

        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = position;
                notifyDataSetChanged();
                ArrayList<Food> list = new ArrayList<>();
                try {
                    Category cate = ListData.listCate.get(position+1);
                    for (Food food:ListData.listFood) {
                        if(food.getCategory()== cate.getId())
                            list.add(food);
                    }
                    iUpdateVerticalFoodsFgm.callBack(position, list);
                }catch (Exception e){
                    Toast.makeText(activity,e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (selected) {
            if (position == 0)
                holder.layout.setBackgroundResource(R.drawable.change_bg_foods_horizontal_cate);
            selected = false;
        }
        else if (row_index == position)
            holder.layout.setBackgroundResource(R.drawable.change_bg_foods_horizontal_cate);
        else
            holder.layout.setBackgroundResource(R.drawable.default_bg_foods_horizontal_cate);


    }

    @Override
    public int getItemCount() {
        return listCate.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;
        LinearLayout layout;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_cate_foods);
            name = itemView.findViewById(R.id.name_cate_foods);
            layout = itemView.findViewById(R.id.lnlCardViewCate);
        }
    }

    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView myImage;
        Bitmap bitmapHinh = null;

        public LoadImage( ImageView myImage) {
            this.myImage = myImage;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                InputStream inputStream = url.openConnection().getInputStream();
                bitmapHinh = BitmapFactory.decodeStream(inputStream);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmapHinh;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            myImage.setImageBitmap(bitmap);
        }
    }
}
