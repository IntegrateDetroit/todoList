package io.integral.todo;

import java.util.Objects;

public class User {

    private Integer userId;
    private String firstName;
    private String lastName;
    private String eMail;
    private String passwordHash;
    private String signupDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(eMail, user.eMail) &&
                Objects.equals(passwordHash, user.passwordHash) &&
                Objects.equals(signupDate, user.signupDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, firstName, lastName, eMail, passwordHash, signupDate);
    }

    public Integer getUserID() {
        return userId;
    }

    public void setUserID(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String geteMail() {
        return eMail;
    }

    public void setEmail(String eMail) {
        this.eMail = eMail;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(String signupDate) {
        this.signupDate = signupDate;
    }

    public User setUser(String firstName, String lastName, String eMail, String passwordHash, String signupDate) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(eMail);
        user.setPasswordHash(passwordHash);
        user.setSignupDate(signupDate);
        return user;
    }


}
