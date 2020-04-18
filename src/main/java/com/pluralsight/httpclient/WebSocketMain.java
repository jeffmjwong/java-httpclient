package com.pluralsight.httpclient;

import java.util.concurrent.CountDownLatch;

public class WebSocketMain {
    public static void main(String[] args) {
        final int messageCount = 5;
        final CountDownLatch receiveLatch = new CountDownLatch(messageCount);
    }
}
