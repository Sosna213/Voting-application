package com.sosnowski.Voting.application.Service;

import com.sosnowski.Voting.application.DTO.RegisterUserDTO;
import com.sosnowski.Voting.application.Entity.Role;
import com.sosnowski.Voting.application.Entity.User;
import com.sosnowski.Voting.application.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class VotingServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @Test
    void addVoting() {
        //given
        String username = "user123";
        String password = "password123";
        String email = "user@user.pl";
        RegisterUserDTO registerUserDTO = new RegisterUserDTO();
        registerUserDTO.setUsername(username);
        registerUserDTO.setEmail(email);
        registerUserDTO.setPassword(password);

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword("passwordEncoded");
        user.setActive(true);
        Role role = new Role();
        role.setRoleId(Long.valueOf(2));
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);

        when(passwordEncoder.encode("passwordEncoded")).thenReturn("passwordEncoded");
        when(userRepository.save(any(User.class))).thenReturn(user);

        //when
        User result = userService.registerUser(registerUserDTO);

        //then
        assertEquals(user, result);
    }

    @Test
    void shareVotingToUsers() {
        //given
        String username = "user123";
        String password = "password123";
        String email = "user@user.pl";
        RegisterUserDTO registerUserDTO = new RegisterUserDTO();
        registerUserDTO.setUsername(username);
        registerUserDTO.setEmail(email);
        registerUserDTO.setPassword(password);

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword("passwordEncoded");
        user.setActive(true);
        Role role = new Role();
        role.setRoleId(Long.valueOf(2));
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);

        when(passwordEncoder.encode("passwordEncoded")).thenReturn("passwordEncoded");
        when(userRepository.save(any(User.class))).thenReturn(user);

        //when
        User result = userService.registerUser(registerUserDTO);

        //then
        assertEquals(user, result);
    }
}