package com.sosnowski.Voting.application.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class EditVotingDTO {
    Long votingId;
    String votingName;
    String question;
    List<AnswerDTO> answers;
}
