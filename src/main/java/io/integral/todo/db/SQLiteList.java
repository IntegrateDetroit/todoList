package io.integral.todo.db;

import io.integral.todo.Item;
import io.integral.todo.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

@Component
public class SQLiteList implements DBWrapperList {

    @Autowired
    DataSource dataSource;


    @Override
    public TodoList createList(TodoList todoList) {
        String todoListTitle = todoList.getTitle();
        Integer todoListUserID = todoList.getUserID();
        String todoListArchiveStatus = todoList.getArchiveStatus();
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "INSERT INTO List (USER_ID, LIST_NAME, ARCHIVE_STATUS) VALUES ('" + todoListUserID + "','" + todoListTitle + "','" + todoListArchiveStatus + "')"
            );
            statement.close();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT last_insert_rowid()");
            int id = resultSet.getInt("last_insert_rowid()");
            todoList.setListID(id);
            connection.close();
            return todoList;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        todoList.setListID(-1);
        return todoList;
    }

    @Override
    public TodoList findListById(int id) {
        TodoList todoList = new TodoList();

        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery( "SELECT * from List WHERE ID=" + id + ";" );

            if( resultSet.next() == false ) {
                todoList.setListID( -1 );
            } else {
                todoList.setListID( resultSet.getInt( "ID" ) );
                todoList.setUserID((resultSet.getInt("USER_ID")));
                todoList.setTitle( resultSet.getString( "LIST_NAME" ) );
                todoList.setArchiveStatus( resultSet.getString("ARCHIVE_STATUS"));
            }
            connection.close();
            return todoList;
        } catch( SQLException e ) { e.printStackTrace(); }

        todoList.setListID( -1 );
        return todoList;
    }

    @Override
    public TodoList updateListTitle(Integer listID, String newTitle) {
        TodoList todoList = this.findListById(listID);
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            int updateCount = statement.executeUpdate(
                    "UPDATE List SET LIST_NAME = '" + newTitle + "' WHERE ID = " + listID + ";"
            );
            if(updateCount==0){
                todoList.setTitle("");
                todoList.setListID(-1);
            }
            statement.close();
            todoList.setTitle(newTitle);
            return todoList;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        todoList.setTitle("");
        todoList.setListID(-2);
        return todoList;

    }

    @Override
    public TodoList archiveListById(TodoList todoList) {
        int listId = todoList.getListID();
        String archiveStatus = "Y";

        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "UPDATE List SET ARCHIVE_STATUS = '" + archiveStatus + "' WHERE ID = " + listId + ";"
            );
            statement.close();
            todoList.setArchiveStatus(archiveStatus);
            return todoList;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        todoList.setArchiveStatus("");
        todoList.setListID(-1);
        return todoList;

    }

    @Override
    public TodoList deleteList(int listID) {
        int result = 0;
        TodoList listToBeDeleted = findListById(listID);
        if (listToBeDeleted.getListID() > -1) {

            try {
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                result = statement.executeUpdate(
                        "DELETE FROM List WHERE ID = " + listToBeDeleted.getListID() + ";"
                );
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (result == 0) {
            listToBeDeleted.setListID(-1);
        }
        return listToBeDeleted;

    }

    @Override
    public List<Item> getListItemsByID(int listID) {
        List<Item> itemList = new LinkedList<>();

        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * from Item WHERE LIST_ID=" + listID + ";"
            );
            while( resultSet.next() == true) {
                Item item = new Item();
                item.setItemID(resultSet.getInt("ID"));
                item.setListID(resultSet.getInt("LIST_ID"));
                item.setDescription(resultSet.getString("DESCRIPTION"));
                itemList.add(item);
            }
            statement.close();
            return itemList;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        Item failedItem = new Item();
        failedItem.setItemID(-1);
        itemList.add(failedItem);
        return itemList;
    }

}
