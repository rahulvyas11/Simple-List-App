package com.fetch.androidtakehome.Network;
import com.fetch.androidtakehome.Models.Items;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("hiring.json")
    Call<List<Items>> getItems();

}
