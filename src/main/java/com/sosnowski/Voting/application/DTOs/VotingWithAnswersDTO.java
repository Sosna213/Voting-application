package com.sosnowski.Voting.application.DTOs;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class VotingWithAnswersDTO implements Serializable {
    Long votingId;
    Boolean active;
    String votingName;
    String question;
    Boolean restricted;
    Boolean explicit;
    String endDate;
    List<AnswerDTO> answers;
}
