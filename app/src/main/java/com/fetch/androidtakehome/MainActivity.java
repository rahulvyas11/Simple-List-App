package com.fetch.androidtakehome;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fetch.androidtakehome.Adapter.ListIDAdapter;
import com.fetch.androidtakehome.Models.Items;
import com.fetch.androidtakehome.ViewModel.ItemViewModel;

import java.util.ArrayList;
import java.util.List;
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
            // Placeholder for item click action
        });

        recyclerView.setAdapter(listIdAdapter);

        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        viewModel.getItems().observe(this, items -> {
            if (items != null && !items.isEmpty()) {
                List<Integer> listIds = items.stream()
                        .map(Items::getListId)
                        .distinct()
                        .collect(Collectors.toList());
                listIdAdapter.updateListIds(listIds);
            } else {
                Log.d("MainActivity", "No items received");
            }
        });
    }
}