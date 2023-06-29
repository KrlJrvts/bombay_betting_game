package com.example.bombay.business;

import com.example.bombay.infratstructure.error.ApiError;
import com.example.bombay.business.dto.GameRequest;
import com.example.bombay.business.dto.GameResponse;
import com.example.bombay.infratstructure.exception.BusinessException;
import com.example.bombay.validation.ValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.example.bombay.validation.ValidationService.validateCorrectBetAmount;
import static com.example.bombay.validation.ValidationService.validateCorrectBetNumber;

@RestController
public class GamesController {

    @Resource
    private GamesService gamesService;

    @Resource
    private ValidationService validationService;

    @PostMapping("/game")
    @Operation(summary = "Play game by betAmount, betNumber", description = """
            User enters betAmount, betNumber. And will get automatically betAmount, betNumber, winAmount, winNumber, status.
            System also checks if betAmount and betNumber are valid. If it is, error with errorCode 111 is thrown""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game completed successfully!"),
            @ApiResponse(responseCode = "403", description = "Bet amount is not valid", content = @Content(schema = @Schema(implementation = ApiError.class)))})
    public ResponseEntity<?> playGame(@RequestBody GameRequest gameRequest) {
        try {
            validateCorrectBetAmount(gameRequest.getBetAmount());
            validateCorrectBetNumber(gameRequest.getBetNumber());

            GameResponse gameResponse = gamesService.playGame(gameRequest);
            return ResponseEntity.ok(gameResponse);
        } catch (BusinessException e) {
            ApiError apiError = new ApiError(e.getMessage(), e.getErrorCode());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
        }

    }
}
