package com.integrate.todo;

import java.util.Objects;

public class Item {

    private Integer itemID;
    private Integer listID;
    private String description;

    public Integer getItemID() {
        return itemID;
    }

    public Item setItemID(Integer itemID) {
        this.itemID = itemID;
        return this;
    }

    public Integer getListID() {
        return listID;
    }

    public Item setListID(Integer listID) {
        this.listID = listID;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Item setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(itemID, item.itemID) &&
                Objects.equals(listID, item.listID) &&
                Objects.equals(description, item.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(itemID, listID, description);
    }
}