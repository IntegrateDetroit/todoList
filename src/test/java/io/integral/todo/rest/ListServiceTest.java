package io.integral.todo.rest;

import io.integral.todo.TodoList;
import io.integral.todo.db.SQLiteList;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ListServiceTest {


        private SQLiteList db;

        private ListService service;

        @Before
        public void setup() {

            this.db = mock(SQLiteList.class);

            this.service = new ListService(this.db);

        }

        @Test
        public void createTodoList_savesToRepository() {
            int expectedID = 3;
            int expectedUserID = 4;
            String expectedTitle = "Specific value";
            String expectedArchiveStatus = "";


            TodoList inputList = new TodoList()
                    .setUserID(expectedUserID).setTitle(expectedTitle).setArchiveStatus(expectedArchiveStatus);

            TodoList expectedTodoList = new TodoList()
                    .setListID(expectedID).setUserID(expectedUserID).setTitle(expectedTitle).setArchiveStatus(expectedArchiveStatus);

            when(this.db.createList(inputList))
                    .thenReturn(expectedTodoList);

            TodoList todoList = this.service.createTodoList(
                    new TodoList().setUserID(expectedUserID).setTitle(expectedTitle).setArchiveStatus(expectedArchiveStatus)
            );

            verify(db)
                    .createList(inputList);

            assertThat(todoList)
                    .isEqualTo(expectedTodoList);
        }

        @Test
        public void getList_returnsListWithCorrectID() {
            int expectedListID = 1;
            int expectedUserID = 4;
            String expectedTitle = "Specific value";
            String expectedArchiveStatus = "";

            TodoList expected_list = new TodoList()
                    .setListID(expectedListID)
                    .setUserID(expectedUserID)
                    .setTitle(expectedTitle)
                    .setArchiveStatus(expectedArchiveStatus);


            when(this.db.findListById(expectedListID))
                    .thenReturn(expected_list);

            TodoList list = this.service.getList(expectedListID);

            verify(this.db)
                    .findListById(expectedListID);

            assertThat(list)
                    .isEqualTo(expected_list);
        }

        @Test
        public void archiveList_returnsArchivedList() {
            int expectedListID = 1;
            int expectedUserID = 4;
            String expectedTitle = "Specific value";
            String expectedArchiveStatus = "Y";

            TodoList expected_list = new TodoList()
                    .setListID(expectedListID)
                    .setUserID(expectedUserID)
                    .setTitle(expectedTitle)
                    .setArchiveStatus(expectedArchiveStatus);

            TodoList inputList = new TodoList()
                    .setListID(expectedListID)
                    .setUserID(expectedUserID)
                    .setTitle(expectedTitle)
                    .setArchiveStatus("");


            when(this.db.archiveListById(inputList))
                    .thenReturn(expected_list);

            TodoList list = this.service.archiveList(inputList);

            verify(this.db)
                    .archiveListById(inputList);

            assertThat(list.getArchiveStatus())
                    .isEqualTo(expectedArchiveStatus);


        }

    @Test
    public void updateList_returnsUpdatedListWithCorrectID() {
        int expectedListID = 1;
        String title = "New Title";
        TodoList expected_list = new TodoList();

        when(this.db.updateListTitle(expectedListID, title))
                .thenReturn(expected_list);

        TodoList list = this.service.updateList(expectedListID, title );

        verify(this.db)
                .updateListTitle(expectedListID, title);

        assertThat(expected_list).isEqualTo(list);
    }

    }

