package com.stu.doantinhoc.Controller.AdminController;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stu.doantinhoc.Activities.Components.MsgDialog;
import com.stu.doantinhoc.Adapter.UserAdaptor;
import com.stu.doantinhoc.R;
import com.stu.doantinhoc.RequestHttp.UserRequestHttp;
import com.stu.doantinhoc.Util.ListData;
import com.stu.doantinhoc.Model.User;
import com.stu.doantinhoc.Util.Url;

import java.util.ArrayList;

public class UserController {

    private Context context;
    private ArrayList<User> listUser;
    private RecyclerView rcv;
    private UserAdaptor adaptor;

    public UserController(Context context,RecyclerView rcv) {
        this.context = context;
        this.rcv = rcv;
        this.listUser = new ArrayList<>();
    }

    public void loadRCVUser() {
        this.listUser = ListData.listUser;
        adaptor = new UserAdaptor(context, listUser, rcv);
        rcv.setAdapter(adaptor);
        rcv.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        rcv.setHasFixedSize(true);
        rcv.setNestedScrollingEnabled(false);
    }

    //region Add new user

    public void clickAddUser() {
        showDialogAddUser();
        loadRCVUser();
    }

    private void showDialogAddUser() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_user_dialog);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        //region Các sự kiện trong dialog
        Button btnAddInDialog = (Button) dialog.findViewById(R.id.btnAddUserDialog);
        Button btnCancelInDialog = (Button) dialog.findViewById(R.id.btnCancelAddUserDialog);
        btnAddInDialog.setOnClickListener(v -> {
            RadioButton check = dialog.findViewById(R.id.rdbAdmin);
            EditText username = dialog.findViewById(R.id.edtAddUsername);
            EditText pass = dialog.findViewById(R.id.edtAddPassword);
            EditText repass = dialog.findViewById(R.id.edtAddRePassword);
            boolean confirm;
            if (check.isChecked())
                confirm = addUser(username.getText().toString().trim(), pass.getText().toString().trim(), repass.getText().toString().trim(), true);
            else
                confirm = addUser(username.getText().toString().trim(), pass.getText().toString().trim(), repass.getText().toString().trim(), false);
            if(confirm)
                dialog.cancel();
        });
        btnCancelInDialog.setOnClickListener(v -> dialog.cancel());
        //endregion
    }

    private boolean addUser(String username, String pass, String repass, boolean type) {
        if (validUser(username, pass, repass)) {
            User newUser = new User(username, pass, type);
            UserRequestHttp requestHttp = new UserRequestHttp(context);
            StringBuilder url = new StringBuilder(Url.URLINSERTUSER);
            url.append("&username=");
            url.append(newUser.getUsername());
            url.append("&password=");
            url.append(newUser.getPassword());
            if (newUser.isType())
               url.append("&type=0");
            else
                url.append("&type=1");
            ListData.listUser.add(newUser);
            requestHttp.insertUser(url.toString());
            return true;
        }
        return false;
    }

    //region check User is valid?
    private boolean validUser(String username, String pass, String repass) {
        if (username.isEmpty()) {
            MsgDialog.showDialog(context, "Username is empty!");
            return false;
        }
        if (pass.isEmpty()) {
            MsgDialog.showDialog(context, "Password is empty!");
            return false;
        }
        if (repass.isEmpty()) {
            MsgDialog.showDialog(context, "Re-Password is empty!");
            return false;
        }
        if (!pass.equals(repass)) {
            MsgDialog.showDialog(context, "Re-Password is not correct!");
            return false;
        }
        for (User user : listUser) {
            if (user.getUsername().equals(username)) {
                MsgDialog.showDialog(context, "Already have that Username!");
                return false;
            }
        }
        return true;
    }
    //endregion

    //endregion

    //region del user

    public void delUserInDB(User user){
        StringBuilder url = new StringBuilder(Url.URLDELETEUSER);
        url.append("&username=");
        url.append(user.getUsername());
//        Toast.makeText(context,url.toString(),Toast.LENGTH_SHORT).show();
        UserRequestHttp requestHttp = new UserRequestHttp(context);
        requestHttp.deleteUser(url.toString());
    }

    //endregion

    //region Edit User pass

    public void showDialogEditUser(User user) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.edit_user_dialog);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        //region Các sự kiện trong dialog
        Button btnAddInDialog = (Button) dialog.findViewById(R.id.btnEditUserDialog);
        Button btnCancelInDialog = (Button) dialog.findViewById(R.id.btnCancelEditUserDialog);
        TextView username = dialog.findViewById(R.id.txtvEditUser);
        username.setText(user.getUsername().toString());
        EditText cpass = dialog.findViewById(R.id.edtEditCurrentPass);
        EditText npass = dialog.findViewById(R.id.edtEditNewPass);
        EditText repass = dialog.findViewById(R.id.edtEditRePassword);
        btnAddInDialog.setOnClickListener(v -> {
            boolean confirm = isInputValid(user,cpass.getText().toString().trim(),npass.getText().toString().trim(),repass.getText().toString().trim());
            if(confirm){
                updatePass(user,npass.getText().toString().trim());
                loadRCVUser();
                dialog.cancel();
            }
        });
        btnCancelInDialog.setOnClickListener(v -> dialog.cancel());
        //endregion
    }

    private boolean isInputValid(User user, String cpass, String npass,String repass){
        if(cpass.isEmpty()){
            MsgDialog.showDialog(context,"Your current password is not correct!");
            return false;
        }
        if(npass.isEmpty()){
            MsgDialog.showDialog(context,"Your new password is empty!");
            return false;
        }
        if(repass.isEmpty()){
            MsgDialog.showDialog(context,"Re-password is empty!");
            return false;
        }
        if(!npass.equals(repass)){
            MsgDialog.showDialog(context,"Re-password is not correct!");
            return false;
        }
        if(!user.getPassword().equals(cpass)){
            MsgDialog.showDialog(context,"Your current password is not correct!");
            return false;
        }
        return true;
    }

    private void updatePass(User user, String newPass){
        StringBuilder url = new StringBuilder(Url.URLUPDATEUSER);
        url.append("&username=");
        url.append(user.getUsername());
        url.append("&password=");
        url.append(newPass);
        UserRequestHttp requestHttp = new UserRequestHttp(context);
        requestHttp.updateUser(url.toString());
        user.setPassword(newPass);
        for (User userr: ListData.listUser) {
            if(userr.getUsername().equals(user.getUsername()))
                userr.setPassword(newPass);
        }
    }

    //endregion
}
