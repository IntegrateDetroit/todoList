package io.integral.todo.db;

import io.integral.todo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class SQLiteItem implements DBWrapperItem {

    @Autowired
    DataSource dataSource;

    @Override
    public Item createItem(Item item) {
        Integer listID = item.getListID();
        String description = item.getDescription();
        //Long dueDate = item.getDueDate();
        Integer status = item.getStatus();

        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "INSERT INTO Item (LIST_ID, DESCRIPTION, STATUS) VALUES ('" + listID + "','" + description + "','"+ status +"');"
            );
            statement.close();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT last_insert_rowid()");
            int id = resultSet.getInt("last_insert_rowid()");
            item.setItemID(id);
            connection.close();
            return item;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        item.setItemID(-1);
        return item;
    }

    @Override
    public Item findItemById(int id) {
        Item item = new Item();

        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery( "SELECT * from Item WHERE ID=" + id + ";" );

            if( resultSet.next() == false ) {
                item.setItemID( -1 );
            } else {
                item.setItemID( resultSet.getInt( "ID" ) );
                item.setListID((resultSet.getInt("LIST_ID")));
                item.setDescription( resultSet.getString( "DESCRIPTION" ) );
                item.setDueDate(resultSet.getLong("DUE_DATE"));
                item.setStatus(resultSet.getInt("STATUS"));
            }
            connection.close();
            return item;
        } catch( SQLException e ) { e.printStackTrace(); }

        item.setItemID( -1 );
        return item;
    }

    @Override
    public Item updateItem(Item item) {
        int itemID = item.getItemID();
        int newListID = item.getListID();
        String newDescription = item.getDescription();

        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            int updateCount = statement.executeUpdate("UPDATE Item SET LIST_ID = '" + newListID + "', DESCRIPTION = '"+ newDescription + "' WHERE ID = " + itemID + ";" );
            if (updateCount == 0){
                item.setDescription("");
                item.setItemID(-1);
            }
            statement.close();
            return item;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        item.setDescription("");
        item.setItemID(-2);
        return item;

    }

    @Override
    public Item deleteItem(int itemID) {
        int result = 0;
        Item itemToBeDeleted = findItemById(itemID);
        if (itemToBeDeleted.getItemID() > -1) {

            try {
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                result = statement.executeUpdate(
                        "DELETE FROM Item WHERE ID = " + itemToBeDeleted.getItemID() + ";"
                );
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (result == 0) {
            itemToBeDeleted.setItemID(-1);
        }
        return itemToBeDeleted;

    }

}
