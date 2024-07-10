package com.fetch.androidtakehome.Repository;

import androidx.lifecycle.LiveData;

import com.fetch.androidtakehome.Model.Item;

import java.util.List;

/**
 * Defines the contract for data operations related to fetching items.
 * Provides a method to get a list of items wrapped in LiveData.
 */
public interface ItemRepositoryInterface {
    LiveData<List<Item>> getItems();
}
