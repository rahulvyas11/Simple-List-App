package com.fetch.androidtakehome.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fetch.androidtakehome.Network.APIClient;
import com.fetch.androidtakehome.Network.APIService;
import com.fetch.androidtakehome.Models.Item;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemRepository {
    private final APIService apiService;

    public ItemRepository(APIService apiService) {
        this.apiService = apiService;
    }

    public ItemRepository() {
        this(APIClient.getClient().create(APIService.class));
    }

    public LiveData<List<Item>> getItems() {
        MutableLiveData<List<Item>> data = new MutableLiveData<>();
        apiService.getItems().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(@NonNull Call<List<Item>> call, @NonNull Response<List<Item>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Filter out items that are null or have null id or listid and where name is null or empty
                    List<Item> filteredItems = response.body().stream()
                            .filter(item-> item !=null)
                            .filter(item -> item.getId()!=null && item.getListId()!=null && item.getName() != null && !item.getName().trim().isEmpty() )
                            .collect(Collectors.toList());
                    data.postValue(filteredItems);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Item>> call, @NonNull Throwable t) {

            }
        });
        return data;
    }
}
