package com.integrate.todo.rest;

import com.integrate.todo.User;
import com.integrate.todo.db.SQLiteUser;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UserServiceTest {


    private SQLiteUser db;

    private UserService service;

    @Before
    public void setup() {

        this.db = mock(SQLiteUser.class);

        this.service = new UserService(this.db);

    }

    @Test
    public void createUser_savesToRepository() {
        int expectedID = 1;
        String expectedFirstName = "Zaara";
        String expectedLastName = "Userson";
        String expectedEmail = "zaara0001@example.com";
        String expectedPasswordHash = "password";
        String expectedSignupDate = "6/9/2018";
        int expectedPreference = 0;

        User inputUser = new User().setUser(expectedFirstName,expectedLastName,expectedEmail,expectedPasswordHash,expectedSignupDate,expectedPreference);

        User expectedUser = new User()
                .setUser(expectedFirstName,expectedLastName,expectedEmail,expectedPasswordHash,expectedSignupDate,expectedPreference);

        when(this.db.createUser(inputUser))
                .thenReturn(expectedUser);

        User user = this.service.createUser(
                new User().setUser(expectedFirstName,expectedLastName,expectedEmail,expectedPasswordHash,expectedSignupDate,expectedPreference));


        verify(db)
                .createUser(inputUser);

        assertThat(user.getUserID())
                .isEqualTo(expectedUser.getUserID());
    }

    @Test
    public void getUser_returnsUserWithCorrectID() {
        int expectedUserID = 1;
        String expectedFirstName = "Zaara";
        String expectedLastName = "Userson";
        String expectedEmail = "zaara0001@example.com";
        String expectedPasswordHash = "password";
        String expectedSignupDate = "6/9/2018";
        int expectedPreference = 0;
        User expectedUser = new User().setUser(expectedFirstName,expectedLastName,expectedEmail,expectedPasswordHash, expectedSignupDate,expectedPreference);

        when(this.db.findUserById(expectedUserID))
                .thenReturn(expectedUser);

        User user = this.service.getUser(expectedUserID);

        verify(this.db)
                .findUserById(expectedUserID);

        assertThat(expectedUser)
                .isEqualTo(user);
    }

    @Test
    public void getUserByEmail_returnsUserWithCorrectID() {
        int expectedUserID = 1;
        String expectedFirstName = "Zaara";
        String expectedLastName = "Userson";
        String expectedEmail = "zaara0001@example.com";
        String expectedPasswordHash = "password";
        String expectedSignupDate = "6/9/2018";
        int expectedPreference = 0;
        User expectedUser = new User().setUser(expectedFirstName,expectedLastName,expectedEmail,expectedPasswordHash, expectedSignupDate,expectedPreference);

        when(this.db.findUserByEmail(expectedEmail))
                .thenReturn(expectedUser);

        User user = this.service.getUserByEmail(expectedEmail);

        verify(this.db)
                .findUserByEmail(expectedEmail);

        assertThat(user).isEqualTo(expectedUser);
    }
}
