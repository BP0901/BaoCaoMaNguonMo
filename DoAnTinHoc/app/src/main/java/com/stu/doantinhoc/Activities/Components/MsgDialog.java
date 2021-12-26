package com.stu.doantinhoc.Activities.Components;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.stu.doantinhoc.R;

public class MsgDialog {
    public static void showDialog(Context context,String result) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.msg_dialog);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        Button btnOkInDialog = (Button) dialog.findViewById(R.id.btnOkMsg);
        TextView txtvResult = dialog.findViewById((R.id.txtvMsg));
        txtvResult.setText(result);
        btnOkInDialog.setOnClickListener(v -> dialog.cancel());
    }
}
