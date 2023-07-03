package com.example.bombay.domain;

import com.example.bombay.business.dto.GameRequest;
import com.example.bombay.business.dto.GameResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameServiceIntegrationTest {

    @Test
        void testPlayGame_Win() {

        double betAmount = 40.5;
        int betNumber = 50;
        int randomNumber = 30;
        double winAmount = betAmount * (99.0 / (100 - betNumber));

        GameService gameService = new GameService() {
            @Override
            public int getRandomNumber() {
                return randomNumber;
            }
        };

        GameRequest gameRequest = new GameRequest(betAmount, betNumber);

        // Act
        GameResponse gameResponse = gameService.playGame(gameRequest);

        // Assert
        assertEquals(betAmount, gameResponse.getBetAmount());
        assertEquals(betNumber, gameResponse.getBetNumber());
        assertEquals(winAmount, gameResponse.getWinAmount());
        assertEquals("You win", gameResponse.getStatus());
        assertEquals(betNumber, gameResponse.getWinNumber());
        assertEquals(randomNumber, gameResponse.getRandomNumber());
    }

    @Test
    void testPlayGame_Lose() {

        double betAmount = 40.5;
        int betNumber = 50;
        int randomNumber = 60;
        double winAmount = 0;

        GameService gameService = new GameService() {
            @Override
            public int getRandomNumber() {
                return randomNumber;
            }
        };

        GameRequest gameRequest = new GameRequest(betAmount, betNumber);

        // Act
        GameResponse gameResponse = gameService.playGame(gameRequest);

        // Assert
        assertEquals(betAmount, gameResponse.getBetAmount());
        assertEquals(betNumber, gameResponse.getBetNumber());
        assertEquals(winAmount, gameResponse.getWinAmount());
        assertEquals("You lose", gameResponse.getStatus());
        assertEquals(randomNumber, gameResponse.getWinNumber());
        assertEquals(randomNumber, gameResponse.getRandomNumber());
    }
}
