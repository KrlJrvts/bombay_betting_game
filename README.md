# Project Name

Bombay Game

## Description

This project is a game application where users can play a betting game by entering their bet amount and bet number. 
The application will generate a random number and compare it with the user's bet number to determine the outcome of 
the game. The user will receive information about their bet amount, bet number, win amount, win number, random number, 
and status (win or lose). The application also includes validation checks for the bet amount and bet number and handles 
error scenarios.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Test Coverage](#test-coverage)
- [Additional Features](#additional-features)
- [Technologies](#technologies)
- [License](#license)

## Installation

To run this project locally, follow these steps:

1. Clone the repository:

   ```bash
   git clone https://github.com/example/bombay-game.git
   ```

2. Navigate to the project directory:

   ```bash
   cd bombay
   ```

3. Build and run the project using your preferred IDE or build tool.

## Usage

1. Make a POST request to the `/game` endpoint with the following payload:

   ```json
   {
       "betAmount": 10.0,
       "betNumber": 50
   }
   ```

   The `betAmount` represents the amount the user wants to bet, and `betNumber` represents the number the user chooses 
to bet on.

2. The application will validate the bet amount and bet number. If they are valid, the game will be played, and the 
3. response will include the following information:

    - `betAmount`: The amount the user bet.
    - `betNumber`: The number the user bet on.
    - `winAmount`: The amount the user wins.
    - `winNumber`: The winning number.
    - `randomNumber`: The randomly generated number.
    - `status`: The outcome of the game (win or lose).

4. If the bet amount or bet number is not valid, an error response will be returned with an appropriate error message.

## API Endpoints

### Play Game

- Endpoint: `/game`
- Method: POST
- Description: Play the game by providing the bet amount and bet number.
- Request Body:

  ```json
  {
      "betAmount": 10.0,
      "betNumber": 50
  }
  ```

- Response:

    - HTTP Status Code: 200 
    - Body:

      ```json
      {
          "betAmount": 10.0,
          "betNumber": 50,
          "winAmount": 9.0,
          "winNumber": 50,
          "randomNumber": 42,
          "status": "You win"
      }
      ```
- Error Responses:
    - HTTP Status Code: 403 (Invalid bet number)
    - Body:

      ```json
      {
          "message": "Bet number is not between 1 and 100.",
          "errorCode": 111
      }
      ```

    - HTTP Status Code: 403 (Invalid bet amount)
    - Body:

      ```json
      {
          "message": "Bet amount is zero or negative.",
          "errorCode": 112
      }
      ```

## Test Coverage

The project has 100%  class test coverage.

To run the tests, follow these steps:

1. Navigate to the test directory:

```bash
cd src/test/java
```

2. Run the from this directory.
3. The test results will be displayed in the console.


## Additional features

For this project, I have added the following feature:

1. The application has a Swagger UI that can be accessed at the following URL: http://localhost:8080/swagger-ui.html
2. The application has a custom error handler that returns a custom error response when an error occurs.
3. The application has a custom index page that can be accessed at the following URL: http://localhost:8080/


## Technologies

- Java 19
- Spring Boot 3.1.1
- Lombok 
- Swagger (OpenAPI)
- Bootstrap
- JavaScript
- CSS
- HTML

## License

The project is licensed under the [MIT License](LICENSE).