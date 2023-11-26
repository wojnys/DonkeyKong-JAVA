package lab;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Objects;

public class GameOverMenuController {
    @FXML
    private Canvas canvas; // get canvas from scene builder app
    private Game game;
    private AnimationTimer animationTimer;

    @FXML
    Label currentScore;
    @FXML
    Label highestScore;

    public void startGame() {

    }

    @FXML
    public void startNewGame() throws IOException {
        this.switchScene();
    }

    public void displayStats(int score) {
        currentScore.setText("Your current SCORE: " + score);
        Pair<String, Integer> highestScoreFromFile =  SaveLoadFile.loadHighestScore();
        highestScore.setText("Name : " + highestScoreFromFile.getKey() + ", Score: " +highestScoreFromFile.getValue() );
    }

    public void switchScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameStartMenu.fxml"));
        BorderPane pane = loader.load(); // Load a .fxml file

        // Get the stage associated with the existing window
        Stage existingStage = (Stage) canvas.getScene().getWindow();
        Scene newScene = new Scene(pane);
        newScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        existingStage.setScene(newScene);
        existingStage.show();

        // load a new controller
    }


    public void stopGame() {
        animationTimer.stop();
    }
}
