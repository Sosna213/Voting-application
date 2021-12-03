package com.sosnowski.Voting.application.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "VotingResults")
@Table(name="VotingResults",schema = "public")
public class VotingResult {

    @Id
    @Column(name = "voting_result_id",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long votingResultId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id", referencedColumnName = "answer_id")
    private Answer answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voting_id", referencedColumnName = "voting_id")
    private Voting voting;
}
