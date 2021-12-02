package com.sosnowski.Voting.application.DTOs;

import lombok.Data;

@Data
public class VotingDTO {
    Long votingId;
    String votingName;
    String question;
}
