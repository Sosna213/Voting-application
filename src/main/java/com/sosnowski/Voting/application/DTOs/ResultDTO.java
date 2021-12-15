package com.sosnowski.Voting.application.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class ResultDTO {
    String answer;
    Integer numberOfAnswers;
    List<String> usernames;
}
