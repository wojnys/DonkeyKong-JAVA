package lab;

import javafx.animation.*;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.Set;

public class Player extends Human implements LadderCollisionable{
    private int hp;
    private int score;
    private final double speedX;
    private final double speedY;
    private boolean moveRight = true;
    private boolean moveLeft = true;
    private boolean moveBottom = true;  // for goiing down by letter
    private boolean moveUp = false;  // is just for climbing by ladders
    private boolean moveDown = false; // is just for goigindown by ladders
    private double gravity = 170;
    private boolean applyGravity = true;
    private boolean hammer = false;

    private boolean isOnPlatform = false;

    private boolean jumpAnimationRunning = false;
    private boolean pressedSpace = false;

    private Image imageLeft = new Image(getClass().getResourceAsStream("/lab/mario-left.gif"));
    private Image imageRight = new Image(getClass().getResourceAsStream("/lab/mario-cropped.gif"));
    private Image imageStand = new Image(getClass().getResourceAsStream("/lab/mario-stand.png"));


    public Player(Game game, Point2D position, Point2D size, Point2D velocity, Image imagePath) {
        super(game, position, size, velocity, imagePath);
        this.score = 0;
        this.speedX = this.velocity.getX();
        this.speedY = this.velocity.getY();
        this.hp = 3;
    }

    public void applyGravity(double deltaT) {
        if (moveBottom && this.applyGravity) {
            this.position = this.position.add(0,  (this.gravity) * deltaT);
        }
    }

    public void setHammer(boolean value) {
        this.hammer = value;
    }
    public boolean getHammer() {return this.hammer;}

    public void jump(double deltaT) {
        if (jumpAnimationRunning) {
            // Duration of jump
            Duration jumpDuration = Duration.seconds(0.3);

            // Create a Timeline for the jump animation
            Timeline jumpTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    // This code executes at the beginning of the animation
                    this.position = this.position.subtract(0,  (330) * deltaT);
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

    public void movementPlayer(Set<KeyCode> pressedKeys, double deltaT) {
        this.image = imageStand;
        if (pressedKeys.contains(KeyCode.D)) {
            if (this.moveRight) {
                this.velocity = new Point2D(Math.abs(this.speedX), 0);
            }
            if (!this.moveRight) {
                this.velocity = new Point2D(0, 0);
            }
            this.image = imageRight;
        }
        if (pressedKeys.contains(KeyCode.A)) {
            if (this.moveLeft) {
                this.velocity = new Point2D(this.speedX * (-1), 0);
            }
            if (!this.moveLeft) {
                this.velocity = new Point2D(0, 0);
            }
            this.image = imageLeft;
        }
        if (pressedKeys.contains(KeyCode.W)) {
            if (this.moveUp) {
                this.velocity = new Point2D(0, this.speedY * (-1));
            }
            if (!this.moveUp) {
                this.velocity = new Point2D(0, 0);
            }
        }
        if (pressedKeys.contains(KeyCode.S)) {
            if (this.moveDown) {
                this.velocity = new Point2D(0, this.speedY * (1));
            }
            if (!this.moveDown) {
                this.velocity = new Point2D(0, 0);
            }
        }
        // Jump logic
        if (!pressedSpace && this.isOnPlatform) {
            if (pressedKeys.contains(KeyCode.SPACE)) {
                this.velocity = new Point2D(0, 0);
                this.pressedSpace = true;
            }
        }
        if (pressedSpace) {
            this.jumpAnimationRunning = true;
            this.jump(deltaT);
        }
    }

    @Override
    public void update(double deltaT, Set<KeyCode> pressedKeys) {
        if(!moveUp) {
            this.applyGravity(deltaT);
        }
        // Check for collisions with platforms
        this.isOnPlatform = false;
        for (Platform platform : game.getPlatforms()) {
            if (platform.intersects( this.rightBoundingBox()) && pressedKeys.contains(KeyCode.D)) {
                this.moveRight = false;
            }
            if (platform.intersects( this.leftBoundingBox()) && pressedKeys.contains(KeyCode.A)) {
                this.moveLeft = false;
            }
            if (platform.intersects( this.getBoundingBox())) {  // this.bottomBoundingBox()
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
        this.movementPlayer(pressedKeys, deltaT);
        // Player is moving only when Keys are pressed
        if (!pressedKeys.isEmpty()) {
            this.position = this.position.add(this.velocity.getX() * deltaT, this.velocity.getY() * deltaT);
        }
    }

    public int getHp() {return this.hp;}
    public void restart(){
        this.hp -= 1;
        this.position = new Point2D(100,this.game.getHeight() - this.size.getY() - 30);
    }
    public void setScore(int value) {
        this.score += value;
    }
    public int getScore() {
        return this.score;
    }
    public void climbingAllowed() {
        this.moveUp = true;
    }
    public void climbingStopped() {
        this.moveUp = false;
    }
    public void goingDownAllowed() { this.moveDown = true; }
    public void goingDownStopped() { this.moveDown = false; }
    public boolean intersectsWithLadder(Ladder ladder) {
        return getBoundingBox().intersects(ladder.getMiddleBoundingBox());
    }
}
