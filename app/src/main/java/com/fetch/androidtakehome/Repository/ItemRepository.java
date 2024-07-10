package com.fetch.androidtakehome.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fetch.androidtakehome.Model.Item;
import com.fetch.androidtakehome.Network.APIClient;
import com.fetch.androidtakehome.Network.APIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Responsible for data access to the rest of the application.
 * It interacts with the API service to fetch data from the remote server and updates LiveData objects.
 */
public class ItemRepository implements ItemRepositoryInterface {

    private final APIService apiService;  // Service to handle API calls

    // Constructor to initialize APIService
    public ItemRepository() {
        this(APIClient.getRetrofitInstance().create(APIService.class));
    }

    // Constructor for dependency injection
    public ItemRepository(APIService apiService) {
        this.apiService = apiService;
    }

    @Override
    public LiveData<List<Item>> getItems() {
        MutableLiveData<List<Item>> data = new MutableLiveData<>();

        // Make an API call to get items
        apiService.getItems().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(@NonNull Call<List<Item>> call, @NonNull Response<List<Item>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body()); // Update LiveData with response data
                } else {
                    data.setValue(null); // Handle case where response is not successful
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Item>> call, @NonNull Throwable t) {
                data.setValue(null); // Handle API call failure
            }
        });

        return data; // Return LiveData containing the list of items
    }
}
