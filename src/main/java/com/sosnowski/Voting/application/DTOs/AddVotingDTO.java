package com.sosnowski.Voting.application.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class AddVotingDTO {
    Long userId;
    String votingName;
    String question;
    List<AnswerDTO> answers;
}
