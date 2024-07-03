package com.fetch.androidtakehome.ViewModel;

import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;

import com.fetch.androidtakehome.Models.Item;
import com.fetch.androidtakehome.Repository.ItemsRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ItemViewModel extends ViewModel {

    private ItemsRepository itemsRepository;
    private LiveData<List<Item>> items;

    public ItemViewModel()
    {
        itemsRepository = new ItemsRepository();
        items = Transformations.map(itemsRepository.getItems(), this::sortItems);
    }

    private List<Item> sortItems(List<Item> items) {
        if (items != null) {
            return items.stream()
                    .sorted(Comparator.comparingInt(Item::getListId)
                            .thenComparingInt(Item::getId))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }


    public LiveData<List<Item>> getItems()
    {
        return items;
    }

}

