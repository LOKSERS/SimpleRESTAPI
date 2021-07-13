package com.example.demo.validations;

import com.example.demo.annotations.PasswordMatches;
import com.example.demo.payload.response.requests.SignUpRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMathesValidator implements ConstraintValidator<PasswordMatches, Object> {
    public void initialize(PasswordMatches constraint) {
    }

    public boolean isValid(Object obj, ConstraintValidatorContext context) {

        SignUpRequest userSignupRequest = (SignUpRequest) obj;
        return userSignupRequest.getPassword().equals(userSignupRequest.getConfirmPassword());
    }
}
