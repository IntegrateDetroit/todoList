package io.integral.todo.db;

import io.integral.todo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class SQLiteUser implements DBWrapperUser {

    @Autowired
    DataSource dataSource;


    @Override
    public User createUser(User user) {

        User userExists = findUserByEmail(user.geteMail());
        if (userExists.getUserID() == -1) {
            String firstName=user.getFirstName();
            String lastName=user.getLastName();
            String eMail=user.geteMail();
            String passwordHash=user.getPasswordHash();
            String signupDate=user.getSignupDate();
            try {
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                statement.execute(
                        "INSERT INTO User (" +
                                "FIRST_NAME," +
                                "LAST_NAME," +
                                "EMAIL," +
                                "PASSWORD_HASH," +
                                "SIGNUP_DATE) VALUES ('" + firstName + "','" +
                                lastName + "','" +
                                eMail + "','" +
                                passwordHash + "','" +
                                signupDate + "');"
                );
                statement.close();
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT last_insert_rowid()");
                int id = resultSet.getInt("last_insert_rowid()");
                user.setUserID(id);
                connection.close();
                return user;
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            user.setUserID(-1);
        }
        return user;
    }

    @Override
    public User deleteUser(String email)  {
        User userToDelete = findUserByEmail(email);
        int result = 0;
        if (userToDelete.getUserID() > -1) {
            try {
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();

                result = statement.executeUpdate(String.format(
                        "DELETE FROM %s WHERE %s=\"%s\";",
                        "User",
                        "EMAIL",
                        email
                ));
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (result == 0) {
            userToDelete.setUserID(-1);
        }
        return userToDelete;
    }

    @Override
    public User findUserById(int id) {
        User user = new User();

        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery( "SELECT * from User WHERE ID=" + id + ";" );

            if( resultSet.next() == false ) {
                user.setUserID( -1 );
            } else {
                user.setUserID( resultSet.getInt( "ID" ) );
                user.setFirstName( resultSet.getString( "FIRST_NAME" ) );
                user.setLastName( resultSet.getString( "LAST_NAME" ) );
                user.setEmail( resultSet.getString( "EMAIL" ) );
                user.setPasswordHash( resultSet.getString( "PASSWORD_HASH" ) );
                user.setSignupDate( resultSet.getString( "SIGNUP_DATE" ) );
            }
            connection.close();
            return user;
        } catch( SQLException e ) { e.printStackTrace(); }

        user.setUserID( -1 );

        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = new User();

        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery( "SELECT * from User WHERE EMAIL='" + email + "';" );

            if( resultSet.next() == false ) {
                user.setUserID( -1 );
            } else {
                user.setUserID( resultSet.getInt( "ID" ) );
                user.setFirstName( resultSet.getString( "FIRST_NAME" ) );
                user.setLastName( resultSet.getString( "LAST_NAME" ) );
                user.setEmail( resultSet.getString( "EMAIL" ) );
                user.setPasswordHash( resultSet.getString( "PASSWORD_HASH" ) );
                user.setSignupDate( resultSet.getString( "SIGNUP_DATE" ) );
            }
            connection.close();
            return user;
        } catch( SQLException e ) { e.printStackTrace(); }

        user.setUserID( -1 );

        return user;
    }
//
//    @Override
//    public int deleteUser(String email) {
//        User user = new User();
//        int DBresult = 0;
//        try {
//            Connection connection = dataSource.getConnection();
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery( "DELETE from User WHERE EMAIL='" + email + "';" );
//
//            if( resultSet.rowDeleted() ) {
//                DBresult=1;
//            } else {
//                DBresult=0;
//            }
//            connection.close();
//            return DBresult;
//        } catch( SQLException e ) { e.printStackTrace(); }
//
//        return DBresult;
//    }
}
