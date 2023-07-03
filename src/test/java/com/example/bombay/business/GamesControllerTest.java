package com.example.bombay.business;

import com.example.bombay.business.GamesController;
import com.example.bombay.business.GamesService;
import com.example.bombay.business.dto.GameRequest;
import com.example.bombay.business.dto.GameResponse;
import com.example.bombay.infratstructure.error.ApiError;
import com.example.bombay.infratstructure.exception.BusinessException;
import com.example.bombay.validation.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class GamesControllerTest {
    @Mock
    private GamesService gamesService;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private GamesController gamesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPlayGame_Success() {
        double betAmount = 99.0;
        int betNumber = 99;
        GameRequest gameRequest = new GameRequest();
        gameRequest.setBetAmount(99);
        gameRequest.setBetNumber(99);

        // Mock the behavior of the gamesService
        GameResponse mockResponse = new GameResponse();
        mockResponse.setBetAmount(99);
        mockResponse.setBetNumber(99);
        mockResponse.setWinAmount(9801);
        mockResponse.setWinNumber(99);
        mockResponse.setStatus("You win");
        when(gamesService.playGame(gameRequest)).thenReturn(mockResponse);

        ResponseEntity<?> response = gamesController.playGame(betAmount, betNumber);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof GameResponse);
        GameResponse gameResponse = (GameResponse) response.getBody();
        assertEquals(99, gameResponse.getBetAmount());
        assertEquals(99, gameResponse.getBetNumber());
        assertEquals(9801, gameResponse.getWinAmount());
        assertEquals(99, gameResponse.getWinNumber());
        assertEquals("You win", gameResponse.getStatus());
    }


    @Test
    void testPlayGame_InvalidBetAmount() throws BusinessException {
        double betAmount = 0;
        int betNumber = 99;
        GameRequest gameRequest = new GameRequest();



        gameRequest.setBetAmount(0);
        gameRequest.setBetNumber(10);

        ResponseEntity<?> response = gamesController.playGame(betAmount, betNumber);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertTrue(response.getBody() instanceof ApiError);
        ApiError apiError = (ApiError) response.getBody();
        assertEquals("Bet amount is zero or negative.", apiError.getMessage());
        assertEquals(112, apiError.getErrorCode());
    }

    @Test
    void testPlayGame_InvalidBetNumber() throws BusinessException {

        double betAmount = 10;
        int betNumber = 0;

        GameRequest gameRequest = new GameRequest();
        gameRequest.setBetAmount(10);
        gameRequest.setBetNumber(0);

        ResponseEntity<?> response = gamesController.playGame(betAmount, betNumber);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertTrue(response.getBody() instanceof ApiError);
        ApiError apiError = (ApiError) response.getBody();
        assertEquals("Bet number is not between 1 and 100.", apiError.getMessage());
        assertEquals(111, apiError.getErrorCode());
    }
}

