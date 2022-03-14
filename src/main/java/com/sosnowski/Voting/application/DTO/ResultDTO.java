package com.sosnowski.Voting.application.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ResultDTO {
    Long answerId;
    String name;
    Integer value;
    List<String> usernames;
}
