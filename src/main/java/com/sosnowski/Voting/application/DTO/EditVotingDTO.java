package com.sosnowski.Voting.application.DTO;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EditVotingDTO {
    Long votingId;
    String votingName;
    String question;
    Boolean restricted;
    Boolean explicit;
    Date endDate;
    List<AnswerDTO> answers;
}
