package com.fetch.androidtakehome.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Model class representing an item with an ID, list ID, and name.
 * Used Integer for listID and id for better handling of null values
 */
public class Item implements Serializable {
    @SerializedName("id")
    private final Integer id;
    @SerializedName("listId")
    private final Integer listId;
    @SerializedName("name")
    private final String name;

    // Constructor to initialize the item
    public Item(Integer id, Integer listId, String name) {
        this.id = id;
        this.listId = listId;
        this.name = name;
    }

    // Get Methods for Item object
    public Integer getId() {
        return id;
    }

    public Integer getListId() {
        return listId;
    }

    public String getName() {
        return name;
    }
}

