package com.sosnowski.Voting.application.Controller;

import com.sosnowski.Voting.application.DTOs.UserGroupAddDTO;
import com.sosnowski.Voting.application.DTOs.UserGroupGetDTO;
import com.sosnowski.Voting.application.Entity.User;
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
    public ResponseEntity<UserGroup> AddUserGroup(@RequestBody UserGroupAddDTO userGroupAddDTO) {
        UserGroup returnedUserUserGroup = userGroupService.addUserGroup(userGroupAddDTO);
        return ResponseEntity.ok().body(returnedUserUserGroup);
    }

    @GetMapping("/user-group/{username}")
    public ResponseEntity<List<UserGroupGetDTO>> getUserGroupsForUser(@PathVariable String username){
        return ResponseEntity.ok().body(userGroupService.getUserGroupsForUser(username));
    }
}
