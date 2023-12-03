package lab;

import com.google.gson.Gson;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class GameController {
    private Game game;
    @FXML
    private Canvas canvas; // get canvas from scene builder app
    private AnimationTimer animationTimer;
    private AnimationTimer enemySpawnTimer;

    @FXML
    private Label scoreLabel;
    @FXML
    private Label hpLabel;

    private int scorePlayer;
    private int hpPlayer;

    private Set<KeyCode> pressedKeys = new HashSet<>();

    private int LEVEL_NUMBER;

    public GameController() {
    }

    public void startGame(int level) throws IOException {
        LEVEL_NUMBER = level;
        canvas.getScene().setOnKeyPressed(event -> pressedKeys.add(event.getCode()));
        canvas.getScene().setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));

        this.game = new Game(canvas.getWidth(), canvas.getHeight(), pressedKeys, LEVEL_NUMBER);
        this.game.setGameListener(new GameListener() {
            @Override
            public void gameWin() throws IOException {
                stopGame();
                enemySpawnTimer.stop();
                GameController.this.switchScene("GameWinMenu.fxml", true);
                System.out.println("You passed a level BRO");
                SaveLoadFile.saveScores("PLAYER", scorePlayer);
            }

            @Override
            public void stateChanged(int score, int hp) {
                System.out.println(score);
                scorePlayer = score;
                scoreLabel.setText("Score: " + scorePlayer);
                hpPlayer = hp;
                hpLabel.setText("HP: " + hpPlayer);
            }

            @Override
            public void gameOver() throws IOException {
                stopGame();
                enemySpawnTimer.stop();
                GameController.this.switchScene("GameOverMenu.fxml", false);
                System.out.println("GAME OVER BRO");
                SaveLoadFile.saveScores("PLAYER", scorePlayer);
            }
        });

        animationTimer = new DrawingThread(canvas, pressedKeys, this.game);
        animationTimer.start();

        scoreLabel.setText("Score: " + "0"); // budu nacitat z JSONU
        hpLabel.setText("HP: " + "3");           // budu nacitat z JSONU

        System.out.println("game was started");
        this.startGeneratingEnemies(this.game);
    }

    public void startGeneratingEnemies(Game game) {
        long spawnTime = 4_000_000_000L / (long) GameStartMenuController.getLevel();
        enemySpawnTimer = new AnimationTimer() {
            long lastSpawnTime = 0;

            @Override
            public void handle(long now) {
                if (now - lastSpawnTime >= spawnTime) { //  In nanoseconds
                    game.spawnEnemies();
                    lastSpawnTime = now;
                }
            }
        };

        enemySpawnTimer.start();
    }

    public void switchScene(String XMLFile, boolean levelPassed) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(XMLFile));
        BorderPane pane = loader.load(); // Load a .fxml file

        // load a new controller
        if (!levelPassed) {
            GameOverMenuController gameOverMenuController = loader.getController();
            gameOverMenuController.displayStats(scorePlayer);
        } else {
            GameWinMenuController gameWinMenuController = loader.getController();
            gameWinMenuController.displayStats(scorePlayer);
        }

        // Get the stage associated with the existing window
        Stage existingStage = (Stage) canvas.getScene().getWindow();
        Scene newScene = new Scene(pane);
        newScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        existingStage.setScene(newScene);
        existingStage.show();
    }

    public void stopGame() {
        animationTimer.stop();
    }
}




