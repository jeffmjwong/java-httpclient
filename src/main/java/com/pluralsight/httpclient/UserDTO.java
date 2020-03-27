package com.pluralsight.httpclient;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserDTO {
    private String gender;
    private NameDTO name;

    public String fullName() {
        return name.getTitle() + " " + name.getFirst() + " " + name.getLast();
    }
}
