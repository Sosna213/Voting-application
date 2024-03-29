package com.sosnowski.Voting.application.Entity;

import com.sosnowski.Voting.application.Enum.UserRole;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity(name = "users")
@Table(name="users",schema = "public",uniqueConstraints = {
        @UniqueConstraint(name = "user_username_unique",columnNames = "username")})
public class User implements UserDetails {

    @Id
    @Column(name = "user_id",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(name = "username",nullable = false)
    private String username;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "email",nullable = false)
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Collection<Role> roles = new ArrayList<>();
    @Column(name = "is_active", nullable = false, columnDefinition = "boolean default true")
    private boolean isActive;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        this.getRoles().forEach(role ->
        {
            if(role.getRoleId() == 1){
                authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.name()));
            } else if(role.getRoleId() == 2){
                authorities.add(new SimpleGrantedAuthority(UserRole.USER.name()));
            }
        });
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
