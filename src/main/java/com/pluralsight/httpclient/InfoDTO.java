package com.pluralsight.httpclient;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class InfoDTO {
    private String seed;
    private int results;
    private int page;
    private String version;
}
