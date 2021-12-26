package com.stu.doantinhoc.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.stu.doantinhoc.Model.Table;
import com.stu.doantinhoc.R;

import java.util.ArrayList;



public class TableAdapterRCV extends RecyclerView.Adapter<TableAdapterRCV.ViewHoder> {
    ArrayList<Table> listTable;
    ISentDataToTableInfo sentData;

    public TableAdapterRCV(ArrayList<Table> listTable, ISentDataToTableInfo sentData) {
        this.listTable = listTable;
        this.sentData = sentData;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHoder(LayoutInflater.from(parent.getContext()).inflate(R.layout.table_row,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        holder.txtvName.setText(listTable.get(position).getName());
        holder.layout.setOnClickListener(v -> sentData.sentData(position));
        if(listTable.get(position).getStatus() == false){
            holder.layout.setBackgroundResource(R.color.grey);
            holder.txtvStatus.setText("(Empty)");
        }
        else{
            holder.layout.setBackgroundResource(R.color.redmain);
            holder.txtvStatus.setText("(Full)");
        }
    }

    @Override
    public int getItemCount() {
        return listTable.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        TextView txtvName;
        TextView txtvStatus;
        ConstraintLayout layout;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            txtvName = itemView.findViewById(R.id.tableRowName);
            txtvStatus = itemView.findViewById(R.id.tableRowStatus);
            layout = itemView.findViewById(R.id.tableRowLayout);
        }
    }
}
