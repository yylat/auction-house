package com.epam.auction.validator;

import com.epam.auction.entity.User;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class UserValidatorTest {

    private static UserValidator userValidator;

    @BeforeClass
    public static void init() {
        userValidator = new UserValidator();
    }

    private User user;
    private boolean signUpParamValidity;
    private boolean usernameValidity;
    private boolean emailValidity;
    private boolean passwordValidity;
    private boolean profileValidity;

    public UserValidatorTest(User user, boolean signUpParamValidity,
                             boolean usernameValidity, boolean emailValidity,
                             boolean passwordValidity, boolean profileValidity) {
        this.user = user;
        this.signUpParamValidity = signUpParamValidity;
        this.usernameValidity = usernameValidity;
        this.emailValidity = emailValidity;
        this.passwordValidity = passwordValidity;
        this.profileValidity = profileValidity;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {

        return Arrays.asList(new Object[][]{
                {new User("user1", "User111",
                        "lastname", "firstname",
                        "+375298965324", "email@email.com"),
                        true, true, true, true, true},

                {new User("user2                 345", "User222",
                        "lastname", "firstname",
                        "+375298965324", "email@email.com"),
                        false, false, true, true, true},

                {new User("user3", "asd",
                        "lastname", "firstname",
                        "+375298965324", "email@email.com"),
                        false, true, true, false, true},

                {new User("user4", "User444",
                        "lastname 4", "firstname",
                        "+375298965324", "email@email.com"),
                        false, true, true, true, false},

                {new User("user5", "User555",
                        "lastname", "middlename", "firstname",
                        "+375298965324", "email@email.com"),
                        true, true, true, true, true},

                {new User("user6", "User666",
                        "lastname", "middlename6", "firstname",
                        "+375298965324", "email@email.com"),
                        false, true, true, true, false},

                {new User("user7", "User777",
                        "lastname", "middlename", "firstname7",
                        "+375298965324", "email@email.com"),
                        false, true, true, true, false},

                {new User("user8", "User888",
                        "lastname", "middlename", "firstname",
                        "888", "email@email.com"),
                        false, true, true, true, false},

                {new User("user9", "User999",
                        "lastname", "middlename", "firstname",
                        "+375298965324", "email"),
                        false, true, false, true, true}
        });
    }

    @Test
    public void validateSignUpParamTest() {
        assertThat(userValidator.validateSignUpParam(user), is(signUpParamValidity));
    }

    @Test
    public void validateUsernameTest() {
        assertThat(userValidator.validateUsername(user.getUsername()), is(usernameValidity));
    }

    @Test
    public void validateEmailTest() {
        assertThat(userValidator.validateEmail(user.getEmail()), is(emailValidity));
    }

    @Test
    public void validatePasswordTest() {
        assertThat(userValidator.validatePassword(user.getPassword()), is(passwordValidity));
    }

    @Test
    public void validateProfileTest() {
        assertThat(userValidator.validateProfile(user.getLastName(), user.getMiddleName(),
                user.getFirstName(), user.getPhoneNumber()),
                is(profileValidity));
    }

}