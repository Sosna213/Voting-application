package com.sosnowski.Voting.application.DTOs;

import com.sosnowski.Voting.application.Entity.Voting;
import lombok.Data;

@Data
public class SharedVotingDTO {
    VotingDTO votingDTO;
    Boolean voted;
}
