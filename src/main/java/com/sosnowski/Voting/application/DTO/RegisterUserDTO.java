package com.sosnowski.Voting.application.DTO;

import lombok.Data;

@Data
public class RegisterUserDTO {
    public String username;
    public String email;
    public String password;
}
