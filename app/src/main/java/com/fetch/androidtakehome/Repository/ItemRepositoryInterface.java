package com.fetch.androidtakehome.Repository;

import androidx.lifecycle.LiveData;
import com.fetch.androidtakehome.Model.Item;
import java.util.List;

public interface ItemRepositoryInterface {
    LiveData<List<Item>> getItems();
}
