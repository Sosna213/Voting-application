package com.sosnowski.Voting.application.Controller;

import com.sosnowski.Voting.application.Configuration.Keycloak.KeycloakProvider;
import com.sosnowski.Voting.application.DTO.LoginDTO;
import com.sosnowski.Voting.application.DTO.RegisterUserDTO;
import com.sosnowski.Voting.application.Entity.User;
import com.sosnowski.Voting.application.Service.KeycloakService;
import com.sosnowski.Voting.application.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final KeycloakService keycloakService;
    private final UserService userService;

    @ApiOperation(value ="Get all usernames from database", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved usernames from the database"),
    })
    @GetMapping("/usernames")
    public ResponseEntity<List<String>> findUsernames() {
        List<String> usernames = keycloakService.findUsernames();
        return ResponseEntity.ok().body(usernames);
    }

    @ApiOperation(value ="Get user by username from database", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved user from the database"),
    })
    @GetMapping("/users/{username}")
    public ResponseEntity<UserRepresentation> findUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(keycloakService.findUserByUsername(username));
    }

    @ApiOperation(value ="Register user in database", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully registered user to database"),
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
        Response createdResponse = keycloakService.registerKeycloakUser(registerUserDTO);
        return ResponseEntity.status(createdResponse.getStatus()).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@NotNull @RequestBody LoginDTO loginRequest) {
        return keycloakService.loginUser(loginRequest);
    }
    @ApiOperation(value ="Get userId by his username from database", response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved user id"),
    })
    @GetMapping("/userId/{kcId}")
    public ResponseEntity<Long> getUserIdByKcId(@PathVariable String kcId){
        return ResponseEntity.ok().body(userService.findUserIdByUsername(kcId));
    }
}
