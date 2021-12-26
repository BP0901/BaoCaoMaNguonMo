package com.stu.doantinhoc.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.stu.doantinhoc.Activities.Components.MsgDialog;
import com.stu.doantinhoc.Controller.AdminController.CaterogyController;
import com.stu.doantinhoc.Model.Category;
import com.stu.doantinhoc.R;
import com.stu.doantinhoc.Util.ListData;
import com.stu.doantinhoc.Util.Url;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class CategoryAdaptorRCV extends RecyclerView.Adapter<CategoryAdaptorRCV.ViewHoder> {

    private ImageView imagHinh;
    private ViewBinderHelper binding;
    private Context context;
    private RecyclerView rcv;
    private ArrayList<Category> listCate;

    public CategoryAdaptorRCV(Context context, ArrayList<Category> listCate, RecyclerView rcv) {
        this.context = context;
        this.listCate = listCate;
        this.rcv = rcv;
        binding = new ViewBinderHelper();
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHoder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        new LoadImage(holder.img).execute(Url.URLLOADIMG+listCate.get(position).getCatePic());
        holder.name.setText(listCate.get(position).getCateName());
        binding.bind(holder.swipeRevealLayout, String.valueOf(listCate.get(position).getId()));
        holder.delRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setMessage("Có chắc muốn xóa?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    CaterogyController controller = new CaterogyController(context);
                                    if(controller.delCateInDB(listCate.get(holder.getAdapterPosition()).getId())) {
                                        listCate.remove(holder.getAdapterPosition());
                                        notifyItemRemoved(holder.getAdapterPosition());
                                        ListData.listCate = listCate;
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
        holder.editRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(listCate.get(holder.getAdapterPosition()));
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCate.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        ImageView delRow;
        ImageView editRow;
        SwipeRevealLayout swipeRevealLayout;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imdCateRow);
            name = itemView.findViewById(R.id.nameCateRow);
            delRow = itemView.findViewById(R.id.imgDelCateRow);
            swipeRevealLayout = itemView.findViewById(R.id.Swipe_row_cate);
            editRow = itemView.findViewById(R.id.imgEditCateRow);
        }
    }

    private void openDialog(Category category) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_category_dialog);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        Button btnAddInDialog = (Button) dialog.findViewById(R.id.btnAddCateDialog);
        Button btnCancelInDialog = (Button) dialog.findViewById(R.id.btnCancelAddCateDialog);
        Button btnSelectPic = (Button) dialog.findViewById(R.id.btnSelectPicCate);
//        imageView = dialog.findViewById(R.id.imgEditCatePic);
        EditText name = dialog.findViewById(R.id.edtEditCateName);
        name.setText(category.getCateName().toString());
//        btnSelectPic.setOnClickListener(v -> );
        btnAddInDialog.setOnClickListener(v -> {
            if (name.getText().toString().isEmpty()) {
                MsgDialog.showDialog(context, "Name is empty!");
                return;
            }
            CaterogyController controller = new CaterogyController(context, rcv);
            controller.updateCate(category, name.getText().toString().trim());
            dialog.cancel();
        });
        btnCancelInDialog.setOnClickListener(v -> dialog.cancel());
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
