package com.pluralsight.httpclient;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
//        useRandomUserGenerator();
        useRandomUserGeneratorAsync();
//        useLinkValidatorSynchronous();
    }

    private static void useLinkValidatorSynchronous() {
        try {
            Files.lines(Path.of("urls.txt"))
                    .map(Main::validateLink)
                    .forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("File error: " + e.getMessage());
        }
    }

    private static String validateLink(String link) {
        final HttpClient httpClient = HttpClient.newHttpClient();
        final HttpRequest request = HttpRequest
                .newBuilder(URI.create(link))
                .GET()
                .build();

        try {
            final HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
            return responseToString(response);
        } catch (Exception e) {
            System.out.println("Http request error: " + e.getMessage());
            return String.format("%s -> %s", link, false);
        }
    }

    private static String responseToString(HttpResponse<Void> response) {
        final int status = response.statusCode();
        final boolean isSuccess = status >= 200 && status <= 299;
        return String.format("%s -> %s (status: %s)", response.uri(), isSuccess, status);
    }

    private static void useRandomUserGeneratorAsync() {
        final HttpClient httpClient = HttpClient.newHttpClient();

        final HttpRequest request = HttpRequest
                .newBuilder(URI.create("https://randomuser.me/api/?results=5"))
                .build();

//        final CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
//        responseFuture.thenAccept(response -> System.out.println(response.body()));

        try {
            System.out.println(1);
            final CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(2);

            responseFuture.thenAccept(response -> {
                final RandomUserDataDTO data = new Gson().fromJson(response.body(), RandomUserDataDTO.class);

                final List<String> fullNames = data
                        .getResults()
                        .stream()
                        .map(UserDTO::fullName)
                        .collect(Collectors.toList());

                System.out.println(fullNames);
            });
            System.out.println(3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void useRandomUserGenerator() {
        final HttpClient httpClient = HttpClient.newHttpClient();

        final HttpRequest request = HttpRequest
                .newBuilder(URI.create("https://randomuser.me/api/?results=5"))
                .build();

        try {
            System.out.println(1);
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(2);
            final RandomUserDataDTO data = new Gson().fromJson(response.body(), RandomUserDataDTO.class);

            final List<String> fullNames = data
                    .getResults()
                    .stream()
                    .map(UserDTO::fullName)
                    .collect(Collectors.toList());

            System.out.println(fullNames);
            System.out.println(3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
