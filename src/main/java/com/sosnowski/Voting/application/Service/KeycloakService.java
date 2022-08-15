package com.sosnowski.Voting.application.Service;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sosnowski.Voting.application.Configuration.Keycloak.KeycloakProvider;
import com.sosnowski.Voting.application.Controller.UserController;
import com.sosnowski.Voting.application.DTO.LoginDTO;
import com.sosnowski.Voting.application.DTO.RegisterUserDTO;
import com.sosnowski.Voting.application.Entity.User;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.RoleRepresentation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    @Value("${keycloak.realm}")
    public String realm;
    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(KeycloakService.class);

    private final KeycloakProvider kcProvider;
    private final UserService userService;

    @Transactional
    public Response registerKeycloakUser(RegisterUserDTO user){
        Response res = this.createKeycloakUser(user);
        UserRepresentation userRepresentation =  findUserByUsername(user.getUsername());
        userService.createUser(userRepresentation);
        List<RoleRepresentation> roleList = rolesToRealmRoleRepresentation(List.of("user"));
        kcProvider.getInstance().realm(realm)
                .users()
                .get(userRepresentation.getId())
                .roles()
                .realmLevel()
                .add(roleList);
        return res;
    }

    private List<RoleRepresentation> rolesToRealmRoleRepresentation(List<String> roles) {
        List<RoleRepresentation> existingRoles = kcProvider.getInstance().realm(realm)
                .roles()
                .list();

        List<String> serverRoles = existingRoles
                .stream()
                .map(RoleRepresentation::getName)
                .collect(Collectors.toList());
        List<RoleRepresentation> resultRoles = new ArrayList<>();

        for (String role : roles) {
            int index = serverRoles.indexOf(role);
            if (index != -1) {
                resultRoles.add(existingRoles.get(index));
            } else {
                LOG.info("Role doesn't exist");
            }
        }
        return resultRoles;
    }
    private Response createKeycloakUser(RegisterUserDTO user) {
        UsersResource usersResource = kcProvider.getInstance().realm(realm).users();
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());
        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(user.getEmail());
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setUsername(user.getUsername());
        kcUser.setEmail(user.getEmail());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(true);

        return usersResource.create(kcUser);
    }

    public UserRepresentation findUserByUsername(String username) {
        UsersResource usersResource = kcProvider.getInstance().realm(realm).users();
        UserRepresentation userRepresentation = new UserRepresentation();
        for (UserRepresentation user : usersResource.list()) {
            userRepresentation = Objects.equals(user.getUsername(), username) ? user : userRepresentation;
        }
        return userRepresentation;
    }

    public List<String> findUsernames() {
        UsersResource usersResource = kcProvider.getInstance().realm(realm).users();
        List<String> usernames = new ArrayList<>();
        for (UserRepresentation user : usersResource.list()) {
            usernames.add(user.getUsername());
        }
        return usernames;
    }

    public JsonNode refreshToken(String token) throws UnirestException {
        return kcProvider.refreshToken(token);
    }

    public ResponseEntity<AccessTokenResponse> loginUser(LoginDTO loginRequest) {
        Keycloak keycloak = kcProvider.newKeycloakBuilderWithPasswordCredentials(loginRequest.getUsername(), loginRequest.getPassword()).build();

        AccessTokenResponse accessTokenResponse = null;
        try {
            accessTokenResponse = keycloak.tokenManager().getAccessToken();
            return ResponseEntity.status(HttpStatus.OK).body(accessTokenResponse);
        } catch (BadRequestException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(accessTokenResponse);
        }
    }

    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }
}
