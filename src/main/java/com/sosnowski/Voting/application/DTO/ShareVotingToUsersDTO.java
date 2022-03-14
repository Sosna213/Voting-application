package com.sosnowski.Voting.application.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ShareVotingToUsersDTO {
    List<String> usernames;
    Long votingId;
}
