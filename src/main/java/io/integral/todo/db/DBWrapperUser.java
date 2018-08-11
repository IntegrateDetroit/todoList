package io.integral.todo.db;


import io.integral.todo.User;

public interface DBWrapperUser {

    User deleteUser(String email);

    User findUserById(int id);

    User createUser(User user);

    User findUserByEmail(String email);
}
