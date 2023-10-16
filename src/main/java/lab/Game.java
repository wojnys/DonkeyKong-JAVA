package lab;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Game {
    private final double width;
    private final double height;
    private Scene scene;
    private Player player;
    private Ground ground;
    private Set<KeyCode> pressedKeys;
    private Rectangle2D[] brownWalls;

    private List<Platform> platforms;

    public Game(double width, double height, Scene scene, Set<KeyCode> pressedKeys) {
        this.width = width;
        this.height = height;
        this.scene = scene;
        this.player = new Player(this, new Point2D(200, height-400), new Point2D(60, 60), new Point2D(100, 60), "/lab/player.png");
        this.ground = new Ground(new Point2D(0,height-30),new Point2D(width, 30));
        this.pressedKeys = pressedKeys;
        platforms = new ArrayList<>();
        platforms.add(new Platform(new Point2D(100, 500), new Point2D(200, 30), Color.BROWN));
        platforms.add(new Platform(new Point2D(100, 600), new Point2D(3000, 30), Color.BROWN));
        platforms.add(new Platform(new Point2D(500, 700), new Point2D(400, 300), Color.BROWN));
        platforms.add(new Platform(new Point2D(0, 760), new Point2D(1200, 40), Color.BROWN));
    }


    public void draw(GraphicsContext gc) {
        this.ground.draw(gc);
        this.player.draw(gc);

        for (Platform platform : platforms) {
            platform.draw(gc);
        }
    }

    public void update(double deltaT, Set<KeyCode> pressedKeys) {
        this.player.update(deltaT, pressedKeys);
        if(this.ground.getBoundingBox().intersects(this.player.getBoundingBox())) {
            System.out.println("THIS IS GROUND - Player collision");
        }
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public List<Platform> getPlatforms() {
        return this.platforms;
    }
}
