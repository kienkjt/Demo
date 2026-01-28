package com.kjt.demo.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ISBNValidator implements ConstraintValidator<ISBN, String> {

    // ISBN-13 format: 978-0132350884 or 9780132350884
    private static final Pattern ISBN_PATTERN = Pattern.compile("^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$");

    @Override
    public void initialize(ISBN constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // Use @NotNull or @NotBlank for null checking
        }

        // Remove hyphens and spaces for validation
        String cleanIsbn = value.replaceAll("[- ]", "");

        // Check length (ISBN-10 or ISBN-13)
        if (cleanIsbn.length() != 10 && cleanIsbn.length() != 13) {
            return false;
        }

        // Validate format
        return ISBN_PATTERN.matcher(value).matches();
    }
}
