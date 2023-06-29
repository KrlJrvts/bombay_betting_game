package com.example.bombay.domain;

import com.example.bombay.business.dto.GameRequest;
import com.example.bombay.business.dto.GameResponse;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GameService {

    private final Random random;

    public GameService() {
        this.random = new Random();
    }

    public GameResponse playGame(GameRequest gameRequest) {

        double betAmount = gameRequest.getBetAmount();
        int betNumber = gameRequest.getBetNumber();
        double winAmount = 0;
        String status = "You win";

        int randomNumber = getRandomNumber();

        return getGameResponse(betNumber, betAmount, winAmount, status, randomNumber);
    }

    public int getRandomNumber() {
        return random.nextInt(100) + 1;
    }

    private static GameResponse getGameResponse(int betNumber, double betAmount, double winAmount, String status, int randomNumber) {
        int winNumber;
        if (betNumber == 100) {
            winNumber = betNumber;
        } else if (betNumber == randomNumber) {
            winAmount = betAmount;
            winNumber = betNumber;
        } else if (betNumber > randomNumber) {
            winAmount = betAmount * (99.0 / (100 - betNumber));
            winNumber = betNumber;
        } else {
            winNumber = randomNumber;
            status = "You lose";
        }
        return new GameResponse(betAmount, betNumber, winAmount, winNumber, randomNumber, status);
    }

}
