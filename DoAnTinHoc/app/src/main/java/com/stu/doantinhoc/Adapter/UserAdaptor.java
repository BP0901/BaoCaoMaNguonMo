package com.stu.doantinhoc.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.stu.doantinhoc.Controller.AdminController.UserController;
import com.stu.doantinhoc.Model.User;
import com.stu.doantinhoc.R;
import com.stu.doantinhoc.RequestHttp.UserRequestHttp;
import com.stu.doantinhoc.Util.ListData;
import com.stu.doantinhoc.Util.Url;

import java.util.ArrayList;


public class UserAdaptor extends RecyclerView.Adapter<UserAdaptor.ViewHoder> {

    private ViewBinderHelper binding;
    private Context context;
    private RecyclerView rcv;
    private ArrayList<User> usersList;

    public UserAdaptor(Context context, ArrayList<User> list, RecyclerView rcv) {
        this.context = context;
        this.usersList = list;
        this.rcv = rcv;
        binding = new ViewBinderHelper();
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHoder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        binding.bind(holder.swipeRevealLayout, String.valueOf(usersList.get(position).getUsername()));
        holder.username.setText(usersList.get(position).getUsername());
        holder.password.setText(usersList.get(position).getPassword());
        if (usersList.get(position).isType())
            holder.type.setText("Admin");
        else holder.type.setText("Staff");
        holder.delRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setMessage("Do you really want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserController controller = new UserController(context, rcv);
                                controller.delUserInDB(usersList.get(holder.getAdapterPosition()));
                                usersList.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                ListData.listUser = usersList;
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
        holder.editRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserController controller = new UserController(context,rcv);
                controller.showDialogEditUser(usersList.get(holder.getAdapterPosition()));
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView username;
        TextView password;
        TextView type;
        SwipeRevealLayout swipeRevealLayout;
        ImageView delRow;
        ImageView editRow;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.txtvUsername);
            password = itemView.findViewById(R.id.txtvPassword);
            type = itemView.findViewById(R.id.txtvType);
            swipeRevealLayout = itemView.findViewById(R.id.Swipe_row_user);
            delRow = itemView.findViewById(R.id.imgDelCateUser);
            editRow = itemView.findViewById(R.id.imgEditCateUser);
        }
    }


}
