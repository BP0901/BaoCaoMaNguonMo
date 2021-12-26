package com.stu.doantinhoc.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.stu.doantinhoc.Activities.Components.MsgDialog;
import com.stu.doantinhoc.Controller.AdminController.TableController;
import com.stu.doantinhoc.Model.Food;
import com.stu.doantinhoc.R;
import com.stu.doantinhoc.Util.Url;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SearchAddFoodAdapter extends RecyclerView.Adapter<SearchAddFoodAdapter.ViewHolder> implements Filterable {

    private ArrayList<Food> listFood;
    private ArrayList<Food> listFoodOld;
    private Context context;
    private int table;

    public SearchAddFoodAdapter(ArrayList<Food> listFood, Context context, int table) {
        this.listFood = listFood;
        this.listFoodOld = listFood;
        this.context = context;
        this.table = table;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchAddFoodAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.add_food_to_table_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        new LoadImage(holder.foodRow).execute(Url.URLLOADIMG+listFood.get(position).getImage());
        holder.name.setText(listFood.get(position).getName());
        holder.discount.setText("");
        if (listFood.get(position).getDiscount() == 0) {
            holder.price.setText(String.valueOf(listFood.get(position).getPrice()));
        } else {
            holder.price.setText(String.valueOf(listFood.get(position).getPrice()));
            holder.discount.setText(String.valueOf(listFood.get(position).getDiscount()));
            holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.addFoodToTableLayout.setOnClickListener(v -> addFood(listFood.get(position)));
    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strsearch = constraint.toString();
                if (strsearch.isEmpty()) {
                    listFood = listFoodOld;
                } else {
                    ArrayList<Food> list = new ArrayList<>();
                    for (Food food : listFoodOld) {
                        if (food.getName().toLowerCase().contains(strsearch.toLowerCase())) {
                            list.add(food);
                        }

                    }
                    listFood = list;
                }
                FilterResults results = new FilterResults();
                results.values = listFood;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listFood = (ArrayList<Food>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;
        TextView discount;
        ImageView foodRow;
        ConstraintLayout addFoodToTableLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameAddFoodRow);
            price = itemView.findViewById(R.id.priceAddFoodRow);
            discount = itemView.findViewById(R.id.discountAddFoodRow);
            foodRow = itemView.findViewById(R.id.imgAddFoodTableRow);
            addFoodToTableLayout = itemView.findViewById(R.id.AddFoodToTableLayout);
        }
    }

    private void addFood(Food food) {
        new AlertDialog.Builder(context)
                .setMessage("Thêm " + food.getName() + "?")
                .setPositiveButton("Thêm",(dialog, which) -> {
                    TableController controller = new TableController(context);
                    TableController.addFoodToTable(food,table);
                    MsgDialog.showDialog(context,"Thêm thành công");
                })
                .setNegativeButton("Không",null)
                .show();
    }

    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView myImage;
        Bitmap bitmapHinh = null;

        public LoadImage(ImageView myImage) {
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
