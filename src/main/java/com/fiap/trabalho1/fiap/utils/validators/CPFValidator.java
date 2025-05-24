package com.fiap.trabalho1.fiap.utils.validators;

import com.fiap.trabalho1.fiap.utils.ValidCPF;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<ValidCPF, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d+")) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (ex.: 11111111111)
        if (cpf.chars().distinct().count() == 1) {
            return false;
        }

        // Calcula os dígitos verificadores
        return isValidCPF(cpf);
    }

    private boolean isValidCPF(String cpf) {
        int[] weights = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int firstCheckDigit = calculateCheckDigit(cpf, weights);
        int secondCheckDigit = calculateCheckDigit(cpf, new int[]{11, 10, 9, 8, 7, 6, 5, 4, 3, 2});

        return cpf.charAt(9) == (char) (firstCheckDigit + '0') && cpf.charAt(10) == (char) (secondCheckDigit + '0');
    }

    private int calculateCheckDigit(String cpf, int[] weights) {
        int sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * weights[i];
        }
        int remainder = sum % 11;
        return (remainder < 2) ? 0 : 11 - remainder;
    }
}
