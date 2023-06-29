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
        String status = "You win";

        int randomNumber = getRandomNumber();

        return getGameResponse(betNumber, betAmount, winAmount, status, randomNumber);
    }

    private int getRandomNumber() {
        return new Random().nextInt(100) + 1;
    }

    private static GameResponse getGameResponse(int betNumber, double betAmount, double winAmount, String status, int randomNumber) {
        int winNumber;
        if (betNumber == 100) {
            winNumber = betNumber;
        } else if (betNumber > randomNumber) {
            winAmount = betAmount * (99.0 / (100 - betNumber));
            winNumber = betNumber;
        } else if (betNumber == randomNumber) {
            winAmount = betAmount;
            winNumber = betNumber;
        } else {
            winNumber = randomNumber;
            status = "You lose";
        }
        return new GameResponse(betAmount, betNumber, winAmount, winNumber, randomNumber, status);
    }

}
