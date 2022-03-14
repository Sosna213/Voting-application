package com.sosnowski.Voting.application.DTO;

import lombok.Data;

@Data
public class SharedVotingDTO {
    VotingDTO votingDTO;
    Boolean voted;
}
