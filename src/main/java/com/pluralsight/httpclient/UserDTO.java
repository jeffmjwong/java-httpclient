package com.pluralsight.httpclient;

public class UserDTO {
    private String gender;
    private NameDTO name;

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "User{gender='" + gender + "', name=" + name + "}";
    }
}
