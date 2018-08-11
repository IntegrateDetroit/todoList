package io.integral.todo.rest;

import io.integral.todo.Item;
import io.integral.todo.db.SQLiteItem;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ItemServiceTest {

    private SQLiteItem db;

    private ItemService service;

    @Before
    public void setup() {

        this.db = mock(SQLiteItem.class);

        this.service = new ItemService(this.db);

    }

    @Test
    public void createItem_savesToRepository() {
        int expectedID = 3;
        int expectedItemID = 4;
        long expectedDueDate = 222222222;
        String expectedDescription = "Specific value";

        Item inputItem = new Item()
                .setItemID(expectedItemID).setDescription(expectedDescription)
                .setDueDate(expectedDueDate);

        Item expectedItem = new Item()
                .setItemID(expectedID).setItemID(expectedItemID).setDescription(expectedDescription)
                .setDueDate(expectedDueDate);


        when(this.db.createItem(inputItem))
                .thenReturn(expectedItem);

        Item item = this.service.createItem(
                new Item().setItemID(expectedItemID).setDescription(expectedDescription).setDueDate(expectedDueDate)
        );

        verify(db)
                .createItem(inputItem);

        assertThat(item)
                .isEqualTo(expectedItem);
    }

    @Test
    public void getItem_returnsItemWithCorrectID() {
        int expectedItemID = 1;
        int expectedListID = 2;
        long expectedDueDate = 222222222;
        String expectedDescription = "Specific value";

        Item expected_item = new Item()
                .setItemID(expectedItemID)
                .setListID(expectedListID)
                .setDescription(expectedDescription)
                .setDueDate(expectedDueDate);


        when(this.db.findItemById(expectedItemID))
                .thenReturn(expected_item);

        Item item = this.service.getItem(expectedItemID);

        verify(this.db)
                .findItemById(expectedItemID);

        assertThat(item)
                .isEqualTo(expected_item);
    }

    @Test
    public void updateItem_returnsUpdatedItemWithCorrectID() {
        int inputItemID = 1;
        int inputListID = 2;
        String inputDescription = "New Description";
        long inputDueDate = 222222222;

        Item inputItem = new Item()
                .setItemID(inputItemID)
                .setListID(inputListID)
                .setDescription(inputDescription)
                .setDueDate(inputDueDate);

        int expectedItemID = 1;
        int expectedListID = 2;
        String expectedDescription = "New Description";
        long expectedDueDate = 222222222;

        Item expected_item = new Item()
                .setItemID(expectedItemID)
                .setListID(expectedListID)
                .setDescription(expectedDescription)
                .setDueDate(expectedDueDate);

        when(this.db.updateItem(inputItem))
                .thenReturn(expected_item);

        Item item = this.service.updateItem(inputItem);

        verify(this.db)
                .updateItem(inputItem);

        assertThat(expected_item).isEqualTo(item);
    }
}