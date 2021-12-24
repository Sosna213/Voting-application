package com.sosnowski.Voting.application.DTOs;

import lombok.Data;

@Data
public class UserGroupEditDTO {
    UserGroupAddDTO userGroupAddDTO;
    Long userGroupId;
}
