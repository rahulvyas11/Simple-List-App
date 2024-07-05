package com.fetch.androidtakehome.Models;

import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("id")
    private Integer id;

    @SerializedName("listId")
    private Integer listId;

    @SerializedName("name")
    private String name;

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
