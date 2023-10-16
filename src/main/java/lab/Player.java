package lab;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
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
    private boolean isJumping = false;
    private double jumpSpeed = 10.0;
    private double gravity = 0.5;
    private boolean applyGravity = true;

    public Player(Game game, Point2D position, Point2D size, Point2D velocity, String imagePath) {
        super(game, position, size, velocity, imagePath);
        this.score = 0;
        this.speedX = this.velocity.getX();
        this.speedY = this.velocity.getY();
    }

    public void applyGravity() {
        if(moveBottom && this.applyGravity) {
            this.position = this.position.add(0, 1.5);
            System.out.println(this.position.getY());
        }
    }

    public void movementPlayer(Set<KeyCode> pressedKeys) {
        if (pressedKeys.contains(KeyCode.D)) {
            if(this.moveRight) {
                this.velocity = new Point2D(Math.abs(this.speedX), 0);
            }
            if(!this.moveRight) {
                this.velocity = new Point2D(0, 0);
            }
        } if (pressedKeys.contains(KeyCode.A)) {
            if(this.moveLeft) {
                this.velocity = new Point2D(this.speedX * (-1), 0);
            }
            if(!this.moveLeft) {
                this.velocity = new Point2D(0, 0);
            }
        } if (pressedKeys.contains(KeyCode.S)) {
            if(this.moveBottom) {
                this.velocity = new Point2D(0, Math.abs(this.speedY));
            }
            if(!this.moveBottom) {
                this.velocity = new Point2D(0, 0);
            }
        }if (pressedKeys.contains(KeyCode.W)) {
            this.velocity = new Point2D( 0, Math.abs(this.speedY) * (-1));
        }

        if(pressedKeys.contains(KeyCode.SPACE)) {
            if (!isJumping) {
                this.position = this.position.add(0, -jumpSpeed);
                isJumping = true;
                Duration duration = Duration.seconds(3); // Adjust the duration as needed
                turnOffGravityForDuration(duration);
            }
        }
    }

    public void turnOffGravityForDuration(Duration duration) {
        // Disable gravity
        applyGravity = false;

        // Create a timeline to re-enable gravity after a specific duration
        Timeline timeline = new Timeline(
                new KeyFrame(duration, e -> applyGravity = true)
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    public void update(double deltaT, Set<KeyCode> pressedKeys) {
        applyGravity();
        // Check for collisions with platforms
        boolean bottomCollision = false;
        for (Platform platform : game.getPlatforms()) {
            if (platform.intersects(this.rightBoundingBox()) && pressedKeys.contains(KeyCode.D)) {
                this.moveRight = false;
            }
            if (platform.intersects(this.leftBoundingBox()) && pressedKeys.contains(KeyCode.A)) {
                this.moveLeft = false;
            }
            if (platform.intersects(this.bottomBoundingBox())) {
                this.moveBottom = false;
                bottomCollision = true;
                this.isJumping = false;
            }
        }
        if(!bottomCollision) {
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
        if (!pressedKeys.isEmpty() ) {
            this.position = this.position.add(this.velocity.getX() * deltaT, this.velocity.getY() * deltaT);
        }
    }
}
