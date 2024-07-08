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

public class ItemViewModel extends ViewModel {

    private ItemRepositoryInterface itemRepository;
    private LiveData<List<Item>> items;

    public ItemViewModel() {
        this(new ItemRepository());
    }

    public ItemViewModel(ItemRepositoryInterface itemRepository) {

        this.itemRepository = itemRepository;
        items = Transformations.map(itemRepository.getItems(), this::filterAndSortItems);
    }

    private List<Item> filterAndSortItems(List<Item> items) {
        if (items != null) {
            return items.stream()
                    .filter(item -> item != null)
                    .filter(item -> item.getName() != null && !item.getName().trim().isEmpty())
                    .sorted(Comparator.comparingInt(Item::getListId)
                            .thenComparingInt(Item::getId))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public LiveData<List<Item>> getItems() {
        return items;
    }
}
