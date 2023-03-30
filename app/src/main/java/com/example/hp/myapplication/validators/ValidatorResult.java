package com.example.hp.myapplication.validators;

public class ValidatorResult {
    public boolean isValid;
    public String reason;

    public ValidatorResult(boolean isValid, String reason) {
        this.isValid = isValid;
        this.reason = reason;
    }
}
