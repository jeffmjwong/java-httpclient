package com.pluralsight.httpclient;

public class UserDTO {
    private String gender;

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "User{gender='" + gender + "'}";
    }
}
