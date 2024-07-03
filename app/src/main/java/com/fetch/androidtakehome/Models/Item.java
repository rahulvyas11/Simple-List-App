package com.fetch.androidtakehome.Models;


import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("id")
    private int id;

    @SerializedName("listId")
    private int listId;

    @SerializedName("name")
    private String name;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getListId() { return listId; }
    public void setListId(int listId) { this.listId = listId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Items{" +
                "listId=" + listId +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
