package io.integral.todo;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTest {

    @Test
    public void testNewItemHasDefaultIncompleteStatus() {
        Item item = new Item();
        assertEquals(Item.ITEM_STATUS_INCOMPLETE, item.getStatus());
    }

    @Test
    public void testNewItemHasBeenMarkedDone() {
        Item item = new Item();
        item.setStatus(Item.ITEM_STATUS_DONE);
        assertEquals(Item.ITEM_STATUS_DONE, item.getStatus());
    }

}