package lab;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class GameController {
    private Game game;
    @FXML
    private Canvas canvas; // get canvas from scene builder app
    private AnimationTimer animationTimer;

    private Set<KeyCode> pressedKeys = new HashSet<>();


    public GameController(){
    }

    public void startGame() throws IOException {
        canvas.getScene().setOnKeyPressed(event -> pressedKeys.add(event.getCode()));
        canvas.getScene().setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));
        this.game = new Game( canvas.getWidth(), canvas.getHeight(),pressedKeys );

        animationTimer = new DrawingThread(canvas, pressedKeys, this.game);
        animationTimer.start();

        System.out.println("game was started");
        this.startGeneratingEnemies(this.game);
    }
    public void startGeneratingEnemies(Game game) {
        AnimationTimer enemySpawnTimer = new AnimationTimer() {
            long lastSpawnTime = 0;

            @Override
            public void handle(long now) {
                if (now - lastSpawnTime >= 5_000_000_000L) { // 5 seconds in nanoseconds
                    game.spawnEnemies();
                    lastSpawnTime = now;
                }
            }
        };
        enemySpawnTimer.start();
    }

    public void switchScene() throws IOException {
        animationTimer.stop();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOverMenu.fxml"));
        BorderPane pane = loader.load(); // Load a .fxml file

        // load a new controller
        GameOverMenuController gameOverMenuController = loader.getController();

        // Get the stage associated with the existing window
        Stage existingStage = (Stage) canvas.getScene().getWindow();
        Scene newScene = new Scene(pane);
        existingStage.setScene(newScene);
        existingStage.show();
    }

    public void stopGame() {
        animationTimer.stop();
    }

}




