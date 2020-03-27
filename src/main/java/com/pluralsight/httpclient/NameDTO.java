package com.pluralsight.httpclient;

import lombok.Getter;

@Getter
public class NameDTO {
    private String title;
    private String first;
    private String last;

    @Override
    public String toString() {
        return "Name{title='" + title + "', first='" + first + "', last='" + last + "'}";
    }
}
