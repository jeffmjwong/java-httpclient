package com.pluralsight.httpclient;

public class InfoDTO {
    private String seed;
    private int results;
    private int page;
    private String version;

    public String getSeed() {
        return seed;
    }
    public int getResults() {
        return results;
    }
    public int getPage() {
        return page;
    }
    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "Info{seed='" + seed + "', results=" + results + ", page=" + page + ", version='" + version + "'}";
    }
}
