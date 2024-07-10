package com.fetch.androidtakehome.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.fetch.androidtakehome.Model.Item;
import com.fetch.androidtakehome.Repository.ItemRepository;
import com.fetch.androidtakehome.Repository.ItemRepositoryInterface;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ViewModel class for managing UI-related data for items.
 * fetches, filters, and sorts the items from the repository and provides them as LiveData.
 */
public class ItemViewModel extends ViewModel {

    private final ItemRepositoryInterface itemRepository;
    private final LiveData<List<Item>> items;

    // Default constructor that uses the default ItemRepository
    public ItemViewModel() {
        this(new ItemRepository());
    }

    // Constructor for dependency injection
    public ItemViewModel(ItemRepositoryInterface itemRepository) {

        this.itemRepository = itemRepository;
        items = Transformations.map(itemRepository.getItems(), this::filterAndSortItems);
    }

    // Method to filter and sort the items
    private List<Item> filterAndSortItems(List<Item> items) {
        if (items != null) {
            return items.stream()
                    .filter(item -> item != null)
                    .filter(item -> item.getListId() != null && item.getId() != null)
                    .filter(item -> item.getName() != null && !item.getName().trim().isEmpty())
                    .sorted(Comparator.comparingInt(Item::getListId)
                            .thenComparingInt(Item::getId))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    // Method to get the filtered and sorted items as LiveData
    public LiveData<List<Item>> getItems() {
        return items;
    }
}
