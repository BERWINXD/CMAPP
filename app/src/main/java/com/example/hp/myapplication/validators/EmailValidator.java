package com.example.hp.myapplication.validators;

public class EmailValidator {
    private static final String EMAIL_REGEX_PATTERN = "^(.+)@(\\S+)$";

    public static ValidatorResult isEmailValid(String email) {
        if (email.length() == 0)
            return new ValidatorResult(false, "Email cannot be Empty");
        boolean match = email.matches(EMAIL_REGEX_PATTERN);
        if (match)
            return new ValidatorResult(true, "Valid Email");
        return new ValidatorResult(false, "Invalid Email");
    }
}
