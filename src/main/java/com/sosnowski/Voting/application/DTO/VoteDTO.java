package com.sosnowski.Voting.application.DTO;

import lombok.Data;

@Data
public class VoteDTO {
    Long votingId;
    Long answerId;
    String username;
}
