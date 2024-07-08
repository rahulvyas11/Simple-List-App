package com.fetch.androidtakehome.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fetch.androidtakehome.Model.Item;
import com.fetch.androidtakehome.Network.APIClient;
import com.fetch.androidtakehome.Network.APIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemRepository {

    private final APIService apiService;

    public ItemRepository() {
        this(APIClient.getRetrofitInstance().create(APIService.class));
    }

    public ItemRepository(APIService apiService) {
        this.apiService = apiService;
    }

    public LiveData<List<Item>> getItems() {
        MutableLiveData<List<Item>> data = new MutableLiveData<>();
        apiService.getItems().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

                data.setValue(null);
            }
        });
        return data;
    }
}