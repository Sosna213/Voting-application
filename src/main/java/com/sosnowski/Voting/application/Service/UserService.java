package com.sosnowski.Voting.application.Service;

import com.sosnowski.Voting.application.DTO.RegisterUserDTO;
import com.sosnowski.Voting.application.Entity.Role;
import com.sosnowski.Voting.application.Entity.User;
import com.sosnowski.Voting.application.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User addUser(User user) {
        String pass;
        pass = passwordEncoder.encode(user.getPassword());
        user.setPassword(pass);
        return userRepository.save(user);
    }

    public List<User> findUsers(){
        return userRepository.findAll();
    }
    public List<String> findUsernames(){
        List<User> users = userRepository.findAll();
        List<String> usernames = new ArrayList<>();
        users.forEach(user->{
            usernames.add(user.getUsername());
        });
        return usernames;
    }

    public User findUserByUsername (String username)
    {
        return userRepository.findByUsername(username);
    }

    public User registerUser(RegisterUserDTO registerUserDTO){
        if(userRepository.findByUsername(registerUserDTO.username) != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nazwa użytkownika jest wykorzystana");
        }
        final String encryptedPassword = passwordEncoder.encode(registerUserDTO.password);
        User user = new User();
        user.setUsername(registerUserDTO.username);
        user.setEmail(registerUserDTO.email);
        user.setPassword(encryptedPassword);
        user.setActive(true);
        Role role = new Role();
        role.setRoleId(2L);
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("Not found " + username);
        }
        return user;
    }
}
