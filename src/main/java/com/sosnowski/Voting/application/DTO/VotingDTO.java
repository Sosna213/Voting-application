package com.sosnowski.Voting.application.DTO;

import lombok.Data;

@Data
public class VotingDTO {
    Long votingId;
    String votingName;
    Boolean restricted;
    Boolean active;
    Boolean explicit;
    String endDate;
    String question;
}
