package com.sosnowski.Voting.application.Controller;


import com.mashape.unirest.http.exceptions.UnirestException;
import com.sosnowski.Voting.application.Service.KeycloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor @Slf4j
public class TokenController {

    private final KeycloakService kcService;

    @PostMapping(value = "/token-refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> refreshToken(@RequestBody String refreshToken) throws UnirestException {
        return ResponseEntity.ok(this.kcService.refreshToken(refreshToken).toString());
    }
}
