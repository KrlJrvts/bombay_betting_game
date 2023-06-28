package com.example.bombay;

import com.example.bombay.business.GamesController;
import com.example.bombay.business.GamesService;
import com.example.bombay.business.dto.GameRequest;
import com.example.bombay.business.dto.GameResponse;
import com.example.bombay.domain.GameService;
import com.example.bombay.infratstructure.error.ApiError;
import com.example.bombay.infratstructure.exception.BusinessException;
import com.example.bombay.validation.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GamesControllerTest {

    @Mock
    private ValidationService validationService;

    @Mock
    private GamesService gamesService;

    @Mock
    private GamesController gamesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void playGame_ValidRequest_Success() {
        GameRequest gameRequest = new GameRequest(40.5, 50);
        GameResponse gameResponse = new GameResponse(40.5, 50, 80.19, 50, 42, "You win");
        when(gamesService.playGame(gameRequest)).thenReturn(gameResponse);


        ResponseEntity<?> responseEntity = gamesController.playGame(gameRequest);

        assertNotNull(responseEntity);
        assertEquals(gameResponse, responseEntity.getBody());
        verify(validationService).validateCorrectBetAmount(40.5);
        verify(validationService).validateCorrectBetNumber(50);
    }

    @Test
    void playGame_InvalidBetNumber_Return403() {
        GameRequest gameRequest = new GameRequest(40.5, 101);
        String expectedResponse = "Bet number must be between 1 and 100";
        int expectedErrorCode = 111;
        when(validationService.validateCorrectBetNumber(gameRequest.getBetNumber()))
                .thenThrow(new BusinessException(expectedResponse, expectedErrorCode));

        ResponseEntity<?> responseEntity = gamesController.playGame(gameRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getBody());
        ApiError apiError = (ApiError) responseEntity.getBody();
        assertNotNull(apiError);
        assertEquals(expectedResponse, apiError.getMessage());
        assertEquals(expectedErrorCode, apiError.getErrorCode());
    }

    @Test
    void playGame_InvalidBetAmount_Return403() {
        GameRequest gameRequest = new GameRequest(0, 50);
        String expectedResponse = "Bet amount must be greater than 0";
        int expectedErrorCode = 112;
        when(validationService.validateCorrectBetAmount(gameRequest.getBetAmount()))
                .thenThrow(new BusinessException(expectedResponse, expectedErrorCode));

        ResponseEntity<?> responseEntity = gamesController.playGame(gameRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getBody());
        ApiError apiError = (ApiError) responseEntity.getBody();
        assertNotNull(apiError);
        assertEquals(expectedResponse, apiError.getMessage());
        assertEquals(expectedErrorCode, apiError.getErrorCode());
    }
}
