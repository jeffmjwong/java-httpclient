package com.pluralsight.httpclient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;

public class WebSocketMain {
    public static void main(String[] args) {
        final int messageCount = 5;
        final CountDownLatch receiveLatch = new CountDownLatch(messageCount);

        final CompletableFuture<WebSocket> wsFuture = HttpClient
                .newHttpClient()
                .newWebSocketBuilder()
                .connectTimeout(Duration.ofSeconds(3))
                .buildAsync(URI.create("ws://echo.websocket.org"), new EchoListener(receiveLatch));

        wsFuture.thenAccept(webSocket -> {
            webSocket.request(messageCount);

            for (int i = 0; i < messageCount; i++) {
                webSocket.sendText("Message " + i, true);
            }
        });

        try {
            receiveLatch.await();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static class EchoListener implements WebSocket.Listener {
        final CountDownLatch receiveLatch;

        public EchoListener(CountDownLatch receiveLatch) {
            this.receiveLatch = receiveLatch;
        }

        @Override
        public void onOpen(WebSocket webSocket) {
            System.out.println("Websocket opened");
        }

        @Override
        public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
            System.out.println("onText " + data);
            receiveLatch.countDown();
            return null;
        }
    }
}
