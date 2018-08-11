package io.integral.todo.rest;

import io.integral.todo.User;
import io.integral.todo.db.DBWrapperUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private DBWrapperUser database;

    @Autowired
    public UserService(DBWrapperUser database ) {
        this.database = database;
    }

    public User getUser(Integer id) {
        return database.findUserById(id);
    }

    public User createUser(User user) {
        return database.createUser(user);
    }

    public User getUserByEmail(String email) {
        return database.findUserByEmail(email);
    }

    public User deleteUserByEmail(String email) { return database.deleteUser(email); }
}
