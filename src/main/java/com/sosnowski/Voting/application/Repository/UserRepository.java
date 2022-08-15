package com.sosnowski.Voting.application.Repository;

import com.sosnowski.Voting.application.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByUserKeycloakId(String kcId);
}
