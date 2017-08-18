package com.epam.auction.validator;

import com.epam.auction.entity.User;

public class UserValidator extends Validator {

    private final static String USERNAME_PATTERN = "[A-Za-z][A-Za-z0-9.//-]{5,20}";
    private final static String PASSWORD_PATTERN = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,30}";
    private final static String NAME_PATTERN = "[A-Za-z]{2,45}";
    private final static String EMAIL_PATTERN = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$";
    private final static String PHONE_PATTERN = "[+][0-9]{11,12}";

    public boolean validateSignUpParam(User user) {
        return validate(user.getUsername(), USERNAME_PATTERN) &&
                validate(user.getPassword(), PASSWORD_PATTERN) &&
                validate(user.getLastName(), NAME_PATTERN) &&
                validate(user.getMiddleName(), NAME_PATTERN) &&
                validate(user.getFirstName(), NAME_PATTERN) &&
                validate(user.getEmail(), EMAIL_PATTERN) &&
                validate(user.getPhoneNumber(), PHONE_PATTERN);
    }

    public boolean validateSingInParam(User user) {
        return validate(user.getUsername(), USERNAME_PATTERN) &&
                validate(user.getPassword(), PASSWORD_PATTERN);
    }

}