package com.example.demo;

import javafx.scene.control.Alert;

public class RoundScore {
    private String player1Score;
    private String player2Score;

    public RoundScore(String player1Score, String player2Score) {
        this.player1Score = player1Score;
        this.player2Score = player2Score;
    }

    public void displayScore() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Round Score");
        alert.setHeaderText(null);
        alert.setContentText("Player 1 score: " + player1Score + "\nPlayer 2 score: " + player2Score);
        alert.showAndWait();
    }
}
