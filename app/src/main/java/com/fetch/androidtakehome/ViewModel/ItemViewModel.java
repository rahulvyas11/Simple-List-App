package com.fetch.androidtakehome.ViewModel;

import androidx.appcompat.view.menu.MenuView;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;

import com.fetch.androidtakehome.Models.Items;
import com.fetch.androidtakehome.Repository.ItemsRepository;

import java.util.List;

public class ItemViewModel extends ViewModel {

    private ItemsRepository itemsRepository;
    private LiveData<List<Items>> items;

    public ItemViewModel()
    {
        itemsRepository = new ItemsRepository();
        items = itemsRepository.getItems();
    }

    public LiveData<List<Items>> getItems()
    {
        return items;
    }

}

