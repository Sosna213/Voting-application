package com.sosnowski.Voting.application.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Ansvers")
@Table(name="Ansvers",schema = "public")
public class Answer {
    @Id
    @Column(name = "answer_id",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;
    @Column(name = "answer",nullable = false)
    private String answer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voting_id", referencedColumnName = "voting_id")
    private Voting voting;
}
