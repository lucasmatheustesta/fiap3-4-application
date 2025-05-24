package com.fiap.trabalho1.fiap.utils;

import com.fiap.trabalho1.fiap.utils.validators.CPFValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CPFValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCPF {
    String message() default "O CPF é inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
