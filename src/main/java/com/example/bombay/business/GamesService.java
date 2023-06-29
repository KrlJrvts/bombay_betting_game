package com.example.bombay.business;

import com.example.bombay.business.dto.GameRequest;
import com.example.bombay.business.dto.GameResponse;
import com.example.bombay.domain.GameService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class GamesService {

    @Resource
    private GameService gameService;

    public GameResponse playGame(GameRequest gameRequest) {
        return gameService.playGame(gameRequest);
    }
}
