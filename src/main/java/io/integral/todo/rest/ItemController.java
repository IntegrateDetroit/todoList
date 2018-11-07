package io.integral.todo.rest;

import io.integral.todo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/list/item")
public class ItemController {
    private final ItemService service;

    @Autowired
    public ItemController(ItemService service ) {
        this.service = service;
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<Item> createItem(@RequestBody Item item) {
        item = this.service.createItem( item );
        if (item.getItemID() == -1) {
           return new ResponseEntity<>(item, HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(
               item, HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Item> readItem(@PathVariable Integer id ) {
        Item list = this.service.getItem( id );
        if (list.getItemID() == -1 )
            return new ResponseEntity<>( list, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>( list, HttpStatus.OK );
    }

    @PutMapping
    public @ResponseBody
    ResponseEntity<Item> updateItem(
            //@PathVariable Integer id,
            @RequestBody Map<String, Object> newItem
    ) {
        Item inputItem = new Item();
        inputItem.setItemID((int)newItem.get("itemID"));
        inputItem.setListID((int)newItem.get("listID"));
        inputItem.setDescription((String)newItem.get("description"));

        Item updatedItem = this.service.updateItem(inputItem);
        if (updatedItem.getItemID() == -1 ) {
            return new ResponseEntity<>( updatedItem, HttpStatus.NOT_MODIFIED );}
        if (updatedItem.getItemID() == -2 ){
            return new ResponseEntity<>( updatedItem, HttpStatus.INTERNAL_SERVER_ERROR );}

        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody
    ResponseEntity<Item> deleteItemById(@PathVariable Integer id ) {
        Item itemToBeDeleted = this.service.deleteItem(id);
        if (itemToBeDeleted.getItemID() == -1) {
            return new ResponseEntity<>(itemToBeDeleted, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(itemToBeDeleted, HttpStatus.GONE);
    }
}
