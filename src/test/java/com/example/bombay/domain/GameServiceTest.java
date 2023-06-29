package com.example.bombay.domain;

import com.example.bombay.business.dto.GameRequest;
import com.example.bombay.business.dto.GameResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

class GameServiceTest {

    private GameService gameService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameService = new GameService();
    }

    @Test
    void testGetRandomNumber() {
        int randomNumber = gameService.getRandomNumber();
        assertTrue(randomNumber >= 1 && randomNumber <= 100);
    }


    @Test
    void testPlayGame_Win() {
        GameService gameService = new GameService();
        int randomNumber = 30;

        GameService mockGameService = Mockito.spy(GameService.class);
        doReturn(randomNumber).when(mockGameService).getRandomNumber();

        GameRequest gameRequest = new GameRequest(40.5, 50);
        GameResponse gameResponse = mockGameService.playGame(gameRequest);

        assertEquals(40.5, gameResponse.getBetAmount());
        assertEquals(50, gameResponse.getBetNumber());
        assertTrue(gameResponse.getWinAmount() > 0);
        assertTrue(gameResponse.getBetNumber() >= 1 && gameResponse.getBetNumber() <= 100);
        assertEquals(30, gameResponse.getRandomNumber());
        assertEquals("You win", gameResponse.getStatus());
    }

    @Test
    void testPlayGame_Lose() {
        int randomNumber = 60;

        GameService mockGameService = Mockito.spy(GameService.class);
        doReturn(randomNumber).when(mockGameService).getRandomNumber();

        GameRequest gameRequest = new GameRequest(40.5, 50);
        GameResponse gameResponse = mockGameService.playGame(gameRequest);

        assertEquals(40.5, gameResponse.getBetAmount());
        assertEquals(50, gameResponse.getBetNumber());
        assertEquals(0, gameResponse.getWinAmount());
        assertTrue(gameResponse.getBetNumber() >= 1 && gameResponse.getBetNumber() <= 100);
        assertTrue(gameResponse.getRandomNumber() >= 1 && gameResponse.getRandomNumber() <= 100);
        assertEquals("You lose", gameResponse.getStatus());
    }

    @Test
    void testPlayGame_Win100() {
        GameRequest gameRequest = new GameRequest(40.5, 100);
        GameResponse gameResponse = gameService.playGame(gameRequest);

        assertEquals(40.5, gameResponse.getBetAmount());
        assertEquals(100, gameResponse.getBetNumber());
        assertEquals(0, gameResponse.getWinAmount());
        assertEquals(100, gameResponse.getWinNumber());
        assertTrue(gameResponse.getRandomNumber() >= 1 && gameResponse.getRandomNumber() <= 100);
        assertEquals("You win", gameResponse.getStatus());
    }

    @Test
    void testPlayGame_BetNumber_Equals_RandomNumber() {

        int randomNumber = 50;

        GameService mockGameService = Mockito.spy(GameService.class);
        doReturn(randomNumber).when(mockGameService).getRandomNumber();

        GameRequest gameRequest = new GameRequest(40.5, 50);
        GameResponse gameResponse = mockGameService.playGame(gameRequest);

        assertEquals(40.5, gameResponse.getBetAmount());
        assertEquals(50, gameResponse.getBetNumber());
        assertEquals(40.5, gameResponse.getWinAmount());
        assertEquals(50, gameResponse.getWinNumber());
        assertEquals(50, gameResponse.getRandomNumber());
        assertEquals("You win", gameResponse.getStatus());
    }

}
