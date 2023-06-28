package com.example.bombay.domain;

import com.example.bombay.business.dto.GameRequest;
import com.example.bombay.business.dto.GameResponse;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GameService {


    public GameResponse playGame(GameRequest gameRequest) {

        int betNumber = gameRequest.getBetNumber();
        double betAmount = gameRequest.getBetAmount();
        double winAmount = 0;
        int winNumber = 0;


        int randomNumber = new Random().nextInt(100) + 1;

        if (betNumber > randomNumber) {
            winAmount = betAmount * (99.0 / (100 - betNumber));
            String status = "You win";
            winNumber = betNumber;
            return new GameResponse(betAmount, betNumber, winAmount, winNumber, randomNumber,status);
        }

        winAmount = 0;
        String status = "You lose";
        winNumber = randomNumber;
        return new GameResponse(betAmount, betNumber, winAmount, winNumber, randomNumber, status);

    }

}
