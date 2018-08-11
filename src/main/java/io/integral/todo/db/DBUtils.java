package io.integral.todo.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


@Component
public class DBUtils {

    @Autowired
    private DataSource dataSource;
    private Connection connection;
    private Statement statement;


    public void initialize(){
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        initializeListTable();
        initializeUserTable();
        initializeItemTable();

        try{
            statement.close();
            connection.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeUserTable() {
        try {
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS User(" +
                            "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "FIRST_NAME text NOT NULL, " +
                            "LAST_NAME text NOT NULL, " +
                            "EMAIL text NOT NULL UNIQUE , " +
                            "PASSWORD_HASH text NOT NULL , " +
                            "SIGNUP_DATE date DEFAULT current_timestamp)"
            );
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeListTable(){
        try {
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS List(" +
                            "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "USER_ID INTEGER not null, " +
                            "LIST_NAME varchar(30) not null, " +
                            "ARCHIVE_STATUS varchar(1), " +
                            "CONSTRAINT User_List " +
                            "FOREIGN KEY (USER_ID) " +
                            "REFERENCES User(ID) " +
                            "ON DELETE CASCADE)"
            );
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeItemTable(){
        try {
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Item(" +
                            "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "LIST_ID INTEGER not null, " +
                            "DESCRIPTION TEXT not null, " +
                            "DUE_DATE NUMERIC , " +
                            "STATUS INTEGER not null, " +
                            "CONSTRAINT List_Item " +
                            "FOREIGN KEY (LIST_ID) " +
                            "REFERENCES List(ID) " +
                            "ON DELETE CASCADE)"
            );
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
