package com.pluralsight.httpclient;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
//        useRandomUserGenerator();
//        useLinkValidatorSynchronous();
        useLinkValidatorAsynchronous();
    }

    private static void useLinkValidatorAsynchronous() {
        try {
            final List<CompletableFuture<String>> futures = Files.lines(Path.of("urls.txt"))
                    .map(Main::validateLinkAsync)
                    .collect(Collectors.toList());

            futures.stream().map(CompletableFuture::join).forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("File error: " + e.getMessage());
        }
    }

    private static CompletableFuture<String> validateLinkAsync(String link) {
        final HttpClient httpClient = HttpClient
                .newBuilder()
                .connectTimeout(Duration.ofSeconds(3))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        final HttpRequest request = HttpRequest
                .newBuilder(URI.create(link))
                .GET()
                .header("Accept", "text/html")
                .timeout(Duration.ofSeconds(3))
                .build();

        return httpClient
                .sendAsync(request, HttpResponse.BodyHandlers.discarding())
                .thenApply(Main::responseToString)
                .exceptionally(e -> String.format("%s -> %s (something went wrong - %s)", link, false, e.getMessage()));
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
