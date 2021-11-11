package com.sosnowski.Voting.application.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "roles")
@Table(name = "roles", schema = "public")
public class Role {
    @Id
    @Column(name = "role_id",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    @Column(name = "role_name",nullable = false)
    private String roleName;
}
