package com.sosnowski.Voting.application.DTOs;

import com.sosnowski.Voting.application.Entity.Answer;
import com.sosnowski.Voting.application.Entity.Voting;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Data
public class VotingWithAnswersDTO implements Serializable {
    Long votingId;
    String votingName;
    String question;
    Boolean restricted;
    Date endDate;
    List<String> answers;
}
