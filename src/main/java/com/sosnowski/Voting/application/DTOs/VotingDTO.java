package com.sosnowski.Voting.application.DTOs;

import lombok.Data;

import java.util.Date;

@Data
public class VotingDTO {
    Long votingId;
    String votingName;
    Boolean restricted;
    Date endDate;
    String question;
}
