package io.integral.todo.rest;

import io.integral.todo.Item;
import io.integral.todo.TodoList;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ListControllerTest {
    @Test
    public void createList_returnsHttpStatusCreated() {
        ListService mockService = mock( ListService.class );
        ListController todoListController = new ListController(mockService);

        TodoList expectedTodoList = new TodoList();
        TodoList todoListPassedIn = new TodoList();


        when( mockService.createTodoList( todoListPassedIn ) )
                .thenReturn( expectedTodoList );
        ResponseEntity expectedResponseEntity = new ResponseEntity<>(
                expectedTodoList, HttpStatus.CREATED);

        ResponseEntity<TodoList> responseEntity = todoListController.createList( todoListPassedIn );


        verify( mockService )
                .createTodoList( todoListPassedIn );

        assertThat( responseEntity )
                .isEqualTo( expectedResponseEntity );
    }

    @Test
    public void getList_returnsListAndHttpStatus200() {


        ListService mockService = mock( ListService.class );
        ListController todoListController = new ListController( mockService );

        TodoList expectedTodoList = new TodoList().setListID( 1 );

        ResponseEntity expectedResponse = new ResponseEntity<>(
                expectedTodoList, HttpStatus.OK);

        when( mockService.getList( 1 ) )
                .thenReturn( expectedTodoList );

        ResponseEntity<TodoList> resultList = todoListController.readList(1);


        verify( mockService )
                .getList( 1 );

        assertThat( resultList )
                .isEqualTo( expectedResponse );
    }

    @Test
    public void getList_whenDoesntExist_returnsHttpStatus204(){
        ListService mockService = mock( ListService.class );
        ListController todoListController = new ListController( mockService );

        TodoList expectedTodoList = new TodoList().setListID( -1 );
        int input_id = 8;

        when( mockService.getList( input_id ) )
                .thenReturn( expectedTodoList );

        ResponseEntity expectedResponse = new ResponseEntity<>(
                expectedTodoList,
                HttpStatus.NO_CONTENT
        );

        ResponseEntity<TodoList> resultList = todoListController.readList( input_id );

        verify( mockService )
                .getList( input_id );

        assertThat( resultList )
                .isEqualTo( expectedResponse );
    }

    @Test
    public void updateListTitle_updatesListTitle_returnsHttpStatus200() {
        ListService mockService = mock( ListService.class );
        ListController todoListController = new ListController(mockService);
        HashMap<String, Object> hashMap = new HashMap<>();
        String newTitle = "New Title";
        int listID = 1;
        hashMap.put("id", listID);
        hashMap.put("updatedTitle", newTitle);
        TodoList todoList = new TodoList().setTitle(newTitle).setListID(listID);

        when (mockService.getList(listID))
                .thenReturn( todoList );

        when (mockService.updateList(listID, newTitle))
                .thenReturn( todoList );


        ResponseEntity<TodoList> responseEntity = todoListController.updateList(listID, hashMap);
        TodoList body = responseEntity.getBody();

        verify(mockService).updateList(listID, newTitle);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body.getTitle()).isEqualTo(newTitle);
        assertThat(body.getListID()).isEqualTo(listID);

    }

    @Test
    public void updateListTitle_DoesNOTupdateListTitle_returnsHttpStatus304(){
        ListService mockService = mock( ListService.class );
        ListController todoListController = new ListController(mockService);

        HashMap<String, Object> hashMap = new HashMap<>();
        String newTitle = "";
        int listIDToReturn = -1;
        int listIDToPassIn = 5;
        hashMap.put("id", listIDToPassIn);
        hashMap.put("updatedTitle", "Hello!");
        TodoList todoList = new TodoList().setTitle(newTitle).setListID(listIDToReturn);

        when (mockService.getList(listIDToPassIn))
                .thenReturn( todoList );

        ResponseEntity<TodoList> responseEntity = todoListController.updateList(listIDToPassIn, hashMap);
        TodoList body = responseEntity.getBody();

        verify(mockService).getList(listIDToPassIn);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_MODIFIED);
        assertThat(body.getListID()).isEqualTo(listIDToReturn);

    }

    @Test
    public void archiveList_returnsArchivedListAndHttpStatus200() {
        int expectedListID = 1;
        int expectedUserID = 4;
        String expectedTitle = "Specific value";
        String expectedArchiveStatus = "Y";

        int listIDToPassIn = 1;

        ListService mockService = mock( ListService.class );
        ListController todoListController = new ListController( mockService );

        TodoList expectedTodoList = new TodoList()
                .setListID(expectedListID)
                .setUserID(expectedUserID)
                .setTitle(expectedTitle)
                .setArchiveStatus(expectedArchiveStatus);

        TodoList inputList = new TodoList()
                .setListID(expectedListID)
                .setUserID(expectedUserID)
                .setTitle(expectedTitle)
                .setArchiveStatus("");

        ResponseEntity expectedResponse = new ResponseEntity<>(
                expectedTodoList, HttpStatus.OK);

        when( mockService.getList(listIDToPassIn) )
                .thenReturn( inputList );

        when( mockService.archiveList(inputList ) )
                .thenReturn( expectedTodoList );

        ResponseEntity<TodoList> resultList = todoListController.archiveList(listIDToPassIn);

        verify( mockService )
                .archiveList(inputList );

        assertThat( resultList )
                .isEqualTo( expectedResponse );
    }

    @Test
    public void archiveList_whenListDoesntExist_DoesNotUpdateArchiveStatus_returnsHttpStatus304(){
        int expectedListID = -1;
        int listIDToPassIn = 5;

        ListService mockService = mock( ListService.class );
        ListController todoListController = new ListController( mockService );

        TodoList expectedTodoList = new TodoList()
                .setListID(expectedListID);

        ResponseEntity expectedResponse = new ResponseEntity<>(
                expectedTodoList, HttpStatus.NOT_MODIFIED);

        when( mockService.getList(listIDToPassIn) )
                .thenReturn( expectedTodoList );

        ResponseEntity<TodoList> resultList = todoListController.archiveList(listIDToPassIn);

        verify( mockService )
                .getList(listIDToPassIn );

        assertThat( resultList )
                .isEqualTo( expectedResponse );
    }
    @Test
    public void archiveList_returnsNegativeTwoAndHttpStatus500(){
        int expectedListID = -2;
        int listIDToPassIn = 5;

        ListService mockService = mock( ListService.class );
        ListController todoListController = new ListController( mockService );

        TodoList expectedTodoList = new TodoList()
                .setListID(expectedListID);

        ResponseEntity expectedResponse = new ResponseEntity<>(
                expectedTodoList, HttpStatus.INTERNAL_SERVER_ERROR);

        when( mockService.getList(listIDToPassIn) )
                .thenReturn( expectedTodoList );

        ResponseEntity<TodoList> resultList = todoListController.archiveList(listIDToPassIn);

        verify( mockService )
                .getList(listIDToPassIn );

        assertThat( resultList )
                .isEqualTo( expectedResponse );
    }

    @Test
    public void getListItems_returnsListOfItemsAndHttpStatus200(){
        ListService mockService = mock( ListService.class );
        ListController todoListController = new ListController( mockService );

        int inputListID = 1;
        List<Item> expectedItemList = new LinkedList<>();

        int expectedItemID = 1;
        int expectedListID = 2;
        String expectedDescription = "Specific value";

        Item expected_item = new Item()
                .setItemID(expectedItemID)
                .setListID(expectedListID)
                .setDescription(expectedDescription);

        expectedItemList.add(expected_item);

        ResponseEntity expectedResponse = new ResponseEntity<>(
                expectedItemList, HttpStatus.OK);

        when( mockService.getListItems(inputListID) )
                .thenReturn( expectedItemList );

        ResponseEntity<List> result = todoListController.getListItems(inputListID);

        verify( mockService )
                .getListItems(inputListID);

        assertThat( result )
                .isEqualTo( expectedResponse );

    }

    @Test
    public void getListItems_returnsEmptyListAndHttpStatus204(){
        ListService mockService = mock( ListService.class );
        ListController todoListController = new ListController( mockService );

        int inputListID = 5;
        List<Item> expectedItemList = new LinkedList<>();

        ResponseEntity expectedResponse = new ResponseEntity<>(
                expectedItemList, HttpStatus.NO_CONTENT);

        when( mockService.getListItems(inputListID) )
                .thenReturn( expectedItemList );

        ResponseEntity<List> result = todoListController.getListItems(inputListID);

        verify( mockService )
                .getListItems(inputListID);

        assertThat( result )
                .isEqualTo( expectedResponse );

    }

    @Test
    public void getListItems_returnsListWithNegativeItemAndHttpStatus500(){
        ListService mockService = mock( ListService.class );
        ListController todoListController = new ListController( mockService );

        int inputListID = 5;
        List<Item> expectedItemList = new LinkedList<>();

        int expectedItemID = -1;

        Item expected_item = new Item()
                .setItemID(expectedItemID);

        expectedItemList.add(expected_item);

        ResponseEntity expectedResponse = new ResponseEntity<>(
                expectedItemList, HttpStatus.INTERNAL_SERVER_ERROR);

        when( mockService.getListItems(inputListID) )
                .thenReturn( expectedItemList );

        ResponseEntity<List> result = todoListController.getListItems(inputListID);

        verify( mockService )
                .getListItems(inputListID);

        assertThat( result )
                .isEqualTo( expectedResponse );

    }

    @Test
    public void deleteList_deletesListById_returnsHttpStatus410(){
        ListService mockService = mock( ListService.class );
        ListController mockController = new ListController( mockService );

        TodoList expectedList = new TodoList();
        expectedList.setListID(1);
        int expectedID = expectedList.getListID();

        when(mockService.deleteList(expectedID)).thenReturn(expectedList);

        ResponseEntity expectedResponseEntity = new ResponseEntity<>(
                expectedList, HttpStatus.GONE);

        ResponseEntity<TodoList> responseEntity = mockController.deleteListById(expectedID);

        verify(mockService).deleteList(expectedID);

        assertThat(responseEntity).isEqualTo( expectedResponseEntity );

    }
}