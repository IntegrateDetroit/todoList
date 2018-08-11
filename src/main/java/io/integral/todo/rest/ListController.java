package io.integral.todo.rest;

import io.integral.todo.Item;
import io.integral.todo.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/list")
public class ListController {
        private final ListService service;

        @Autowired
        public ListController(ListService service ) {
            this.service = service;
        }

        @PostMapping
        public @ResponseBody
        ResponseEntity<TodoList> createList(@RequestBody TodoList todoList) {
            return new ResponseEntity<>(
                    this.service.createTodoList( todoList ),
                    HttpStatus.CREATED
            );
        }

        @GetMapping("/{id}")
        public @ResponseBody
        ResponseEntity<TodoList> readList( @PathVariable Integer id ) {
            TodoList list = this.service.getList( id );
            if(list.getListID() == -1 )
                return new ResponseEntity<>( list, HttpStatus.NO_CONTENT);
            return new ResponseEntity<>( list, HttpStatus.OK );
        }

        @PutMapping("/{id}")
        public @ResponseBody
        ResponseEntity<TodoList> updateList(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> newTitle
        ) {
            TodoList list = this.service.getList( id );
            if(list.getListID() == -1 )
                return new ResponseEntity<>( list, HttpStatus.NOT_MODIFIED );
            return new ResponseEntity<>(this.service.updateList(id, newTitle.get("updatedTitle").toString()), HttpStatus.OK);
        }


        @DeleteMapping("/{id}")
        public @ResponseBody
        ResponseEntity<TodoList> deleteListById(@PathVariable Integer id ) {
            TodoList listToBeDeleted = this.service.deleteList(id);
            if (listToBeDeleted.getListID() == -1) {
                return new ResponseEntity<>(listToBeDeleted, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(listToBeDeleted, HttpStatus.GONE);
        }

        @PutMapping("archive/{listID}")
        public @ResponseBody
        ResponseEntity<TodoList> archiveList(@PathVariable Integer listID) {
            TodoList list = this.service.getList(listID);
            if(list.getListID() == -1 )
                return new ResponseEntity<>( list, HttpStatus.NOT_MODIFIED);
            if(list.getListID() == -2 )
                return new ResponseEntity<>( list, HttpStatus.INTERNAL_SERVER_ERROR);

            return new ResponseEntity<>(this.service.archiveList(list), HttpStatus.OK);
        }

    @GetMapping("/listItems/{listID}")
    public @ResponseBody
    ResponseEntity<List> getListItems( @PathVariable Integer listID ) {
        List<Item> itemList = this.service.getListItems( listID );
        if(itemList.isEmpty())
            return new ResponseEntity<>( itemList, HttpStatus.NO_CONTENT);
        if(itemList.get(0).getItemID() == -1 ) {
            return new ResponseEntity<>( itemList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>( itemList, HttpStatus.OK );
    }
}
