package com.sosnowski.Voting.application.Controller;

import com.sosnowski.Voting.application.Entity.User;
import com.sosnowski.Voting.application.Entity.UserGroup;
import com.sosnowski.Voting.application.Service.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserGroupController {

    public final UserGroupService userGroupService;

    @PostMapping("/users-group/add")
    public ResponseEntity<UserGroup> AddUserGroup(@RequestBody UserGroup userGroup) {
        UserGroup returnedUserUserGroup = userGroupService.addUserGroup(userGroup);
        return ResponseEntity.ok().body(returnedUserUserGroup);
    }
}
