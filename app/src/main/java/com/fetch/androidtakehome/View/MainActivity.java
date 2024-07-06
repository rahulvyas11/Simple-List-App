package com.fetch.androidtakehome.View;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fetch.androidtakehome.Adapter.ListIDAdapter;
import com.fetch.androidtakehome.R;
import com.fetch.androidtakehome.ViewModel.ItemViewModel;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListIDAdapter listIDAdapter;
    private ItemViewModel itemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview_main_listidcard);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        itemViewModel.getItems().observe(this, items -> {
            if (items != null) {
                listIDAdapter = new ListIDAdapter(MainActivity.this, items);
                recyclerView.setAdapter(listIDAdapter);
            }
        });
    }
}