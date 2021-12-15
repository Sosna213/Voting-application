package com.sosnowski.Voting.application.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class UserGroupGetDTO {
    Long userGroupId;
    String userGroupName;
    List<String> usernames;
}
