package com.sosnowski.Voting.application.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity(name = "UserGroup")
@Table(name="UserGroup",schema = "public")
public class UserGroup {

    @Id
    @Column(name = "user_group_id",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userGroupId;

    @Column(name = "user-group-name",nullable = false)
    private String userGroupName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Collection<User> users;
}
