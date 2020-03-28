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
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
//        useRandomUserGenerator();
        useLinkValidatorSynchronous();
    }

    private static void useLinkValidatorSynchronous() {
        try {
            final List<String> strings = Files
                    .lines(Path.of("urls.txt"))
                    .map(Main::validateLink)
                    .collect(Collectors.toList());
            System.out.println(strings);
        } catch (Exception e) {
            System.out.println("File error: " + e.getMessage());
        }


//        final Path path = Paths.get("urls.txt");
//
//        try {
//            final List<String> urls = Files.readAllLines(path);
//
//            urls.forEach(url -> {
//                final HttpRequest request = HttpRequest
//                        .newBuilder(URI.create(url))
//                        .build();
//
//
//            });
//        } catch (Exception e) {
//            System.out.println("File reading error: " + e.getMessage());
//        }

    }

    private static String validateLink(String link) {
        final HttpClient httpClient = HttpClient.newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder(URI.create(link)).build();

        try {
            final HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
            return responseToString(response);
        } catch (Exception e) {
            System.out.println("Http request error: " + e.getMessage());
            return "Failed";
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
