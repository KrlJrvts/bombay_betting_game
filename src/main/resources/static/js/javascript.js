document.getElementById("gameForm").addEventListener("submit", function(event) {
    event.preventDefault();

    console.log("JavaScript code is running");

    var betAmount = parseFloat(document.getElementById("betAmount").value);
    var betNumber = parseInt(document.getElementById("betNumber").value);

    var gameRequest = {
        betAmount: betAmount,
        betNumber: betNumber
    };

    fetch("/game", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(gameRequest)
    })
        .then(response => response.json())
        .then(data => {
            document.getElementById("winAmount").textContent = "Win Amount: " + data.winAmount;
            document.getElementById("winNumber").textContent = "Win Number: " + data.winNumber;
            document.getElementById("randomNumber").textContent = "Random Number: " + data.randomNumber;
            document.getElementById("status").textContent = "Status: " + data.status;
            document.getElementById("gameResult").style.display = "block";
        })
        .catch(error => console.log(error));
});
