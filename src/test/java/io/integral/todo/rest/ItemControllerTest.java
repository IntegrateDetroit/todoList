package io.integral.todo.rest;

import io.integral.todo.Item;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    ItemService mockService;
    ItemController itemController;

    @Before
    public void setUp(){
         mockService = mock( ItemService.class );
         itemController = new ItemController(mockService);
    }


    @Test
    public void createItem_returnsHttpStatusCreated() {
        Item expectedItem = new Item().setItemID(999);
        Item itemPassedIn = new Item().setDescription("New Item");


        when( mockService.createItem( itemPassedIn ) )
                .thenReturn( expectedItem );
        ResponseEntity expectedResponseEntity = new ResponseEntity<>(
                expectedItem, HttpStatus.CREATED);

        ResponseEntity<Item> responseEntity = itemController.createItem( itemPassedIn );


        verify( mockService )
                .createItem( itemPassedIn );

        assertThat( responseEntity )
                .isEqualTo( expectedResponseEntity );
    }

    @Test
    public void createItem_returnsHTTPstatus304_whenItemIsNotCreated() {
        Item item = new Item ().setDescription("New Item");
        Item expectedItem = new Item().setItemID(-1);

        when (mockService.createItem(item)).thenReturn(expectedItem);


        ResponseEntity<Item> result = itemController.createItem(item);

        assertThat( result.getBody() ).isEqualTo(expectedItem);
        assertThat( result.getStatusCode()).isEqualTo(HttpStatus.NOT_MODIFIED);

        verify(mockService).createItem(item);
    }


    @Test
    public void getItem_returnsItemAndHttpStatus200() {
        int expectedID = 3;
        int expectedListID = 4;
        String expectedDescription = "Specific value";

        Item expectedItem = new Item().setItemID(expectedID ).setListID(expectedListID).setDescription(expectedDescription);

        ResponseEntity expectedResponse = new ResponseEntity<>(
                expectedItem, HttpStatus.OK);

        when( mockService.getItem( 1 ) )
                .thenReturn( expectedItem );

        ResponseEntity<Item> resultItem = itemController.readItem(1);


        verify( mockService )
                .getItem( 1 );

        assertThat( resultItem )
                .isEqualTo( expectedResponse );
    }

    @Test
    public void getItem_whenDoesntExist_returnsHttpStatus204(){

        Item expectedItem = new Item().setItemID( -1 );
        int input_id = 8;

        when( mockService.getItem( input_id ) )
                .thenReturn( expectedItem );

        ResponseEntity expectedResponse = new ResponseEntity<>(
                expectedItem,
                HttpStatus.NO_CONTENT
        );

        ResponseEntity<Item> resultItem = itemController.readItem( input_id );

        verify( mockService )
                .getItem( input_id );

        assertThat( resultItem )
                .isEqualTo( expectedResponse );
    }

    @Test
    public void updateItemTitle_updatesItemTitle_returnsHttpStatus200() {

        HashMap<String, Object> hashMap = new HashMap<>();

        int expectedID = 3;
        int expectedListID = 4;
        String expectedDescription = "Specific value";
        long expectedDueDate = 222222222;
        int expectedStatus = Item.ITEM_STATUS_DONE;

        hashMap.put("itemID", expectedID);
        hashMap.put("listID", expectedListID);
        hashMap.put("description", expectedDescription);
        hashMap.put("dueDate", expectedDueDate);
        hashMap.put("status", expectedStatus);

        Item inputItem = new Item()
                .setItemID(expectedID)
                .setListID(expectedListID)
                .setDescription(expectedDescription)
                .setDueDate(expectedDueDate)
                .setStatus(expectedStatus);

        Item expectedItem = new Item()
                .setItemID(expectedID)
                .setListID(expectedListID)
                .setDescription(expectedDescription)
                .setDueDate(expectedDueDate)
                .setStatus(expectedStatus);

        when (mockService.updateItem(inputItem))
                .thenReturn( expectedItem );

        ResponseEntity<Item> responseEntity = itemController.updateItem(hashMap);

        Item body = responseEntity.getBody();

        verify(mockService).updateItem(inputItem);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body.getDescription()).isEqualTo(expectedDescription);
        assertThat(body.getListID()).isEqualTo(expectedListID);
        assertThat(body.getItemID()).isEqualTo(expectedID);

    }

    @Test
    public void updateItem_DoesNOTupdateItem_returnsHttpStatus304(){
        ItemService mockService = mock( ItemService.class );
        ItemController itemController = new ItemController(mockService);

        HashMap<String, Object> hashMap = new HashMap<>();

        int inputID = 3;
        int inputListID = 4;
        String inputDescription = "Specific value";
        long inputDueDate = 222222222;
        int inputStatus = Item.ITEM_STATUS_DONE;

        int expectedID = -1;
        int expectedListID = 0;
        String expectedDescription = "";
        long expectedDueDate = 222222222;
        int expectedStatus = Item.ITEM_STATUS_INCOMPLETE;


        hashMap.put("itemID", inputID);
        hashMap.put("listID", inputListID);
        hashMap.put("description", inputDescription);
        hashMap.put("dueDate", inputDueDate);
        hashMap.put("status", inputStatus);

        Item inputItem = new Item()
                .setItemID(inputID)
                .setListID(inputListID)
                .setDescription(inputDescription)
                .setDueDate(inputDueDate)
                .setStatus(inputStatus);

        Item expectedItem = new Item()
                .setItemID(expectedID)
                .setListID(expectedListID)
                .setDescription(expectedDescription)
                .setDueDate(expectedDueDate)
                .setStatus(expectedStatus);

        when (mockService.updateItem(inputItem))
                .thenReturn( expectedItem );

        ResponseEntity<Item> responseEntity = itemController.updateItem(hashMap);
        Item body = responseEntity.getBody();

        verify(mockService).updateItem(inputItem);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_MODIFIED);
        assertThat(body.getItemID()).isEqualTo(expectedID);

    }

    @Test
    public void updateItem_returnsWithNegativeTwoAndHttpStatus500(){
        ItemService mockService = mock( ItemService.class );
        ItemController itemController = new ItemController(mockService);

        HashMap<String, Object> hashMap = new HashMap<>();

        int inputID = 3;
        int inputListID = 4;
        String inputDescription = "Specific value";
        long inputDueDate = 222222222;
        int inputStatus = Item.ITEM_STATUS_DONE;


        int expectedID = -2;
        int expectedListID = 0;
        String expectedDescription = "";
        long expectedDueDate = 222222222;
        int expectedStatus = Item.ITEM_STATUS_INCOMPLETE;

        hashMap.put("itemID", inputID);
        hashMap.put("listID", inputListID);
        hashMap.put("description", inputDescription);
        hashMap.put("dueDate", inputDueDate);
        hashMap.put("status", inputStatus);

        Item inputItem = new Item()
                .setItemID(inputID)
                .setListID(inputListID)
                .setDescription(inputDescription)
                .setDueDate(inputDueDate)
                .setStatus(inputStatus);

        Item expectedItem = new Item()
                .setItemID(expectedID)
                .setListID(expectedListID)
                .setDescription(expectedDescription)
                .setDueDate(expectedDueDate)
                .setStatus(expectedStatus);

        when (mockService.updateItem(inputItem))
                .thenReturn( expectedItem );

        ResponseEntity<Item> responseEntity = itemController.updateItem(hashMap);
        Item body = responseEntity.getBody();

        verify(mockService).updateItem(inputItem);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(body.getItemID()).isEqualTo(expectedID);

    }

    @Test
    public void deleteItem_deletesItemById_returnsHttpStatus410(){
        ItemService mockService = mock( ItemService.class );
        ItemController mockController = new ItemController( mockService );

        Item expectedItem = new Item();
        expectedItem.setItemID(1);
        int expectedID = expectedItem.getItemID();

        when(mockService.deleteItem(expectedID)).thenReturn(expectedItem);

        ResponseEntity expectedResponseEntity = new ResponseEntity<>(
                expectedItem, HttpStatus.GONE);

        ResponseEntity<Item> responseEntity = mockController.deleteItemById(expectedID);

        verify(mockService).deleteItem(expectedID);

        assertThat(responseEntity).isEqualTo( expectedResponseEntity );

    }
}