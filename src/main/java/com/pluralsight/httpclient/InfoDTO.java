package com.pluralsight.httpclient;

import lombok.Getter;

@Getter
public class InfoDTO {
    private String seed;
    private int results;
    private int page;
    private String version;

    @Override
    public String toString() {
        return "Info{seed='" + seed + "', results=" + results + ", page=" + page + ", version='" + version + "'}";
    }
}
