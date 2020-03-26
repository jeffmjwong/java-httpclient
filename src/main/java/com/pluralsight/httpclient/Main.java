package com.pluralsight.httpclient;

import java.net.http.HttpClient;

public class Main {
    public static void main(String[] args) {
        final HttpClient httpClient = HttpClient.newHttpClient();
        System.out.println(httpClient);
    }
}
