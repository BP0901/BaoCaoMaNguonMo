package com.stu.doantinhoc.Activities.Admin.ui.Table;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stu.doantinhoc.Activities.Admin.ui.TableInfo.TableInfoActivity;
import com.stu.doantinhoc.Adapter.ISentDataToTableInfo;
import com.stu.doantinhoc.Adapter.TableAdapterRCV;
import com.stu.doantinhoc.Model.FoodTable;
import com.stu.doantinhoc.Model.Table;
import com.stu.doantinhoc.Util.ListData;
import com.stu.doantinhoc.databinding.FragmentTableBinding;

import java.util.ArrayList;

public class TableFragment extends Fragment {

    private FragmentTableBinding binding;
    private RecyclerView rcv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentTableBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initLayout();
        return root;
    }

    private void initLayout() {
        try {
            rcv = binding.rcvTableFood;
            TableAdapterRCV adapter = new TableAdapterRCV(ListData.listTable, new ISentDataToTableInfo() {
                @Override
                public void sentData(int tb) {
                    clickEvent(tb);
                }
            });
            rcv.setAdapter(adapter);
            rcv.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
            rcv.setHasFixedSize(true);
            rcv.setNestedScrollingEnabled(false);
        }catch (Exception e){
            Toast.makeText(getContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void clickEvent(int tb){
        try {
            Intent intent = new Intent(getContext(), TableInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("table",tb);
            intent.putExtras(bundle);
            startActivity(intent);
        }catch (Exception e){
            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        initLayout();
        super.onResume();
    }
}