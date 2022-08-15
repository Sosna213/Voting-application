package com.sosnowski.Voting.application.Service;

import com.sosnowski.Voting.application.DTO.RegisterUserDTO;
import com.sosnowski.Voting.application.Entity.Role;
import com.sosnowski.Voting.application.Entity.User;
import com.sosnowski.Voting.application.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(UserRepresentation user){
        User createdUser = new User();
        createdUser.setUserKeycloakId(user.getId());
        createdUser.setUsername(user.getUsername());
        createdUser.setEmail(user.getEmail());
        createdUser.setUserKeycloakId(user.getId());
        userRepository.save(createdUser);
    }
    public Long findUserIdByUsername (String kcId)
    {
        return userRepository.findByUserKeycloakId(kcId).getUserId();
    }
}
