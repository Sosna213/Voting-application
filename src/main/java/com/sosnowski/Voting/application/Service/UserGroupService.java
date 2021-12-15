package com.sosnowski.Voting.application.Service;

import com.sosnowski.Voting.application.DTOs.UserGroupAddDTO;
import com.sosnowski.Voting.application.DTOs.UserGroupGetDTO;
import com.sosnowski.Voting.application.Entity.User;
import com.sosnowski.Voting.application.Entity.UserGroup;
import com.sosnowski.Voting.application.Entity.Voting;
import com.sosnowski.Voting.application.Repository.UserGroupRepository;
import com.sosnowski.Voting.application.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserGroupService {

    public final UserGroupRepository userGroupRepository;
    public final UserRepository userRepository;

    public UserGroup addUserGroup(UserGroupAddDTO userGroupAddDTO) {
        HashSet<User> users = new HashSet<>();
        userGroupAddDTO.getUsernames().forEach(username -> {
            users.add(userRepository.findByUsername(username));
        });
        UserGroup userGroup = new UserGroup();
        userGroup.setOwner(userRepository.findByUsername(userGroupAddDTO.getOwnerUsername()));
        userGroup.setUserGroupName(userGroupAddDTO.getUserGroupName());
        userGroup.setUsers(users);
        return userGroupRepository.save(userGroup);
    }

    public List<UserGroupGetDTO> getUserGroupsForUser(String username) {
        List<UserGroupGetDTO> userGroupGetDTOS = new ArrayList<>();
        List<UserGroup> userGroups = userGroupRepository.findUserGroupsByOwner(userRepository.findByUsername(username));
        userGroups.forEach(userGroup -> {
            UserGroupGetDTO userGroupGetDTO = new UserGroupGetDTO();
            userGroupGetDTO.setUserGroupId(userGroup.getUserGroupId());
            userGroupGetDTO.setUserGroupName(userGroup.getUserGroupName());
            List<String> usernames = new ArrayList<>();
            userGroup.getUsers().forEach(user->{
                usernames.add(user.getUsername());
            });
            userGroupGetDTO.setUsernames(usernames);
            userGroupGetDTOS.add(userGroupGetDTO);
        });
        return userGroupGetDTOS;
    }
}
