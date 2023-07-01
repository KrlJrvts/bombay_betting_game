package com.example.bombay.business;

import com.example.bombay.business.dto.GameRequest;
import com.example.bombay.business.dto.GameResponse;
import com.example.bombay.domain.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class GamesService {

    @Autowired
    private GameService gameService;

    public GameResponse playGame(GameRequest gameRequest) {
        return gameService.playGame(gameRequest);
    }
}
