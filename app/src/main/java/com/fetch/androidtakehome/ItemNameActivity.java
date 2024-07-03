package com.fetch.androidtakehome;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fetch.androidtakehome.Adapter.ItemNameAdapter;
import com.fetch.androidtakehome.Models.Item;
import com.fetch.androidtakehome.ViewModel.ItemViewModel;

import java.util.List;
import java.util.stream.Collectors;

public class ItemNameActivity extends AppCompatActivity {
    private ItemViewModel viewModel;
    private ItemNameAdapter itemAdapter;
    private int listId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemview);

        listId = getIntent().getIntExtra("listId", -1);

        RecyclerView recyclerView = findViewById(R.id.ItemNameView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemAdapter = new ItemNameAdapter();
        recyclerView.setAdapter(itemAdapter);

        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        viewModel.getItems().observe(this, items -> {
            if (items != null && !items.isEmpty()) {
                List<Item> filteredItems = items.stream()
                        .filter(item -> item.getListId() == listId)
                        .collect(Collectors.toList());
                itemAdapter.setItems(filteredItems);
            } else {
                Log.d("ItemListActivity", "No items received for list ID: " + listId);
            }
        });
    }


}
