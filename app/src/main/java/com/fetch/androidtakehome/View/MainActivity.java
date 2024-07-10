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
/**
 * Serves as the main entry point for the application.
 * Responsible for setting up the UI components, initializing the ViewModel, and observing data changes.
 * The activity displays a list of items grouped by their list IDs in a RecyclerView and handles UI interactions.
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView listRecyclerView;  // RecyclerView to display the list of items
    private ListIDAdapter listIDAdapter;    // Adapter to bind the data to the RecyclerView
    private ItemViewModel itemViewModel;    // ViewModel to manage UI-related data
    private TextView noItemsTextView;       // TextView to display when no items are available

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();         // Initialize the views
        setupRecyclerView(); // Set up the RecyclerView
        setupViewModel();    // Set up the ViewModel
        observeViewModel();  // Observe changes in the ViewModel
    }

    // Method to initialize the views
    private void initViews() {
        listRecyclerView = findViewById(R.id.recyclerview_main_listidcard);
        noItemsTextView = findViewById(R.id.textview_main_nolists);
    }

    // Method to set up the RecyclerView
    private void setupRecyclerView() {
        listRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Method to set up the ViewModel
    private void setupViewModel() {
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
    }

    // Method to observe changes in the ViewModel and update the UI accordingly
    private void observeViewModel() {
        itemViewModel.getItems().observe(this, items -> {
            if (items != null && !items.isEmpty()) {
                // If items are available, display them in the RecyclerView
                listIDAdapter = new ListIDAdapter(this, items);
                listRecyclerView.setAdapter(listIDAdapter);
                listRecyclerView.setVisibility(View.VISIBLE);
                noItemsTextView.setVisibility(View.GONE);
            } else {
                // If no items are available, display the noItemsTextView
                listRecyclerView.setVisibility(View.GONE);
                noItemsTextView.setVisibility(View.VISIBLE);
            }
        });
    }
}
