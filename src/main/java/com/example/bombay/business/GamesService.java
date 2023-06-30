package com.example.bombay.business;

import com.example.bombay.business.dto.GameRequest;
import com.example.bombay.business.dto.GameResponse;
import com.example.bombay.domain.GameService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GamesService {

    @Resource
    private GameService gameService;

    public GameResponse playGame(GameRequest gameRequest) {
        return gameService.playGame(gameRequest);
    }
}
