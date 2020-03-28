package com.pluralsight.httpclient;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
//        useRandomUserGenerator();
        useLinkValidatorSynchronous();
    }

    private static void useLinkValidatorSynchronous() {
        final HttpClient httpClient = HttpClient.newHttpClient();

        final Path path = Paths.get("urls.txt");

        try {
            final List<String> urls = Files.readAllLines(path);

            urls.forEach(url -> {
                final HttpRequest request = HttpRequest
                        .newBuilder(URI.create(url))
                        .build();

                try {
                    final HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
                    System.out.println(responseToString(response));
                } catch (Exception e) {
                    System.out.println("Http request error: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            System.out.println("File reading error: " + e.getMessage());
        }

    }

    private static String validateLink(String link) {
        return null;
    }

    private static String responseToString(HttpResponse<Void> response) {
        final int status = response.statusCode();
        final boolean isSuccess = status >= 200 && status <= 299;
        return String.format("%s -> %s (status: %s)", response.uri(), isSuccess, status);
    }

    private static void useRandomUserGenerator() {
        final HttpClient httpClient = HttpClient.newHttpClient();

        final HttpRequest request = HttpRequest
                .newBuilder(URI.create("https://randomuser.me/api/?results=5"))
                .build();

        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            final RandomUserDataDTO data = new Gson().fromJson(response.body(), RandomUserDataDTO.class);

            final List<String> fullNames = data
                    .getResults()
                    .stream()
                    .map(UserDTO::fullName)
                    .collect(Collectors.toList());

            System.out.println(fullNames);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
