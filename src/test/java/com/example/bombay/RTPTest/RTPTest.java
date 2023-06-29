package com.example.bombay.RTPTest;

import com.example.bombay.business.dto.GameRequest;
import com.example.bombay.business.dto.GameResponse;
import com.example.bombay.domain.GameService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class RTPTest {
    public static void main(String[] args) {
        int numberOfThreads = 24;
        int numberOfIterations = 1000000;
        AtomicReference<Double>  totalBetAmount = new AtomicReference<>(0.0);
        AtomicReference<Double>  totalWinAmount = new AtomicReference<>(0.0);



        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        GameService gameService = new GameService();

        for (int i = 0; i < numberOfIterations; i++) {
            executorService.execute(() -> {
                GameRequest gameRequest = new GameRequest(1.0, 50);
                GameResponse gameResponse = gameService.playGame(gameRequest);
                synchronized (RTPTest.class) {
                    totalBetAmount.updateAndGet(amount-> amount + gameResponse.getBetAmount());
                    totalWinAmount.updateAndGet(amount -> amount + gameResponse.getWinAmount());
                }
            });

        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double rtp = (totalWinAmount.get() / totalBetAmount.get()) * 100;
        System.out.println("RTP: " + rtp + "%");

    }
}
