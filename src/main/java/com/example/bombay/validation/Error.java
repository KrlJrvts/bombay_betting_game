package com.example.bombay.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum Error {
    INCORRECT_BET_NUMBER("Bet number is not between 1 and 100.", 111),
    INCORRECT_BET_AMOUNT("Bet amount is zero or negative.", 112);

    private final String message;
    private final int errorCode;

}

