package com.sosnowski.Voting.application.DTOs;

import lombok.Data;

import java.util.List;
@Data
public class UserGroupAddDTO {
    String ownerUsername;
    String userGroupName;
    List<String> usernames;
}
