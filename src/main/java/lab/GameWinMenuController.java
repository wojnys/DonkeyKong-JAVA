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

public class GameWinMenuController {
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

    public void displayStats(int score) {
        currentScore.setText("Your current SCORE: " + score);
        Pair<String, Integer> highestScoreFromFile =  SaveLoadFile.loadHighestScore();
        highestScore.setText("Name : " + highestScoreFromFile.getKey() + ", Score: " +highestScoreFromFile.getValue() );
    }

    @FXML
    public void nextLevel() throws IOException {
        int LEVEL_NUMBER_INCREASED = GameStartMenuController.getLevel();
        LEVEL_NUMBER_INCREASED++; // Increase Level value
        GameStartMenuController.setLevel(LEVEL_NUMBER_INCREASED);


        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameView.fxml"));
        BorderPane pane = loader.load(); // Load a .fxml file

        Stage existingStage = (Stage) canvas.getScene().getWindow();
        Scene newScene = new Scene(pane);
        newScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        existingStage.setScene(newScene);
        existingStage.show();

        // load a new controller
        GameController gameController = loader.getController();
        gameController.startGame(LEVEL_NUMBER_INCREASED);
    }
    @FXML
    public void startNewGame() throws IOException {
        this.switchScene();
    }
    @FXML
    public void quitGame() {
        Stage stage = (Stage) canvas.getScene().getWindow();
        // do what you have to do
        stage.close();
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
