package com.example.demo.annotations;

import com.example.demo.validations.PasswordMathesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMathesValidator.class)
@Documented
public @interface PasswordMatches {

    String message() default "Password do not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
