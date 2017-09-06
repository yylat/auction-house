package com.epam.auction.entity;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Represents `user` table from database.
 */
public class User extends Entity {

    /**
     * Name in the system.
     */
    private String username;
    /**
     * Password.
     */
    private String password;
    /**
     * Last name.
     */
    private String lastName;
    /**
     * Middle name.
     */
    private String middleName;
    /**
     * First name.
     */
    private String firstName;
    /**
     * Phone number.
     */
    private String phoneNumber;
    /**
     * Email.
     */
    private String email;
    /**
     * Balance.
     */
    private BigDecimal balance = new BigDecimal(0);
    /**
     * Boolean value that detect is user account deleted or not ("0" - not deleted, "1" - deleted).
     */
    private boolean isBanned = false;
    /**
     * Shows if user banned or not.
     */
    private UserRole role = UserRole.USER;

    /**
     * Constructs user without parameters.
     */
    public User() {
    }

    /**
     * Constructs user with username and password.
     *
     * @param username username
     * @param password password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Constructs user with username, password, last name,
     * first name, phone number and email.
     *
     * @param username    username
     * @param password    password
     * @param lastName    last name
     * @param firstName   first name
     * @param phoneNumber phone number
     * @param email       email
     */
    public User(String username, String password, String lastName, String firstName, String phoneNumber, String email) {
        this.username = username;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    /**
     * Constructs user with username, password, last name, middle name,
     * first name, phone number and email.
     *
     * @param username    username
     * @param password    password
     * @param lastName    last name
     * @param middleName  middle name
     * @param firstName   first name
     * @param phoneNumber phone number
     * @param email       email
     */
    public User(String username, String password, String lastName, String middleName, String firstName, String phoneNumber, String email) {
        this.username = username;
        this.password = password;
        this.lastName = lastName;
        this.middleName = middleName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    /**
     * Constructs user with id, username, password, last name, middle name,
     * first name, phone number, email and role.
     *
     * @param id          id
     * @param username    username
     * @param password    password
     * @param lastName    last name
     * @param middleName  middle name
     * @param firstName   first name
     * @param phoneNumber phone number
     * @param email       email
     * @param role        role
     */
    public User(long id, String username, String password, String lastName, String middleName, String firstName, String phoneNumber, String email, UserRole role) {
        super(id);
        this.username = username;
        this.password = password;
        this.lastName = lastName;
        this.middleName = middleName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
    }

    /**
     * Constructs user with id, username, password, last name, middle name,
     * first name, phone number, email, balance, ban status and role.
     *
     * @param id          id
     * @param username    username
     * @param password    password
     * @param lastName    last name
     * @param middleName  middle name
     * @param firstName   first name
     * @param phoneNumber phone number
     * @param email       email
     * @param balance     balance
     * @param isBanned    ban status
     * @param role        role
     */
    public User(long id, String username, String password, String lastName, String middleName, String firstName, String phoneNumber, String email, BigDecimal balance, boolean isBanned, UserRole role) {
        super(id);
        this.username = username;
        this.password = password;
        this.lastName = lastName;
        this.middleName = middleName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.balance = balance;
        this.isBanned = isBanned;
        this.role = role;
    }

    /**
     * Returns username.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns last name.
     *
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns middle name.
     *
     * @return middle name
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets middle name.
     *
     * @param middleName middle name
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Returns first name.
     *
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns phone number.
     *
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets phone number.
     *
     * @param phoneNumber phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns email.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns balance.
     *
     * @return balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Sets balance.
     *
     * @param balance balance
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * Returns <code>true</code> if user is banned.
     *
     * @return <code>true</code> if user is banned;
     * <code>false</code> otherwise
     */
    public boolean isBanned() {
        return isBanned;
    }

    /**
     * Sets whether user is banned or not.
     *
     * @param banned ban status
     */
    public void setBanned(boolean banned) {
        isBanned = banned;
    }


    /**
     * Returns role of the user.
     *
     * @return role of the user
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * Sets role of the user.
     *
     * @param role role of the user
     */
    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        return isBanned == user.isBanned
                && (username != null ? username.equals(user.username) : user.username == null)
                && (password != null ? password.equals(user.password) : user.password == null)
                && (lastName != null ? lastName.equals(user.lastName) : user.lastName == null)
                && (middleName != null ? middleName.equals(user.middleName) : user.middleName == null)
                && (firstName != null ? firstName.equals(user.firstName) : user.firstName == null)
                && (phoneNumber != null ? phoneNumber.equals(user.phoneNumber) : user.phoneNumber == null)
                && (email != null ? email.equals(user.email) : user.email == null)
                && (balance != null ? balance.equals(user.balance) : user.balance == null)
                && role == user.role;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (isBanned ? 1 : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    /**
     * Represents `user_role` table from database.
     */
    public enum UserRole {

        /**
         * Administrator role.
         */
        ADMIN,
        /**
         * User role.
         */
        USER;

        /**
         * Returns usr role with id (ordinal number in enum).
         *
         * @param id id of the user role
         * @return user role
         */
        public static UserRole define(int id) {
            return Arrays.stream(UserRole.values())
                    .filter(userRole -> id == userRole.ordinal())
                    .findFirst()
                    .orElse(null);
        }

    }
}