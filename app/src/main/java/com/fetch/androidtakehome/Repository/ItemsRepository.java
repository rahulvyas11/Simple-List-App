package com.fetch.androidtakehome.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;
import com.fetch.androidtakehome.Network.APIClient;
import com.fetch.androidtakehome.Network.APIService;
import com.fetch.androidtakehome.Models.Item;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemsRepository {
    private APIService apiService;

    public ItemsRepository() {
        apiService = APIClient.getClient().create(APIService.class);
    }

    public LiveData<List<Item>> getItems() {
        MutableLiveData<List<Item>> data = new MutableLiveData<>();
        apiService.getItems().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(@NonNull Call<List<Item>> call, @NonNull Response<List<Item>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("ItemRepository", "Successfully fetched Data!");

                    // Filter out items where name is null or empty, and sort by listId
                    List<Item> filteredItems = response.body().stream()
                            .filter(item -> item.getName() != null && !item.getName().isEmpty())
                            .collect(Collectors.toList());


                    // Update the LiveData object with the filtered and sorted list
                    data.setValue(filteredItems);

                    // Log the filtered and sorted items
                    for (Item item : filteredItems) {
                        Log.d("ItemRepository",item.toString());
                    }
                } else {
                    Log.d("ItemRepository", "Response unsuccessful or body is null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Item>> call, @NonNull Throwable t) {
                // Handle failure
                Log.d("ItemRepository", "Error Fetching Items", t);
            }
        });
        return data;
    }
}
