package com.pluralsight.httpclient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) {
        final HttpClient httpClient = HttpClient.newHttpClient();

        final HttpRequest request = HttpRequest
                .newBuilder(URI.create("https://pluralsight.com"))
                .build();

        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            System.out.println(response.headers());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
