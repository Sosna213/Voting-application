package com.sosnowski.Voting.application.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class ShareVotingToUsersDTO {
    List<String> usernames;
    Long votingId;
}
