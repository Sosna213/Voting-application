package com.sosnowski.Voting.application.DTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class VotingWithAnswersDTO implements Serializable {
    Long votingId;
    String votingName;
    Boolean restricted;
    Boolean active;
    Boolean explicit;
    String endDate;
    String question;
    List<AnswerDTO> answers;
}
