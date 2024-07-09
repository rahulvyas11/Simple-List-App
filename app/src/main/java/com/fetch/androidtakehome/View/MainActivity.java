package com.fetch.androidtakehome.View;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fetch.androidtakehome.Adapter.ListIDAdapter;
import com.fetch.androidtakehome.R;
import com.fetch.androidtakehome.ViewModel.ItemViewModel;

public class MainActivity extends AppCompatActivity {

    private RecyclerView listRecyclerView;
    private ListIDAdapter listIDAdapter;
    private ItemViewModel itemViewModel;
    private TextView noItemsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupRecyclerView();
        setupViewModel();
        observeViewModel();
    }

    private void initViews() {
        listRecyclerView = findViewById(R.id.recyclerview_main_listidcard);
        noItemsTextView = findViewById(R.id.textview_main_nolists);
    }

    private void setupRecyclerView() {
        listRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupViewModel() {
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
    }

    private void observeViewModel() {
        itemViewModel.getItems().observe(this, items -> {
            if (items != null && !items.isEmpty()) {
                listIDAdapter = new ListIDAdapter(items);
                listRecyclerView.setAdapter(listIDAdapter);
                listRecyclerView.setVisibility(View.VISIBLE);
                noItemsTextView.setVisibility(View.GONE);
            } else {
                listRecyclerView.setVisibility(View.GONE);
                noItemsTextView.setVisibility(View.VISIBLE);
            }
        });
    }
}
