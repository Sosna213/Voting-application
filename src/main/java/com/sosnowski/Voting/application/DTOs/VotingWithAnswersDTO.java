package com.sosnowski.Voting.application.DTOs;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class VotingWithAnswersDTO implements Serializable {
    Long votingId;
    String votingName;
    String question;
    Boolean restricted;
    String endDate;
    List<AnswerDTO> answers;
}
