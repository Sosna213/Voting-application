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
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findUsers() {
        return ResponseEntity.ok().body(userService.findUsers());
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<User> findUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.findUserByUsername(username));
    }

    @PostMapping("/register")
    public String signUp(String username, String password) {
        userService.signUpUser(username, password);
        return "redirect:/login";
    }

}
