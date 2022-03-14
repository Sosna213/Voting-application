package com.sosnowski.Voting.application.Controller;

import com.sosnowski.Voting.application.DTO.UserGroupAddDTO;
import com.sosnowski.Voting.application.DTO.UserGroupEditDTO;
import com.sosnowski.Voting.application.DTO.UserGroupGetDTO;
import com.sosnowski.Voting.application.Entity.UserGroup;
import com.sosnowski.Voting.application.Service.UserGroupService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserGroupController {

    public final UserGroupService userGroupService;

    @ApiOperation(value ="Add users group to database", response = UserGroup.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added user group to database"),
    })
    @PostMapping("/users-group/add")
    public ResponseEntity<UserGroup> addUserGroup(@RequestBody UserGroupAddDTO userGroupAddDTO) {
        UserGroup returnedUserUserGroup = userGroupService.addUserGroup(userGroupAddDTO);
        return ResponseEntity.ok().body(returnedUserUserGroup);
    }

    @ApiOperation(value ="Edit user group in database", response = UserGroup.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully edited user group to database"),
    })
    @PutMapping("/users-group/edit")
    public ResponseEntity<UserGroup> EditUserGroup(@RequestBody UserGroupEditDTO userGroupEditDTO) {
        UserGroup returnedUserUserGroup = userGroupService.editUserGroup(userGroupEditDTO);
        return ResponseEntity.ok().body(returnedUserUserGroup);
    }

    @ApiOperation(value ="Get user groups for user by his username", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved user group from database"),
    })
    @GetMapping("/user-group/{username}")
    public ResponseEntity<List<UserGroupGetDTO>> getUserGroupsForUser(@PathVariable String username){
        return ResponseEntity.ok().body(userGroupService.getUserGroupsForUser(username));
    }

    @ApiOperation(value ="Delete user group from database by its id", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted user group from database"),
    })
    @DeleteMapping("/user-group/delete/{userGroupId}")
    public ResponseEntity<Long> deleteUserGroupById(@PathVariable Long userGroupId){
        Long deletedGroupId = userGroupService.deleteUserGroup(userGroupId);
        return ResponseEntity.ok().body(deletedGroupId);
    }
}
