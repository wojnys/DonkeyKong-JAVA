package lab;

import javafx.animation.*;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.Set;

public class Player extends Human {
    private double score;
    private double speedX;
    private double speedY;
    private boolean moveRight = true;
    private boolean moveLeft = true;
    private boolean moveBottom = true;  // for goiing down by letter
    private boolean moveUp = true;  // is just for climbing bya  letters
    private double jumpSpeed = 40.0;
    private double gravity = 3;
    private boolean applyGravity = true;

    private boolean isOnPlatform = false;

    private TranslateTransition jumpAnimation;

    private boolean jumpAnimationRunning = false;
    private boolean pressedSpace = false;


    public Player(Game game, Point2D position, Point2D size, Point2D velocity, String imagePath) {
        super(game, position, size, velocity, imagePath);
        this.score = 0;
        this.speedX = this.velocity.getX();
        this.speedY = this.velocity.getY();
    }

    public void applyGravity() {
        if (moveBottom && this.applyGravity) {
            this.position = this.position.add(0, this.gravity);
        }
    }

    public void jump() {
        if (jumpAnimationRunning) {
            // Duration of jump
            Duration jumpDuration = Duration.seconds(0.35);

            // Create a Timeline for the jump animation
            Timeline jumpTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    // This code executes at the beginning of the animation
                    this.position = this.position.subtract(0, 9);
                }),
                new KeyFrame(jumpDuration, e -> {
                    // This code executes after setted time,
                    jumpAnimationRunning = false;
                    this.pressedSpace = false;
                })
            );
            jumpTimeline.setCycleCount(1); // Play the animation once
            jumpTimeline.play();
        }
    }

    public void movementPlayer(Set<KeyCode> pressedKeys) {
        if (pressedKeys.contains(KeyCode.D)) {
            if (this.moveRight) {
                this.velocity = new Point2D(Math.abs(this.speedX), 0);
            }
            if (!this.moveRight) {
                this.velocity = new Point2D(0, 0);
            }
        }
        if (pressedKeys.contains(KeyCode.A)) {
            if (this.moveLeft) {
                this.velocity = new Point2D(this.speedX * (-1), 0);
            }
            if (!this.moveLeft) {
                this.velocity = new Point2D(0, 0);
            }
        }
        if (!pressedSpace && this.isOnPlatform) {
            if (pressedKeys.contains(KeyCode.SPACE)) {
                this.velocity = new Point2D(0, 0);
                this.pressedSpace = true;
            }
        }
        if (pressedSpace) {
            this.jumpAnimationRunning = true;
            this.jump();
        }
    }

    public void update(double deltaT, Set<KeyCode> pressedKeys) {
        applyGravity();
        // Check for collisions with platforms
        this.isOnPlatform = false;
        for (Platform platform : game.getPlatforms()) {
            if (platform.intersects(this.rightBoundingBox()) && pressedKeys.contains(KeyCode.D)) {
                this.moveRight = false;
            }
            if (platform.intersects(this.leftBoundingBox()) && pressedKeys.contains(KeyCode.A)) {
                this.moveLeft = false;
            }
            if (platform.intersects(this.bottomBoundingBox())) {
                this.moveBottom = false;
                this.isOnPlatform = true;
            }
        }
        if (!this.isOnPlatform) {
            this.moveBottom = true;
        }

        // Reset moveRight or moveLeft when not colliding
        if (!moveRight) {
            if (!pressedKeys.contains(KeyCode.D)) {
                moveRight = true;
            }
        }
        if (!moveLeft) {
            if (!pressedKeys.contains(KeyCode.A)) {
                moveLeft = true;
            }
        }

        // Handle player movement
        this.movementPlayer(pressedKeys);
        // Player is moving only when Keys are pressed
        if (!pressedKeys.isEmpty()) {
            this.position = this.position.add(this.velocity.getX() * deltaT, this.velocity.getY() * deltaT);
        }
    }
}
