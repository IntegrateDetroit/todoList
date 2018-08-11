package io.integral.todo.db;

import io.integral.todo.Item;

public interface DBWrapperItem {
    Item createItem(Item item);

    Item findItemById(int id);

    Item updateItem(Item item);

    Item deleteItem(int itemID);
}
