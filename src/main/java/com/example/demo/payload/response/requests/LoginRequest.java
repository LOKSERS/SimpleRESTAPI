package com.example.demo.payload.response.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequest {

    @NotEmpty(message = "Username can not be empty")
    private String username;
    @NotEmpty(message = "Password can not be empty")
    private String password;

}
