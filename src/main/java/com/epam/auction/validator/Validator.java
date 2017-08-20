package com.epam.auction.validator;

import java.util.regex.Pattern;

abstract class Validator {

    private String validationMessage;

    public String getValidationMessage() {
        return validationMessage;
    }

    void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    boolean validate(String verifiable, String pattern) {
        if (Pattern.compile(pattern).matcher(verifiable).find()) {
            return true;
        } else {
            this.validationMessage = "Not valid parameter: [" + verifiable + "].";
            return false;
        }
    }

}