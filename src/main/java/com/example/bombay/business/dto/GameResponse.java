package com.example.bombay.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameResponse {
    private double betAmount;
    private int betNumber;
    private double winAmount;
    private int winNumber;
    private int randomNumber;
    private String status;


}
