package lab;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class GameStartMenuController {

    @FXML
    private Canvas canvas; // get canvas from scene builder app
    private Game game;
    private AnimationTimer animationTimer;

    public void startGame() {

    }

    @FXML
    public void startNewGame() throws IOException {
        this.switchScene();
    }

    public void switchScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameView.fxml"));
        BorderPane pane = loader.load(); // Load a .fxml file

        Stage existingStage = (Stage) canvas.getScene().getWindow();
        Scene newScene = new Scene(pane);
        newScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        existingStage.setScene(newScene);
        existingStage.show();

        // load a new controller
        GameController gameController = loader.getController();
        gameController.startGame();
    }


    public void stopGame() {
        animationTimer.stop();
    }
}


