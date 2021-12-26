package com.stu.doantinhoc.Activities.Admin.ui.FoodMenu.Components;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

import com.stu.doantinhoc.Adapter.SearchFoodAdapter;
import com.stu.doantinhoc.R;
import com.stu.doantinhoc.Util.ListData;
import com.stu.doantinhoc.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;
    private RecyclerView rcvSearch;
    private SearchView searchView;
    private SearchFoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        rcvSearch = binding.rcvFoodSearch;
        loadSearchView();
    }

    private void loadSearchView() {
        adapter = new SearchFoodAdapter(ListData.listFood, SearchActivity.this);
        rcvSearch.setAdapter(adapter);
        rcvSearch.setLayoutManager(new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL, false));
        rcvSearch.setHasFixedSize(true);
        rcvSearch.setNestedScrollingEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.actin_search).getActionView();
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        if(!searchView.isIconified()){
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}