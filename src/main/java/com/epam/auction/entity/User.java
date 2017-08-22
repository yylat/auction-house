package com.epam.auction.entity;

import java.math.BigDecimal;
import java.util.Arrays;

public class User extends Entity {

    private String username;
    private String password;
    private String lastName;
    private String middleName;
    private String firstName;
    private String phoneNumber;
    private String email;
    private BigDecimal balance = new BigDecimal(0);
    private boolean isDeleted = false;
    private UserRole role = UserRole.USER;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String lastName, String middleName, String firstName, String phoneNumber, String email) {
        this.username = username;
        this.password = password;
        this.lastName = lastName;
        this.middleName = middleName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public User(int id, String username, String password, String lastName, String middleName, String firstName, String phoneNumber, String email, UserRole role) {
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

    public User(int id, String username, String password, String lastName, String middleName, String firstName, String phoneNumber, String email, BigDecimal balance, boolean isDeleted, UserRole role) {
        super(id);
        this.username = username;
        this.password = password;
        this.lastName = lastName;
        this.middleName = middleName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.balance = balance;
        this.isDeleted = isDeleted;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (isDeleted != user.isDeleted) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (middleName != null ? !middleName.equals(user.middleName) : user.middleName != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(user.phoneNumber) : user.phoneNumber != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (balance != null ? !balance.equals(user.balance) : user.balance != null) return false;
        return role == user.role;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (isDeleted ? 1 : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    public enum UserRole {

        ADMIN,
        USER;

        public static UserRole define(int id) {
            return Arrays.stream(UserRole.values())
                    .filter(userRole -> id == userRole.ordinal())
                    .findFirst()
                    .orElse(null);
        }

    }
}