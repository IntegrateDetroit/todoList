package com.integrate.todo.rest;

import com.integrate.todo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/item")
public class ItemController {
    private final ItemService service;

    @Autowired
    public ItemController(ItemService service ) {
        this.service = service;
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<Item> createItem(@RequestBody Item item) {
        return new ResponseEntity<>(
                this.service.createItem( item ),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Item> readItem(@PathVariable Integer id ) {
        Item list = this.service.getItem( id );
        if(list.getItemID() == -1 )
            return new ResponseEntity<>( list, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>( list, HttpStatus.OK );
    }

    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Item> updateItem(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> newItem
    ) {
        Item inputItem = new Item();
        inputItem.setItemID((int)newItem.get("itemID"));
        inputItem.setListID((int)newItem.get("newListID"));
        inputItem.setDescription((String)newItem.get("newDescription"));

        Item item = this.service.getItem((int)newItem.get("itemID"));
        if(item.getItemID() == -1 ){
            return new ResponseEntity<>( inputItem, HttpStatus.NOT_MODIFIED );}

        return new ResponseEntity<>(this.service.updateItem(inputItem), HttpStatus.OK);
    }
}