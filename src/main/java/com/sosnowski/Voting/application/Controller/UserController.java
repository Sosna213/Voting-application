package com.sosnowski.Voting.application.Controller;

import com.sosnowski.Voting.application.DTO.RegisterUserDTO;
import com.sosnowski.Voting.application.Entity.User;
import com.sosnowski.Voting.application.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value ="Add user to database operation", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added user to database")
    })
    @PostMapping("/users/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User returnedUser = userService.addUser(user);
        return ResponseEntity.ok().body(returnedUser);
    }

    @ApiOperation(value ="Get all users from database", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved users from the database"),
    })
    @GetMapping("/users")
    public ResponseEntity<List<User>> findUsers() {
        List<User> users = userService.findUsers();
        return ResponseEntity.ok().body(users);
    }

    @ApiOperation(value ="Get all usernames from database", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved usernames from the database"),
    })
    @GetMapping("/usernames")
    public ResponseEntity<List<String>> findUsernames() {
        List<String> usernames = userService.findUsernames();
        return ResponseEntity.ok().body(usernames);
    }

    @ApiOperation(value ="Get user by username from database", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved user from the database"),
    })
    @GetMapping("/users/{username}")
    public ResponseEntity<User> findUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.findUserByUsername(username));
    }

    @ApiOperation(value ="Register user in database", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully registered user to database"),
    })
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
       User registeredUser = userService.registerUser(registerUserDTO);
       return ResponseEntity.ok().body(registeredUser);
    }
    @ApiOperation(value ="Get userId by his username from database", response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved user id"),
    })
    @GetMapping("/userId/{username}")
    public ResponseEntity<Long> getUserIdByUsername(@PathVariable String username){
        return ResponseEntity.ok().body(userService.findUserByUsername(username).getUserId());
    }
}
