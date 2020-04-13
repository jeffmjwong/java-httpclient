package com.pluralsight.httpclient;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
//        useRandomUserGenerator();
//        useLinkValidatorSynchronous();
//        useLinkValidatorAsynchronous();

        final HttpClient httpClient = HttpClient.newHttpClient();
        final HttpRequest request1 = HttpRequest
                .newBuilder(URI.create("https://httpstat.us/200?sleep=15000"))
                .GET()
                .build();
        final HttpRequest request2 = HttpRequest
                .newBuilder(URI.create("https://httpstat.us/200?sleep=3000"))
                .GET()
                .build();
        final HttpRequest request3 = HttpRequest
                .newBuilder(URI.create("https://httpstat.us/200"))
                .GET()
                .build();

        try {
            final CompletableFuture<Integer> cf1 = httpClient.sendAsync(request3, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::statusCode);
            System.out.println("a");
            System.out.println("b");
            System.out.println("c");
            final int i1 = cf1.get();
            System.out.println("d" + " is a " + i1);
            System.out.println("e");

            TimeUnit.SECONDS.sleep(5);

            System.out.println("f");
            System.out.println("g");
            System.out.println("h");

//            final HttpResponse<Void> res1 = httpClient.send(request1, HttpResponse.BodyHandlers.discarding());
//            System.out.println("15s done");
//
//            final HttpResponse<Void> res2 = httpClient.send(request1, HttpResponse.BodyHandlers.discarding());
//            System.out.println("3s done");

//            final CompletableFuture<HttpResponse<Void>> future1 = httpClient.sendAsync(request1, HttpResponse.BodyHandlers.discarding());
//            final CompletableFuture<Void> statusCode1 = future1.thenAccept(f -> System.out.println(f.statusCode() + " - 15s done"));
//
//            final CompletableFuture<HttpResponse<Void>> future2 = httpClient.sendAsync(request2, HttpResponse.BodyHandlers.discarding());
//            final CompletableFuture<Void> statusCode2 = future2.thenAccept(f -> System.out.println(f.statusCode() + " - 3s done"));

//            final List<CompletableFuture<Void>> list1 = new ArrayList<>(Arrays.asList(statusCode1, statusCode2));
//            list1.forEach(CompletableFuture::join);
        } catch (Exception e) {
            System.out.println("Http request error: " + e.getMessage());
        }
    }

//    private static void useLinkValidatorAsynchronous() {
//        try {
//            final List<String> futures = Files.lines(Path.of("urls.txt"))
//                    .map(Main::validateLinkAsync)
//                    .collect(Collectors.toList());
//
//            futures.stream().map(CompletableFuture::join).forEach(System.out::println);
//
//        } catch (Exception e) {
//            System.out.println("File error: " + e.getMessage());
//        }
//    }
//
//    private static String validateLinkAsync(String link) {
//        final HttpClient httpClient = HttpClient.newHttpClient();
//        final HttpRequest request = HttpRequest
//                .newBuilder(URI.create(link))
//                .GET()
//                .build();
//
//        try {
//            final HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
//            return responseToString(response);
//        } catch (Exception e) {
//            System.out.println("Http request error: " + e.getMessage());
//            return String.format("%s -> %s", link, false);
//        }
//    }

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
