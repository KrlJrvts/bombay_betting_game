package com.example.bombay.business;

import com.example.bombay.business.GamesController;
import com.example.bombay.business.dto.GameRequest;
import com.example.bombay.business.dto.GameResponse;
import com.example.bombay.business.GamesService;
import com.example.bombay.domain.GameService;
import com.example.bombay.infratstructure.error.ApiError;
import com.example.bombay.infratstructure.exception.BusinessException;
import com.example.bombay.validation.Error;
import com.example.bombay.validation.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;

@SpringJUnitConfig
@WebMvcTest(GamesController.class)
class GamesControllerIntegrationTest {

    @MockBean
    private GamesService gamesService;

    @MockBean
    private ValidationService validationService;

    @InjectMocks
    private GamesController gamesController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(gamesController).build();
    }

    @Test
    void testPlayGame_Success() throws Exception {
        GameRequest request = new GameRequest(10.0, 50);
        GameResponse response = new GameResponse(10.0, 50, 20.0, 50, 60, "You win");

        when(gamesService.playGame(request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"betAmount\": 10.0, \"betNumber\": 50}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.betAmount").value(10.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.betNumber").value(50))
                .andExpect(MockMvcResultMatchers.jsonPath("$.winAmount").value(20.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.winNumber").value(50))
                .andExpect(MockMvcResultMatchers.jsonPath("$.randomNumber").value(60))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("You win"));

        verify(gamesService, times(1)).playGame(request);
    }

    @Test
    void testPlayGame_InvalidBetAmount() throws Exception {
        GameRequest request = new GameRequest(-10.0, 50);
        ApiError error = new ApiError(Error.INCORRECT_BET_AMOUNT.getMessage(), Error.INCORRECT_BET_AMOUNT.getErrorCode());


        doThrow(new BusinessException(Error.INCORRECT_BET_AMOUNT.getMessage(), Error.INCORRECT_BET_AMOUNT.getErrorCode()))
                .when(validationService);
                ValidationService.validateCorrectBetAmount(request.getBetAmount());

        mockMvc.perform(MockMvcRequestBuilders.post("/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"betAmount\": -10.0, \"betNumber\": 50}"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(error.getMessage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(error.getErrorCode()));

        verify(gamesService, never()).playGame(request);
    }

    @Test
    void testPlayGame_InvalidBetNumber() throws Exception {
        GameRequest request = new GameRequest(10.0, 150);
        ApiError error = new ApiError(Error.INCORRECT_BET_NUMBER.getMessage(), Error.INCORRECT_BET_NUMBER.getErrorCode());

        doThrow(new BusinessException(Error.INCORRECT_BET_NUMBER.getMessage(), Error.INCORRECT_BET_NUMBER.getErrorCode()))
                .when(validationService);
                ValidationService.validateCorrectBetNumber(request.getBetNumber());

        mockMvc.perform(MockMvcRequestBuilders.post("/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"betAmount\": 10.0, \"betNumber\": 150}"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(error.getMessage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(error.getErrorCode()));

        verify(gamesService, never()).playGame(request);
    }
}
