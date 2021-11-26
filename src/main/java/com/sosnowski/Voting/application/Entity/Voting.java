package com.sosnowski.Voting.application.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Votings")
@Table(name="Votings",schema = "public")
public class Voting {

    @Id
    @Column(name = "voting_id",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long votingId;
    @Column(name = "voting_name",nullable = false)
    private String votingName;
    @Column(name = "question",nullable = false)
    private String question;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
