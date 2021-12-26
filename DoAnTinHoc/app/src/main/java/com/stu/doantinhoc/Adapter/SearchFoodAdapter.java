package com.stu.doantinhoc.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.stu.doantinhoc.Activities.Admin.ui.FoodMenu.Components.AddFoodActivity;
import com.stu.doantinhoc.Activities.Components.MsgDialog;
import com.stu.doantinhoc.Controller.AdminController.CaterogyController;
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
import java.util.List;

public class SearchFoodAdapter extends RecyclerView.Adapter<SearchFoodAdapter.ViewHolder> implements Filterable {

    private ArrayList<Food> listFood;
    private ArrayList<Food> listFoodOld;
    private Context context;
    private ViewBinderHelper binding;

    public SearchFoodAdapter(ArrayList<Food> listFood, Context context) {
        this.listFood = listFood;
        this.context = context;
        this.listFoodOld = listFood;
        binding = new ViewBinderHelper();
    }

    @NonNull
    @Override
    public SearchFoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchFoodAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_food_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchFoodAdapter.ViewHolder holder, int position) {
        new LoadImage(holder.foodRow).execute(Url.URLLOADIMG+listFood.get(position).getImage());
        binding.bind(holder.swipeRevealLayout, String.valueOf(listFood.get(position).getId()));
        holder.name.setText(listFood.get(position).getName());
        holder.discount.setText("");
        if (listFood.get(position).getDiscount() == 0) {
            holder.price.setText(String.valueOf(listFood.get(position).getPrice()));
        } else {
            holder.price.setText(String.valueOf(listFood.get(position).getPrice()));
            holder.discount.setText(String.valueOf(listFood.get(position).getDiscount()));
            holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.delRow.setOnClickListener(v -> delFood(listFood.get(position)));
        holder.editRow.setOnClickListener(v -> updateFood(listFood.get(position),position));
    }

    private void updateFood(Food food, int pos) {
        openDialog(food, pos);
        notifyDataSetChanged();
    }

    private void delFood(Food food) {
        new AlertDialog.Builder(context)
                .setMessage("Do you really want to delete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FoodController controller = new FoodController(context);
                        controller.delFood(food);
                        listFood.remove(food);
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void openDialog(Food food, int pos) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_food_dialog);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        Button btnAddInDialog = (Button) dialog.findViewById(R.id.btnAddFoodDialog);
        Button btnCancelInDialog = (Button) dialog.findViewById(R.id.btnCancelAddFoodDialog);
        Spinner spn = dialog.findViewById(R.id.spinnerCategories);
        ArrayList<String> cateNames = new ArrayList<>();
        for (Category cate : ListData.listCate) {
            cateNames.add(cate.getCateName());
        }
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, cateNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapter);
        EditText name = dialog.findViewById(R.id.edtEditFoodname);
        name.setText(food.getName());
        EditText price = dialog.findViewById(R.id.edtEditPriceFood);
        price.setText(String.valueOf(food.getPrice()));
        EditText dis = dialog.findViewById(R.id.edtEditFoodDiscount);
        dis.setText(String.valueOf(food.getDiscount()));
        btnAddInDialog.setOnClickListener(v -> {
            if (name.getText().toString().isEmpty()) {
                MsgDialog.showDialog(context, "Name is empty!");
                return;
            }
            if (price.getText().toString().isEmpty()) {
                MsgDialog.showDialog(context, "Price is empty!");
                return;
            }
            if (dis.getText().toString().isEmpty()) {
                MsgDialog.showDialog(context, "Discount is empty!");
                return;
            }
            FoodController controller = new FoodController(context);
            controller.updateFood(food, name.getText().toString().trim(), price.getText().toString().trim(), dis.getText().toString().trim(), ListData.listCate.get(spn.getSelectedItemPosition()).getId(), "");
            listFood.get(pos).setName(name.getText().toString().trim());
            listFood.get(pos).setPrice(Integer.parseInt(price.getText().toString().trim()));
            listFood.get(pos).setDiscount(Integer.parseInt(dis.getText().toString().trim()));
            listFood.get(pos).setCategory(ListData.listCate.get(spn.getSelectedItemPosition()).getId());
            dialog.cancel();
        });
        btnCancelInDialog.setOnClickListener(v -> dialog.cancel());
    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;
        TextView discount;
        SwipeRevealLayout swipeRevealLayout;
        ImageView foodRow;
        ImageView delRow;
        ImageView editRow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameFoodRow);
            price = itemView.findViewById(R.id.priceFoodRow);
            discount = itemView.findViewById(R.id.discountFoodRow);
            swipeRevealLayout = itemView.findViewById(R.id.Swipe_row_food);
            delRow = itemView.findViewById(R.id.imgDelFoodRow);
            editRow = itemView.findViewById(R.id.imgEditFoodRow);
            foodRow = itemView.findViewById(R.id.imgFoodRow);
        }
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
