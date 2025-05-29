package com.fiap.trabalho1.fiap.utils.validators;

import com.fiap.trabalho1.fiap.utils.ValidCPF;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<ValidCPF, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.trim().isEmpty()) {
            // Deixe o @NotBlank tratar valores nulos ou em branco
            return true;
        }

        cpf = cpf.replaceAll("\\D", ""); // Remove caracteres não numéricos

        if (cpf.length() != 11 || cpf.chars().distinct().count() == 1) {
            return false;
        }

        return isValidCPF(cpf);
    }

    private boolean isValidCPF(String cpf) {
        int firstCheckDigit = calculateCheckDigit(cpf, 9);
        int secondCheckDigit = calculateCheckDigit(cpf, 10);

        return cpf.charAt(9) == (char) (firstCheckDigit + '0') &&
                cpf.charAt(10) == (char) (secondCheckDigit + '0');
    }

    private int calculateCheckDigit(String cpf, int length) {
        int sum = 0;
        int weight = length + 1;
        for (int i = 0; i < length; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * weight--;
        }
        int remainder = sum % 11;
        return (remainder < 2) ? 0 : 11 - remainder;
    }
}