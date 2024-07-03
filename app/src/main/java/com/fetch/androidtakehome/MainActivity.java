package com.fetch.androidtakehome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fetch.androidtakehome.Adapter.ListIDAdapter;
import com.fetch.androidtakehome.Models.Item;
import com.fetch.androidtakehome.ViewModel.ItemViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private ItemViewModel viewModel;
    private ListIDAdapter listIdAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.ListIDView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listIdAdapter = new ListIDAdapter(new ArrayList<>(), listId -> {
            Intent intent = new Intent(MainActivity.this, ItemNameActivity.class);
            intent.putExtra("listId", listId);
            startActivity(intent);
        });

        recyclerView.setAdapter(listIdAdapter);

        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        viewModel.getItems().observe(this, items -> {
            if (items != null && !items.isEmpty()) {
                // Create a map of listId to item count
                Map<Integer, Long> itemCountMap = items.stream()
                        .collect(Collectors.groupingBy(Item::getListId, Collectors.counting()));

                List<Integer> listIds = new ArrayList<>(itemCountMap.keySet());
                listIdAdapter.updateListIds(listIds, itemCountMap);
            } else {
                Log.d("MainActivity", "No items received");
            }
        });
    }
}