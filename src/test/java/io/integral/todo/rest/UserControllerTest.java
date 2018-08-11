package io.integral.todo.rest;

import io.integral.todo.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    UserService mockService;
    UserController userController;

    @Before
    public void setUp() {
        mockService = mock( UserService.class );
        userController = new UserController(mockService);

    }

    @Test
    public void createUser_returnsHttpStatusCreated() {

        User expectedUser = new User();
        User userPassedIn = new User();

        expectedUser.setUserID(1);

        when (mockService.createUser(userPassedIn))
                .thenReturn(expectedUser);
        ResponseEntity expectedResponseEntity = new ResponseEntity<>(
                expectedUser, HttpStatus.CREATED);
        ResponseEntity<User> responseEntity = userController.createUser( userPassedIn );
        verify(mockService)
                .createUser( userPassedIn );
        assertThat( responseEntity )
                .isEqualTo( expectedResponseEntity );
    }

    @Test
    public void getUser_returnsUserAndHttpStatus200() {

        User expectedUser = new User();
        expectedUser.setUserID(1);

        when( mockService.getUser( 1 ) )
                .thenReturn( expectedUser );

        ResponseEntity<User> resultUser = userController.readUser(1);
        ResponseEntity expectedResponse = new ResponseEntity<>( // compare what I an getting back to what I expect
                expectedUser,
                HttpStatus.OK
        );

        verify( mockService )
                .getUser( 1 );

        assertThat( resultUser )
                .isEqualTo( expectedResponse );
    }

    @Test
    public void getUserByEmail_returnsUserAndHttpStatus200() {

        User expectedUser = new User();
        expectedUser.setUserID(2);
        expectedUser.setEmail("zaara0001@example.com");

        when( mockService.getUserByEmail("zaara0001@example.com") )
                .thenReturn( expectedUser );

        ResponseEntity<User> resultUser = userController.readUserByEmail("zaara0001@example.com");
        ResponseEntity expectedResponse = new ResponseEntity<>( // compare what I an getting back to what I expect
                expectedUser,
                HttpStatus.OK
        );

        verify( mockService )
                .getUserByEmail("zaara0001@example.com" );

        assertThat( resultUser )
                .isEqualTo( expectedResponse );
    }

    @Test
    public void getUser_whenDoesntExist_returnsHttpStatus204(){

        User expectedUser = new User();
        expectedUser.setUserID( -1 );
        int input_id = 8;

        when( mockService.getUser( input_id ) )
                .thenReturn( expectedUser );

        ResponseEntity expectedResponse = new ResponseEntity<>(
                expectedUser,
                HttpStatus.NO_CONTENT);


        ResponseEntity<User> resultUser = userController.readUser( input_id );


        verify( mockService )
                .getUser( input_id );

        assertThat( resultUser )
                .isEqualTo( expectedResponse );
    }

    @Test
    public void deleteUserMethod_deletesUserFromDatabase() {

        User expectedUser = new User();
        User deletedUserPassedIn = new User();

        String expextedEmail = expectedUser.geteMail();
        String deletedEmail = deletedUserPassedIn.geteMail();
        expectedUser.setUserID(1);

        when(mockService.deleteUserByEmail(expextedEmail)).thenReturn(expectedUser);

        ResponseEntity expectedResponseEntity = new ResponseEntity<>(
                expectedUser, HttpStatus.GONE);


        ResponseEntity<User> responseEntity = userController.deleteUserByEmail(deletedEmail);

        verify(mockService).deleteUserByEmail(deletedEmail);

        assertThat(responseEntity).isEqualTo( expectedResponseEntity );

    }
}