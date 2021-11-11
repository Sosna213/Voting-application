package com.sosnowski.Voting.application.Service;

import com.sosnowski.Voting.application.Entity.Role;
import com.sosnowski.Voting.application.Entity.User;
import com.sosnowski.Voting.application.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public User addUser(User user) {
        String pass = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(pass);
        return userRepository.save(user);
    }

    public List<User> findUsers(){
        return userRepository.findAll();
    }

    public User findUserByUsername (String username)
    {
        return userRepository.findByUsername(username);
    }

    public void signUpUser(String username, String password){
        final String encryptedPassword = bCryptPasswordEncoder.encode(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(encryptedPassword);
        user.setActive(true);
        Role role = new Role();
        role.setRoleId(Long.valueOf(1));
        user.setRole(role);
        final User createdUser = userRepository.save(user);
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
