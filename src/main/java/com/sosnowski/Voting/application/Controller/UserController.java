package com.sosnowski.Voting.application.Controller;

import com.sosnowski.Voting.application.Entity.User;
import com.sosnowski.Voting.application.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User returnedUser = userService.addUser(user);
        return ResponseEntity.ok().body(returnedUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findUsers() {
        List<User> users = userService.findUsers();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<User> findUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.findUserByUsername(username));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.registerUser(user.getUsername(), user.getPassword(), user.getEmail());
        return ResponseEntity.ok().body("User has been registered!");
    }

}
