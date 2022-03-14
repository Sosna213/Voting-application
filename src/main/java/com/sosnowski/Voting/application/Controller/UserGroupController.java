package com.sosnowski.Voting.application.Controller;

import com.sosnowski.Voting.application.DTO.UserGroupAddDTO;
import com.sosnowski.Voting.application.DTO.UserGroupEditDTO;
import com.sosnowski.Voting.application.DTO.UserGroupGetDTO;
import com.sosnowski.Voting.application.Entity.UserGroup;
import com.sosnowski.Voting.application.Service.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserGroupController {

    public final UserGroupService userGroupService;

    @PostMapping("/users-group/add")
    public ResponseEntity<UserGroup> addUserGroup(@RequestBody UserGroupAddDTO userGroupAddDTO) {
        UserGroup returnedUserUserGroup = userGroupService.addUserGroup(userGroupAddDTO);
        return ResponseEntity.ok().body(returnedUserUserGroup);
    }

    @PutMapping("/users-group/edit")
    public ResponseEntity<UserGroup> EditUserGroup(@RequestBody UserGroupEditDTO userGroupEditDTO) {
        UserGroup returnedUserUserGroup = userGroupService.editUserGroup(userGroupEditDTO);
        return ResponseEntity.ok().body(returnedUserUserGroup);
    }

    @GetMapping("/user-group/{username}")
    public ResponseEntity<List<UserGroupGetDTO>> getUserGroupsForUser(@PathVariable String username){
        return ResponseEntity.ok().body(userGroupService.getUserGroupsForUser(username));
    }
    @DeleteMapping("/user-group/delete/{userGroupId}")
    public ResponseEntity<Long> deleteUserGroupById(@PathVariable Long userGroupId){
        Long deletedGroupId = userGroupService.deleteUserGroup(userGroupId);
        return ResponseEntity.ok().body(deletedGroupId);
    }
}
