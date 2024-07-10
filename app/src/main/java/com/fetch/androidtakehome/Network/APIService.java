package com.fetch.androidtakehome.Network;

import com.fetch.androidtakehome.Model.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Defines the API endpoints for fetching items.
 * Returns a list of items from the server.
 */
public interface APIService {
    @GET("hiring.json")
    Call<List<Item>> getItems(); // Endpoint to fetch a list of items
}