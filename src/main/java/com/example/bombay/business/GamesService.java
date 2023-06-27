package com.example.bombay.business;

import com.example.bombay.business.dto.GameRequest;
import com.example.bombay.business.dto.GameResponse;
import com.example.bombay.domain.GameService;
import com.example.bombay.validation.ValidationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class GamesService {

    @Resource
    private GameService gameService;

    public GameResponse playGame(GameRequest gameRequest) {

        double betAmount = gameRequest.getBetAmount();
        int betNumber = gameRequest.getBetNumber();

        ValidationService.validateCorrectBetAmount(betAmount);
        ValidationService.validateCorrectBetNumber(betNumber);

        return gameService.playGame(gameRequest);
    }
}
