package com.sosnowski.Voting.application.Repository;

import com.sosnowski.Voting.application.Entity.User;
import com.sosnowski.Voting.application.Entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    List<UserGroup> findUserGroupsByOwner(User user);
}
