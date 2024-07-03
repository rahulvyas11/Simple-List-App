package com.fetch.androidtakehome.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;
import com.fetch.androidtakehome.Network.APIClient;
import com.fetch.androidtakehome.Network.APIService;
import com.fetch.androidtakehome.Models.Items;

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

    public LiveData<List<Items>> getItems() {
        MutableLiveData<List<Items>> data = new MutableLiveData<>();
        apiService.getItems().enqueue(new Callback<List<Items>>() {
            @Override
            public void onResponse(@NonNull Call<List<Items>> call, @NonNull Response<List<Items>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("ItemRepository", "Successfully fetched Data!");

                    // Filter out items where name is null or empty, and sort by listId
                    List<Items> filteredSortedItems = response.body().stream()
                            .filter(item -> item.getName() != null && !item.getName().isEmpty())
                            .sorted((item1, item2) -> {
                                int listIdComparison = Integer.compare(item1.getListId(), item2.getListId());
                                if (listIdComparison == 0) {
                                    return item1.getName().compareTo(item2.getName());
                                }
                                return listIdComparison;
                            })
                            .collect(Collectors.toList());

                    data.setValue(filteredSortedItems);

                    // Log the filtered and sorted items
                    for (Items item : filteredSortedItems) {
                        Log.d("ItemRepository", item.toString());
                    }
                } else {
                    Log.d("ItemRepository", "Response unsuccessful or body is null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Items>> call, @NonNull Throwable t) {
                // Handle failure
                Log.d("ItemRepository", "Error Fetching Items", t);
            }
        });
        return data;
    }
}
