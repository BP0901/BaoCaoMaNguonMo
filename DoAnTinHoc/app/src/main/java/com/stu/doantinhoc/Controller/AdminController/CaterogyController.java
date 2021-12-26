package com.stu.doantinhoc.Controller.AdminController;

import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stu.doantinhoc.Activities.Components.MsgDialog;
import com.stu.doantinhoc.Adapter.CategoryAdaptorRCV;
import com.stu.doantinhoc.Model.Category;
import com.stu.doantinhoc.Model.Food;
import com.stu.doantinhoc.RequestHttp.CategoryRequestHttp;
import com.stu.doantinhoc.Util.ListData;
import com.stu.doantinhoc.Util.Url;

public class CaterogyController{
    private Context context;
    private RecyclerView recyclerViewCate;
    private CategoryRequestHttp requestHttp;
    private CategoryAdaptorRCV adaptorRCV;

    public CaterogyController(Context context, RecyclerView recyclerViewCate) {
        this.context = context;
        this.recyclerViewCate = recyclerViewCate;
        this.requestHttp = new CategoryRequestHttp(context);
    }
    public CaterogyController(Context context){
        this.context = context;
        this.requestHttp = new CategoryRequestHttp(context);
    }

    public void loadRCVCate() {
        adaptorRCV = new CategoryAdaptorRCV(context, ListData.listCate, recyclerViewCate);
        recyclerViewCate.setAdapter(adaptorRCV);
        recyclerViewCate.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        recyclerViewCate.setHasFixedSize(true);
        recyclerViewCate.setNestedScrollingEnabled(false);
    }

    public void addCateToData(String name, String pic) {
        if (name.isEmpty()) {
            MsgDialog.showDialog(context, "Name is empty!");
            return;
        }
        StringBuilder url = new StringBuilder(Url.URLINSERTCATE);
        url.append("&catename=");
        url.append(name);
        requestHttp.insertAndDeleteAndUpdate(url.toString());

        int id = ListData.listCate.get(ListData.listCate.size() - 1).getId() + 1;
        ListData.listCate.add(new Category(id, name, ""));
        MsgDialog.showDialog(context, "Successfully!");
    }

    public boolean delCateInDB(int id) {
        for (Food food : ListData.listFood) {
            if(food.getCategory()==id){
                MsgDialog.showDialog(context,"Không thể xóa do còn thức ăn thuộc danh mục này!");
                return false;
            }
        }
        StringBuilder url = new StringBuilder(Url.URLDELETECATE);
        url.append("&ID=");
        url.append(id);
        requestHttp.insertAndDeleteAndUpdate(url.toString());
        return true;
    }


    public void updateCate(Category category,String name) {
        StringBuilder url = new StringBuilder(Url.URLUPDATECATE);
        url.append("&ID=");
        url.append(category.getId());
        url.append("&catename=");
        url.append(name);
        url.append("&picture=");
        url.append(category.getCatePic());
        Toast.makeText(context,url.toString(),Toast.LENGTH_SHORT).show();
        requestHttp.insertAndDeleteAndUpdate(url.toString());
        for (Category cate:ListData.listCate) {
            if(cate.getId()==category.getId())
                    cate.setCateName(name);
        }
        loadRCVCate();
    }

}
