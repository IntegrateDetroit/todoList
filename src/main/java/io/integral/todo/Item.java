package io.integral.todo;

import java.util.Objects;

public class Item {

    public static final int ITEM_STATUS_DONE = 0;
    public static final int ITEM_STATUS_INCOMPLETE = 1;
    public static final int ITEM_STATUS_IN_PROGRESS = 2;

    private Integer itemID;
    private Integer listID;
    private String description;
    private Long dueDate;
    private int itemStatus = ITEM_STATUS_INCOMPLETE;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemStatus == item.itemStatus &&
                Objects.equals(itemID, item.itemID) &&
                Objects.equals(listID, item.listID) &&
                Objects.equals(description, item.description) &&
                Objects.equals(dueDate, item.dueDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(itemID, listID, description, dueDate, itemStatus);
    }

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

    public int getStatus() {
        return itemStatus;
    }

    public Item setStatus( int status) {
        this.itemStatus = status;
        return this;
    }

    public Long getDueDate() {
        return dueDate;
    }

    public Item setDueDate(Long dueDate) {
        this.dueDate = dueDate;
        return this;
    }


}
