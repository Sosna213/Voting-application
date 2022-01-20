package com.sosnowski.Voting.application.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

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
    @Column(name = "restricted",nullable = false)
    private Boolean restricted;
    @Column(name = "active",nullable = false)
    private Boolean active;
    @Column(name = "explicit",nullable = false)
    private Boolean explicit;
    @Column(name = "endDate")
    private Date endDate;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Collection<User> sharedToUsers;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
