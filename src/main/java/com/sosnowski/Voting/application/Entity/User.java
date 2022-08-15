package com.sosnowski.Voting.application.Entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "users")
@Table(name="users",schema = "public",uniqueConstraints = {
        @UniqueConstraint(name = "user_username_unique",columnNames = "username")})
public class User {

    @Id
    @Column(name = "user_id",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(name = "user_keycloak_id",nullable = false)
    private String userKeycloakId;
    @Column(name = "username",nullable = false)
    private String username;
    @Column(name = "email",nullable = false)
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return userId != null && Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
