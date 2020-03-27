package com.pluralsight.httpclient;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) {
        final HttpClient httpClient = HttpClient.newHttpClient();

        final HttpRequest request = HttpRequest
                .newBuilder(URI.create("https://randomuser.me/api/?results=2"))
                .build();

        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

            final RandomUserDTO user1 = new Gson().fromJson(response.body(), RandomUserDTO.class);
            System.out.println(user1.getResults());
            System.out.println(user1.getInfo());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
