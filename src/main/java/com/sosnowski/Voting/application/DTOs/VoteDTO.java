package com.sosnowski.Voting.application.DTOs;

import lombok.Data;

@Data
public class VoteDTO {
    Long votingId;
    Long answerId;
    String username;
}
