package io.integral.todo.rest;

import io.integral.todo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service ) {
        this.service = service;
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<User> createUser(@RequestBody User user) {
        user = this.service.createUser(user );
        if (user.getUserID() == -1 ) {
            return new ResponseEntity<>(user,
                HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(user,
                HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public @ResponseBody
    ResponseEntity<User> readUser(@PathVariable Integer id ) {
        User user = this.service.getUser( id );
        if(user.getUserID() == -1 ) {
            return new ResponseEntity<>(user, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>( user, HttpStatus.OK );
    }

    @GetMapping("/email/{email}")
    public @ResponseBody
    ResponseEntity<User> readUserByEmail(@PathVariable String email ) {
        User user = this.service.getUserByEmail( email );
        if (user.getUserID() == -1 ) {
            return new ResponseEntity<>(user, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>( user, HttpStatus.OK );
    }

    @DeleteMapping("/email/{email}")
    public @ResponseBody
    ResponseEntity<User> deleteUserByEmail(@PathVariable String email ) {
        User user = this.service.deleteUserByEmail(email);
        if (user.getUserID() == -1) {
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user, HttpStatus.GONE);


    }
}

