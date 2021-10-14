package com.project.inventory.common.user.model;

public class UserAccount {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String username;
    private String role;
    private String birthday;
    private String password;
    private String email;
    private boolean isEnabled;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber( String phoneNumber ) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled( boolean enabled ) {
        isEnabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername( String username ) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole( String role ) {
        this.role = role;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday( String birthday ) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    @Override
    public boolean equals( Object obj ) {
        return super.equals( obj );
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
