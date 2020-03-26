package com.pluralsight.httpclient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class Main {
    public static void main(String[] args) {
        final HttpClient httpClient = HttpClient.newHttpClient();
        final HttpRequest httpRequest = HttpRequest
                .newBuilder(URI.create("https://pluralsight.com"))
                .build();

        httpClient.send(request);
    }
}
