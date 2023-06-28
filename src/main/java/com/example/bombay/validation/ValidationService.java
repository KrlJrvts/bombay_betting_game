package com.example.bombay.validation;

import com.example.bombay.infratstructure.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    public static void validateCorrectBetAmount(double betAmount) {
        if (betAmount <= 0) {
            throw new BusinessException(Error.INCORRECT_BET_AMOUNT.getMessage(), Error.INCORRECT_BET_AMOUNT.getErrorCode());
        }
    }

    public static void validateCorrectBetNumber(int betNumber) {
        if (betNumber < 1 || betNumber > 100) {
            throw new BusinessException(Error.INCORRECT_BET_NUMBER.getMessage(), Error.INCORRECT_BET_NUMBER.getErrorCode());
        }
    }

}
