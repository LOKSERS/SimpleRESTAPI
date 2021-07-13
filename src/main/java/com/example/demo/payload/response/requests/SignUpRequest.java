package com.example.demo.payload.response.requests;

import com.example.demo.annotations.ValidEmail;
import com.example.demo.annotations.PasswordMatches;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class SignUpRequest {
    @Email(message = "It should have email format")
    @NotBlank(message = "User email is required")
    @ValidEmail
    private String email;
    @NotEmpty(message = "Enter your name")
    private  String firstname;
    @NotEmpty(message = "Enter your lastname")
    private String lastname;
    @NotEmpty(message = "Enter your username")
    private String username;
    @NotEmpty(message = "Password is required")
    @Size(min=6)
    private String password;
    private String confirmPassword;
}
