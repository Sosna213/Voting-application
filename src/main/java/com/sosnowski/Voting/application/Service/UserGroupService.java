package com.sosnowski.Voting.application.Service;

import com.sosnowski.Voting.application.Entity.UserGroup;
import com.sosnowski.Voting.application.Repository.UserGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserGroupService {

    public final UserGroupRepository userGroupRepository;

    public UserGroup addUserGroup(UserGroup userGroup){
        return userGroupRepository.save(userGroup);
    }
}
